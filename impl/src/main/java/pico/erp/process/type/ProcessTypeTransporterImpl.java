package pico.erp.process.type;

import com.coreoz.windmill.Windmill;
import com.coreoz.windmill.exports.config.ExportHeaderMapping;
import com.coreoz.windmill.exports.exporters.excel.ExportExcelConfig;
import com.coreoz.windmill.files.FileSource;
import com.coreoz.windmill.imports.Parsers;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kkojaeh.spring.boot.component.Give;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import pico.erp.process.cost.ProcessCostRates;
import pico.erp.process.difficulty.ProcessDifficulty;
import pico.erp.process.difficulty.ProcessDifficultyKind;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.process.preparation.type.ProcessPreparationType;
import pico.erp.process.preparation.type.ProcessPreparationTypeId;
import pico.erp.process.preparation.type.ProcessPreparationTypeMapper;
import pico.erp.shared.data.ContentInputStream;
import pico.erp.shared.event.EventPublisher;

@Component
@Give
@Validated
@Transactional
public class ProcessTypeTransporterImpl implements ProcessTypeTransporter {

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProcessTypeRepository processTypeRepository;

  @Autowired
  private ProcessPreparationTypeMapper processPreparationTypeMapper;

  @SneakyThrows
  @Override
  public ContentInputStream exportExcel(ExportRequest request) {
    val locale = LocaleContextHolder.getLocale();
    Stream<ProcessType> processTypes =
      request.isEmpty() ? Stream.empty() : processTypeRepository.getAll();
    val workbook = new XSSFWorkbook();
    val bytes = Windmill
      .export(() -> processTypes.iterator())
      .withHeaderMapping(
        new ExportHeaderMapping<ProcessType>()
          .add("id", e -> e.getId().getValue())
          .add("name", e -> e.getName())
          .add("baseUnitCost", e -> e.getBaseUnitCost())
          .add("infoTypeId",
            e -> e.getInfoTypeId() != null ? e.getInfoTypeId().getValue() : null)
          .add("lossRate", e -> e.getLossRate())
          .add("costRates[directLabor]", e -> e.getCostRates().getDirectLabor())
          .add("costRates[indirectLabor]", e -> e.getCostRates().getIndirectLabor())
          .add("costRates[indirectMaterial]", e -> e.getCostRates().getIndirectMaterial())
          .add("costRates[indirectExpenses]", e -> e.getCostRates().getIndirectExpenses())
          .add("difficulties[EASY].costRate",
            e -> e.getDifficulties().get(ProcessDifficultyKind.EASY).getCostRate())
          .add("difficulties[EASY].description",
            e -> e.getDifficulties().get(ProcessDifficultyKind.EASY).getDescription())
          .add("difficulties[NORMAL].costRate",
            e -> e.getDifficulties().get(ProcessDifficultyKind.NORMAL).getCostRate())
          .add("difficulties[NORMAL].description",
            e -> e.getDifficulties().get(ProcessDifficultyKind.NORMAL).getDescription())
          .add("difficulties[HARD].costRate",
            e -> e.getDifficulties().get(ProcessDifficultyKind.HARD).getCostRate())
          .add("difficulties[HARD].description",
            e -> e.getDifficulties().get(ProcessDifficultyKind.HARD).getDescription())
          .add("difficulties[VERY_HARD].costRate",
            e -> e.getDifficulties().get(ProcessDifficultyKind.HARD).getCostRate())
          .add("difficulties[VERY_HARD].description",
            e -> e.getDifficulties().get(ProcessDifficultyKind.HARD).getDescription())
          .add("preparationTypes", e ->
            StringUtils.collectionToCommaDelimitedString(
              e.getPreparationTypes().stream()
                .map(ProcessPreparationType::getId)
                .map(ProcessPreparationTypeId::getValue)
                .collect(Collectors.toList())
            )
          )
      )
      .asExcel(
        ExportExcelConfig.fromWorkbook(workbook).build("process-types")
      )
      .toByteArray();

    return ContentInputStream.builder()
      .name(
        String.format("process-types-%s.%s",
          DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()),
          ContentInputStream.XLSX_CONTENT_EXTENSION
        )
      )
      .contentType(ContentInputStream.XLSX_CONTENT_TYPE)
      .contentLength(bytes.length)
      .inputStream(new ByteArrayInputStream(bytes))
      .build();
  }

  @SneakyThrows
  @Override
  public void importExcel(ImportRequest request) {
    /*

    add("costRates[directLabor]", e -> e.getCostRates().getDirectLabor())
          .add("costRates[indirectLabor]", e -> e.getCostRates().getIndirectLabor())
          .add("costRates[indirectMaterial]", e -> e.getCostRates().getIndirectMaterial())
          .add("costRates[indirectExpenses]", e -> e.getCostRates().getIndirectExpenses())
          .add("difficulties[EASY].costRate", e -> e.getDifficulties().get(ProcessDifficultyKind.EASY).getCostRate())
          .add("difficulties[EASY].description", e -> e.getDifficulties().get(ProcessDifficultyKind.EASY).getDescription())
          .add("difficulties[NORMAL].costRate", e -> e.getDifficulties().get(ProcessDifficultyKind.NORMAL).getCostRate())
          .add("difficulties[NORMAL].description", e -> e.getDifficulties().get(ProcessDifficultyKind.NORMAL).getDescription())
          .add("difficulties[HARD].costRate", e -> e.getDifficulties().get(ProcessDifficultyKind.HARD).getCostRate())
          .add("difficulties[HARD].description", e -> e.getDifficulties().get(ProcessDifficultyKind.HARD).getDescription())
          .add("difficulties[VERY_HARD].costRate", e -> e.getDifficulties().get(ProcessDifficultyKind.HARD).getCostRate())
          .add("difficulties[VERY_HARD].description", e -> e.getDifficulties().get(ProcessDifficultyKind.HARD).getDescription())
     */

    val processTypes = Parsers.xlsx("process-types")
      .trimValues()
      .parse(FileSource.of(request.getInputStream()))
      .skip(1)
      .map(row -> ProcessType.builder()
        .id(ProcessTypeId.from(row.cell("id").asString()))
        .name(row.cell("name").asString())
        .baseUnitCost(new BigDecimal(row.cell("baseUnitCost").asString()))
        .infoTypeId(
          Optional.ofNullable(row.cell("infoTypeId").asString())
            .map(id -> ProcessInfoTypeId.from(id))
            .orElse(null)
        )
        .lossRate(new BigDecimal(row.cell("lossRate").asString()))
        .costRates(
          ProcessCostRates.builder()
            .directLabor(new BigDecimal(row.cell("costRates[directLabor]").asString()))
            .indirectLabor(new BigDecimal(row.cell("costRates[indirectLabor]").asString()))
            .indirectMaterial(new BigDecimal(row.cell("costRates[indirectMaterial]").asString()))
            .indirectExpenses(new BigDecimal(row.cell("costRates[indirectExpenses]").asString()))
            .build()
        )
        .difficulties(
          Stream.of(
            ProcessDifficultyWithKind.subBuilder()
              .difficulty(ProcessDifficultyKind.EASY)
              .costRate(new BigDecimal(row.cell("difficulties[EASY].costRate").asString()))
              .description(row.cell("difficulties[EASY].description").asString())
              .build(),
            ProcessDifficultyWithKind.subBuilder()
              .difficulty(ProcessDifficultyKind.NORMAL)
              .costRate(new BigDecimal(row.cell("difficulties[NORMAL].costRate").asString()))
              .description(row.cell("difficulties[NORMAL].description").asString())
              .build(),
            ProcessDifficultyWithKind.subBuilder()
              .difficulty(ProcessDifficultyKind.HARD)
              .costRate(new BigDecimal(row.cell("difficulties[HARD].costRate").asString()))
              .description(row.cell("difficulties[HARD].description").asString())
              .build(),
            ProcessDifficultyWithKind.subBuilder()
              .difficulty(ProcessDifficultyKind.VERY_HARD)
              .costRate(new BigDecimal(row.cell("difficulties[VERY_HARD].costRate").asString()))
              .description(row.cell("difficulties[VERY_HARD].description").asString())
              .build()
          ).collect(
            Collectors.toMap(ProcessDifficultyWithKind::getDifficulty, d -> d)
          )
        )
        .preparationTypes(
          StringUtils.commaDelimitedListToSet(row.cell("preparationTypes").asString())
            .stream()
            .map(ProcessPreparationTypeId::from)
            .map(processPreparationTypeMapper::map)
            .collect(Collectors.toList())
        )
        .build()
      );

    processTypes.forEach(processType -> {
      val previous = processTypeRepository.findBy(processType.getId()).orElse(null);
      val response = processType.apply(new ProcessTypeMessages.PrepareImportRequest(previous));
      if (previous == null) {
        processTypeRepository.create(processType);
      } else if (request.isOverwrite()) {
        processTypeRepository.update(processType);
      }
      eventPublisher.publishEvents(response.getEvents());
    });
  }

  @Getter
  private static class ProcessDifficultyWithKind extends ProcessDifficulty {

    private ProcessDifficultyKind difficulty;

    @Builder(builderMethodName = "subBuilder")
    public ProcessDifficultyWithKind(String description, BigDecimal costRate,
      ProcessDifficultyKind difficulty) {
      super(description, costRate);
      this.difficulty = difficulty;
    }
  }

}
