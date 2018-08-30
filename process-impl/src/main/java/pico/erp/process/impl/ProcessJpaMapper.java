package pico.erp.process.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.item.ItemService;
import pico.erp.item.data.ItemData;
import pico.erp.item.data.ItemId;
import pico.erp.process.ProcessTypeExceptions.NotFoundException;
import pico.erp.process.core.PreprocessTypeRepository;
import pico.erp.process.core.ProcessInfoTypeRepository;
import pico.erp.process.core.ProcessTypeRepository;
import pico.erp.process.data.PreprocessTypeId;
import pico.erp.process.data.ProcessInfo;
import pico.erp.process.data.ProcessInfoType;
import pico.erp.process.data.ProcessInfoTypeId;
import pico.erp.process.data.ProcessTypeId;
import pico.erp.process.domain.Preprocess;
import pico.erp.process.domain.PreprocessType;
import pico.erp.process.domain.Process;
import pico.erp.process.domain.ProcessCost;
import pico.erp.process.domain.ProcessCostRates;
import pico.erp.process.domain.ProcessDifficultyGrade;
import pico.erp.process.domain.ProcessType;
import pico.erp.process.impl.jpa.PreprocessEntity;
import pico.erp.process.impl.jpa.PreprocessTypeEntity;
import pico.erp.process.impl.jpa.ProcessCostEmbeddable;
import pico.erp.process.impl.jpa.ProcessCostRatesEmbeddable;
import pico.erp.process.impl.jpa.ProcessDifficultyGradeEmbeddable;
import pico.erp.process.impl.jpa.ProcessEntity;
import pico.erp.process.impl.jpa.ProcessTypeEntity;
import pico.erp.user.UserService;
import pico.erp.user.data.UserData;
import pico.erp.user.data.UserId;

@Mapper(imports = ProcessTypeEntity.class)
public abstract class ProcessJpaMapper {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Lazy
  @Autowired
  private ProcessInfoTypeRepository processInfoTypeRepository;

  @Lazy
  @Autowired
  private ProcessTypeRepository processTypeRepository;

  @Lazy
  @Autowired
  private PreprocessTypeRepository preprocessTypeRepository;

  @Lazy
  @Autowired
  private ItemService itemService;

  @Lazy
  @Autowired
  private UserService userService;

