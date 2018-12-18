package pico.erp.process.preparation;

import lombok.val;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.process.Process;
import pico.erp.process.ProcessId;
import pico.erp.process.ProcessMapper;
import pico.erp.process.info.type.ProcessInfoType;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.process.info.type.ProcessInfoTypeMapper;
import pico.erp.process.preparation.type.ProcessPreparationType;
import pico.erp.process.preparation.type.ProcessPreparationTypeId;
import pico.erp.process.preparation.type.ProcessPreparationTypeMapper;

@Mapper
public abstract class ProcessPreparationMapper {


  @Lazy
  @Autowired
  protected ProcessMapper processMapper;

  @Lazy
  protected ProcessInfoTypeMapper processInfoTypeMapper;

  @Lazy
  @Autowired
  protected ProcessPreparationTypeMapper processPreparationTypeMapper;

  @Mappings({
    @Mapping(target = "processId", source = "process.id"),
    @Mapping(target = "typeId", source = "type.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract ProcessPreparationEntity jpa(ProcessPreparation processPreparation);

  public ProcessPreparation jpa(ProcessPreparationEntity entity) {
    val type = map(entity.getTypeId());
    return ProcessPreparation.builder()
      .id(entity.getId())
      .name(entity.getName())
      .process(map(entity.getProcessId()))
      .type(type)
      .status(entity.getStatus())
      .description(entity.getDescription())
      .chargeCost(entity.getChargeCost())
      .deleted(entity.isDeleted())
      .deletedDate(entity.getDeletedDate())
      .build();
  }

  @Mappings({
  })
  public abstract ProcessPreparationMessages.UpdateRequest map(
    ProcessPreparationRequests.UpdateRequest request);

  public abstract ProcessPreparationMessages.DeleteRequest map(
    ProcessPreparationRequests.DeleteRequest request);

  public abstract ProcessPreparationMessages.CommitRequest map(
    ProcessPreparationRequests.CommitRequest request);

  public abstract ProcessPreparationMessages.CompleteRequest map(
    ProcessPreparationRequests.CompleteRequest request);

  public abstract ProcessPreparationMessages.CancelRequest map(
    ProcessPreparationRequests.CancelRequest request);



  @Mappings({
    @Mapping(target = "processId", source = "process.id"),
    @Mapping(target = "typeId", source = "type.id")
  })
  public abstract ProcessPreparationData map(ProcessPreparation processPreparation);

  protected Process map(ProcessId processId) {
    return processMapper.map(processId);
  }

  protected ProcessPreparationType map(ProcessPreparationTypeId processPreparationTypeId) {
    return processPreparationTypeMapper.map(processPreparationTypeId);
  }

  @Mappings({
    @Mapping(target = "process", source = "processId"),
    @Mapping(target = "type", source = "typeId")
  })
  public abstract ProcessPreparationMessages.CreateRequest map(
    ProcessPreparationRequests.CreateRequest request);

  protected ProcessInfoType map(ProcessInfoTypeId preprocessTypeId) {
    return processInfoTypeMapper.map(preprocessTypeId);
  }


  @Mappings({
  })
  public abstract void pass(
    ProcessPreparationEntity from, @MappingTarget ProcessPreparationEntity to);

}
