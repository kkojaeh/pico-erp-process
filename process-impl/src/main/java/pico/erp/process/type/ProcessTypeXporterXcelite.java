package pico.erp.process.type;

import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheetImpl;
import com.ebay.xcelite.writer.SheetWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.process.cost.ProcessCostRatesData;
import pico.erp.process.difficulty.grade.ProcessDifficultyGradeData;
import pico.erp.process.info.type.xcelite.ProcessTypeXportData;
import pico.erp.process.type.ProcessTypeRequests.CreateRequest;
import pico.erp.process.type.ProcessTypeRequests.UpdateRequest;
import pico.erp.shared.Public;
import pico.erp.shared.data.ContentInputStream;

@Component
@Public
@Validated
@Transactional
public class ProcessTypeXporterXcelite implements ProcessTypeXporter {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private ProcessTypeService processTypeService;

  @Autowired
  private ProcessTypeRepository processTypeRepository;

  {
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  @SneakyThrows
  @Override
  public ContentInputStream exportExcel(ExportRequest request) {
    XSSFWorkbook workbook = new XSSFWorkbook();
    SheetWriter<ProcessTypeXportData> processTypeWriter = new XceliteSheetImpl(
      workbook.createSheet("process-types"))
      .getBeanWriter(ProcessTypeXportData.class);
    processTypeWriter.generateHeaderRow(true);
    if (request.isEmpty()) {
      processTypeWriter.write(Collections.emptyList());
    } else {
      processTypeWriter.write(
        processTypeRepository.getAll()
          .map(this::translate)
          .collect(Collectors.toList())
      );
    }
    @Cleanup
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    workbook.write(baos);

    return ContentInputStream.builder()
      .name(
        String.format("process-type-%s.%s",
          DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(OffsetDateTime.now()),
          "xlsx"
        )
      )
      .contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
      .contentLength(baos.size())
      .inputStream(new ByteArrayInputStream(baos.toByteArray()))
      .build();
  }

  @SneakyThrows
  @Override
  public void importExcel(ImportRequest request) {
    @Cleanup
    InputStream xlsxInputStream = request.getInputStream();
    XSSFWorkbook workbook = new XSSFWorkbook(xlsxInputStream);
    SheetReader<ProcessTypeXportData> processTypeReader = new XceliteSheetImpl(
      workbook.getSheet("process-types"))
      .getBeanReader(ProcessTypeXportData.class);
    processTypeReader.skipHeaderRow(true);
    Collection<ProcessTypeXportData> processTypes = processTypeReader.read();

    Map<ProcessTypeId, Boolean> exists = new HashMap<>();

    processTypes.stream()
      .forEach(data -> {
        exists.put(data.getId(), processTypeService.exists(data.getId()));
      });

    // 존재하지 않는 것만 생성
    processTypes.stream()
      .filter(data -> !exists.get(data.getId()))
      .map(this::toCreate)
      .forEach(processTypeService::create);

    if (request.isOverwrite()) {
      processTypes.stream()
        .filter(data -> exists.get(data.getId()))
        .map(this::toUpdate)
        .forEach(processTypeService::update);
    }
  }

  @SneakyThrows
  CreateRequest toCreate(ProcessTypeXportData data) {
    return ProcessTypeRequests.CreateRequest.builder()
      .id(data.getId())
      .name(data.getName())
      .baseUnitCost(data.getBaseUnitCost())
      .infoTypeId(data.getInfoTypeId())
      .costRates(objectMapper.readValue(data.getCostRates(), ProcessCostRatesData.class))
      .difficultyGrades(objectMapper.readValue(data.getDifficultyGrades(),
        objectMapper.getTypeFactory()
          .constructCollectionType(List.class, ProcessDifficultyGradeData.class)))
      .build();
  }

  @SneakyThrows
  UpdateRequest toUpdate(ProcessTypeXportData data) {
    return ProcessTypeRequests.UpdateRequest.builder()
      .id(data.getId())
      .name(data.getName())
      .baseUnitCost(data.getBaseUnitCost())
      .infoTypeId(data.getInfoTypeId())
      .costRates(objectMapper.readValue(data.getCostRates(), ProcessCostRatesData.class))
      .difficultyGrades(objectMapper.readValue(data.getDifficultyGrades(),
        objectMapper.getTypeFactory()
          .constructCollectionType(List.class, ProcessDifficultyGradeData.class)))
      .build();
  }

  @SneakyThrows
  ProcessTypeXportData translate(ProcessType processType) {
    return ProcessTypeXportData.builder()
      .id(processType.getId())
      .name(processType.getName())
      .baseUnitCost(processType.getBaseUnitCost())
      .infoTypeId(processType.getInfoTypeId())
      .costRates(objectMapper.writeValueAsString(processType.getCostRates()))
      .difficultyGrades(objectMapper.writeValueAsString(processType.getDifficultyGrades()))
      .build();
  }

}