  {
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  @AfterMapping
  protected void afterMapping(ProcessEntity from, @MappingTarget ProcessEntity to) {
    to.setType(from.getType());
  }

  @AfterMapping
  protected void afterMapping(PreprocessEntity from, @MappingTarget PreprocessEntity to) {
    to.setType(from.getType());
  }

  @SneakyThrows
  protected String map(ProcessInfo info) {
    if (info == null) {
      return null;
    }
    return objectMapper.writeValueAsString(info);
  }

  protected ProcessInfoType map(ProcessInfoTypeId infoTypeId) {
    return Optional.ofNullable(infoTypeId)
      .map(id -> processInfoTypeRepository.findBy(id)
        .orElseThrow(pico.erp.process.ProcessInfoTypExceptions.NotFoundException::new)
      )
      .orElse(null);
  }

  protected ProcessType map(ProcessTypeId typeId) {
    return Optional.ofNullable(typeId)
      .map(id -> processTypeRepository.findBy(id)
        .orElseThrow(NotFoundException::new))
      .orElse(null);
  }

  protected PreprocessType map(PreprocessTypeId typeId) {
    return Optional.ofNullable(typeId)
      .map(id -> preprocessTypeRepository.findBy(id)
        .orElseThrow(NotFoundException::new))
      .orElse(null);
  }

  protected ItemData map(ItemId itemId) {
    return Optional.ofNullable(itemId)
      .map(itemService::get)
      .orElse(null);
  }

  protected UserData map(UserId userId) {
    return Optional.ofNullable(userId)
      .map(userService::get)
      .orElse(null);
  }

  //@SuppressWarnings("unchecked")
  public Process map(ProcessEntity entity) {
    ProcessType type = map(entity.getType().getId());

    return Process.builder()
      .id(entity.getId())
      .name(entity.getName())
      .itemData(map(entity.getItemId()))
      .type(type)
      .status(entity.getStatus())
      .difficulty(entity.getDifficulty())
      .description(entity.getDescription())
      .managerData(map(entity.getManagerId()))
      .commentSubjectId(entity.getCommentSubjectId())
      .info(mapAs(entity.getInfo(), type.getInfoType().getType()))
      .estimatedCost(map(entity.getEstimatedCost()))
      .attachmentId(entity.getAttachmentId())
      .deleted(entity.isDeleted())
      .deletedDate(entity.getDeletedDate())
      .adjustCost(entity.getAdjustCost())
      .adjustCostReason(entity.getAdjustCostReason())
      .build();
  }

  @Mappings({
    @Mapping(target = "itemId", source = "itemData.id"),
    @Mapping(target = "type", source = "type.id"),
    @Mapping(target = "managerId", source = "managerData.id"),
    @Mapping(target = "managerName", source = "managerData.name"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract ProcessEntity map(Process process);

  @Mappings({
    @Mapping(target = "process", source = "process"),
    @Mapping(target = "type", source = "type.id"),
    @Mapping(target = "managerId", source = "managerData.id"),
    @Mapping(target = "managerName", source = "managerData.name"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract PreprocessEntity map(Preprocess preprocess);

  public ProcessType map(ProcessTypeEntity entity) {
    return ProcessType.builder()
      .id(entity.getId())
      .name(entity.getName())
      .baseUnitCost(entity.getBaseUnitCost())
      .infoType(map(entity.getInfoTypeId()))
      .difficultyGrades(
        entity.getDifficultyGrades().stream()
          .map(this::map)
          .collect(Collectors.toList())
      )
      .costRates(
        map(entity.getCostRates())
      )
      .preprocessTypes(
        entity.getPreprocessTypes().stream()
          .map(this::map)
          .collect(Collectors.toList())
      )
      .build();
  }

  public PreprocessType map(PreprocessTypeEntity entity) {
    return PreprocessType.builder()
      .id(entity.getId())
      .name(entity.getName())
      .baseCost(entity.getBaseCost())
      .infoType(map(entity.getInfoTypeId()))
      .build();
  }

  @Mappings({
    @Mapping(target = "infoTypeId", source = "infoType.id"),
    @Mapping(target = "infoTypeName", source = "infoType.name"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract ProcessTypeEntity map(ProcessType processType);

  @Mappings({
    @Mapping(target = "infoTypeId", source = "infoType.id"),
    @Mapping(target = "infoTypeName", source = "infoType.name"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract PreprocessTypeEntity map(PreprocessType preprocessType);

  @Mappings({
    @Mapping(target = "ordinal", expression = "java(grade.getDifficulty().ordinal())")
  })
  public abstract ProcessDifficultyGradeEmbeddable map(ProcessDifficultyGrade grade);

  public ProcessDifficultyGrade map(ProcessDifficultyGradeEmbeddable grade) {
    return ProcessDifficultyGrade.builder()
      .difficulty(grade.getDifficulty())
      .costRate(grade.getCostRate())
      .description(grade.getDescription())
      .build();
  }

  public abstract ProcessCostRatesEmbeddable map(ProcessCostRates rates);

  public ProcessCostRates map(ProcessCostRatesEmbeddable rates) {
    return ProcessCostRates.builder()
      .directLabor(rates.getDirectLabor())
      .indirectLabor(rates.getIndirectLabor())
      .indirectMaterial(rates.getIndirectMaterial())
      .indirectExpenses(rates.getIndirectExpenses())
      .build();
  }

  public abstract ProcessCostEmbeddable map(ProcessCost cost);

  public ProcessCost map(ProcessCostEmbeddable cost) {
    return ProcessCost.builder()
      .directLabor(cost.getDirectLabor())
      .indirectLabor(cost.getIndirectLabor())
      .indirectMaterial(cost.getIndirectMaterial())
      .indirectExpenses(cost.getIndirectExpenses())
      .build();
  }

  public Preprocess map(PreprocessEntity entity) {
    PreprocessType type = map(entity.getType().getId());
    return Preprocess.builder()
      .id(entity.getId())
      .name(entity.getName())
      .process(map(entity.getProcess()))
      .type(type)
      .status(entity.getStatus())
      .description(entity.getDescription())
      .managerData(map(entity.getManagerId()))
      .commentSubjectId(entity.getCommentSubjectId())
      .chargeCost(entity.getChargeCost())
      .info(mapAs(entity.getInfo(), type.getInfoType().getType()))
      .attachmentId(entity.getAttachmentId())
      .deleted(entity.isDeleted())
      .deletedDate(entity.getDeletedDate())
      .build();
  }

  @SneakyThrows
  protected ProcessInfo mapAs(String variables, Class<ProcessInfo> type) {
    if (variables == null) {
      return null;
    }
    return objectMapper.readValue(variables, type);
  }

  public abstract void pass(ProcessTypeEntity from, @MappingTarget ProcessTypeEntity to);

  public abstract void pass(PreprocessTypeEntity from, @MappingTarget PreprocessTypeEntity to);

  @Mappings({
    @Mapping(target = "type", ignore = true)
  })
  public abstract void pass(PreprocessEntity from, @MappingTarget PreprocessEntity to);

  @Mappings({
    @Mapping(target = "type", ignore = true)
  })
  public abstract void pass(ProcessEntity from, @MappingTarget ProcessEntity to);


}
