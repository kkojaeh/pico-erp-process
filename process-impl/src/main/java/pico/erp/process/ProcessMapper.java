package pico.erp.process;

import java.util.Optional;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.process.cost.ProcessCostMapper;
import pico.erp.process.cost.ProcessCostRates;
import pico.erp.process.cost.ProcessCostRatesData;
import pico.erp.process.difficulty.ProcessDifficulty;
import pico.erp.process.difficulty.ProcessDifficultyData;
import pico.erp.process.difficulty.ProcessDifficultyMapper;
import pico.erp.process.info.ProcessInfoLifecycler;
import pico.erp.process.info.type.ProcessInfoType;
import pico.erp.process.info.type.ProcessInfoTypeData;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.process.info.type.ProcessInfoTypeMapper;
import pico.erp.process.preprocess.type.PreprocessType;
import pico.erp.process.preprocess.type.PreprocessTypeData;
import pico.erp.process.preprocess.type.PreprocessTypeId;
import pico.erp.process.preprocess.type.PreprocessTypeMapper;
import pico.erp.process.type.ProcessType;
import pico.erp.process.type.ProcessTypeData;
import pico.erp.process.type.ProcessTypeExceptions.NotFoundException;
import pico.erp.process.type.ProcessTypeId;
import pico.erp.process.type.ProcessTypeMapper;
import pico.erp.process.type.ProcessTypeMessages;
import pico.erp.process.type.ProcessTypeRepository;
import pico.erp.process.type.ProcessTypeRequests;

@Mapper
public abstract class ProcessMapper {

  @Lazy
  @Autowired
  private ProcessTypeRepository processTypeRepository;

  @Lazy
  @Autowired
  private ProcessRepository processRepository;

  @Lazy
  @Autowired
  protected ProcessInfoLifecycler processInfoLifecycler;

  @Lazy
  @Autowired
  protected ProcessDifficultyMapper processDifficultyMapper;

  @Lazy
  @Autowired
  protected ProcessTypeMapper processTypeMapper;

  @Lazy
  @Autowired
  private ProcessCostMapper processCostMapper;

  @Lazy
  @Autowired
  private PreprocessTypeMapper preprocessTypeMapper;

  @Lazy
  @Autowired
  private ProcessInfoTypeMapper processInfoTypeMapper;


  protected ProcessType map(ProcessTypeId typeId) {
    return Optional.ofNullable(typeId)
      .map(id -> processTypeRepository.findBy(id)
        .orElseThrow(NotFoundException::new))
      .orElse(null);
  }

  @Mappings({
  })
  public abstract ProcessTypeMessages.CreateRequest map(ProcessTypeRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "preprocessType", source = "preprocessTypeId")
  })
  public abstract ProcessTypeMessages.AddPreprocessTypeRequest map(
    ProcessTypeRequests.AddPreprocessTypeRequest request);

  @Mappings({
    @Mapping(target = "preprocessType", source = "preprocessTypeId")
  })
  public abstract ProcessTypeMessages.RemovePreprocessTypeRequest map(
    ProcessTypeRequests.RemovePreprocessTypeRequest request);

  @AfterMapping
  protected void afterMapping(Process domain, @MappingTarget ProcessEntity entity) {
    entity.setInfo(
      processInfoLifecycler.stringify(domain.getType().getInfoTypeId(), domain.getInfo())
    );
  }

  @Mappings({
  })
  public abstract ProcessTypeMessages.UpdateRequest map(ProcessTypeRequests.UpdateRequest request);

  @Mappings({
    @Mapping(target = "typeId", source = "type.id"),
    @Mapping(target = "info", ignore = true),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract ProcessEntity jpa(Process process);

  public abstract ProcessMessages.CompletePlanRequest map(
    ProcessRequests.CompletePlanRequest request);

  public abstract ProcessTypeMessages.DeleteRequest map(ProcessTypeRequests.DeleteRequest request);


  public abstract ProcessMessages.DeleteRequest map(ProcessRequests.DeleteRequest request);

  @Mappings({
  })
  public abstract ProcessTypeData map(ProcessType processType);


  public abstract ProcessInfoTypeData map(ProcessInfoType type);

  public Process map(ProcessId processId) {
    return Optional.ofNullable(processId)
      .map(id -> processRepository.findBy(id)
        .orElseThrow(NotFoundException::new))
      .orElse(null);
  }

  public Process jpa(ProcessEntity entity) {
    ProcessType type = map(entity.getTypeId());

    return Process.builder()
      .id(entity.getId())
      .name(entity.getName())
      .type(type)
      .status(entity.getStatus())
      .difficulty(entity.getDifficulty())
      .description(entity.getDescription())
      .info(processInfoLifecycler.parse(type.getInfoTypeId(), entity.getInfo()))
      .estimatedCost(processCostMapper.domain(entity.getEstimatedCost()))
      .deleted(entity.isDeleted())
      .deletedDate(entity.getDeletedDate())
      .adjustCost(entity.getAdjustCost())
      .adjustCostReason(entity.getAdjustCostReason())
      .lossRate(entity.getLossRate())
      .build();
  }

  @Mappings({
    @Mapping(target = "type", source = "typeId"),
    @Mapping(target = "processInfoLifecycler", expression = "java(processInfoLifecycler)")
  })
  public abstract ProcessMessages.UpdateRequest map(ProcessRequests.UpdateRequest request);

  @Mappings({
    @Mapping(target = "typeId", source = "type.id")
  })
  public abstract ProcessData map(Process process);

  protected ProcessDifficulty map(ProcessDifficultyData data) {
    return processDifficultyMapper.map(data);
  }

  protected ProcessCostRates map(ProcessCostRatesData data) {
    return processCostMapper.map(data);
  }

  protected PreprocessType map(PreprocessTypeId typeId) {
    return preprocessTypeMapper.map(typeId);
  }

  protected PreprocessTypeData map(PreprocessType type) {
    return preprocessTypeMapper.map(type);
  }

  protected ProcessInfoType map(ProcessInfoTypeId infoTypeId) {
    return processInfoTypeMapper.map(infoTypeId);
  }

  @Mappings({
    @Mapping(target = "type", source = "typeId"),
    @Mapping(target = "processInfoLifecycler", expression = "java(processInfoLifecycler)")
  })
  public abstract ProcessMessages.CreateRequest map(ProcessRequests.CreateRequest request);

  public abstract void pass(ProcessEntity from, @MappingTarget ProcessEntity to);

}
