package pico.erp.process;

import java.util.Optional;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.item.ItemData;
import pico.erp.item.ItemId;
import pico.erp.item.ItemService;
import pico.erp.process.cost.ProcessCostMapper;
import pico.erp.process.difficulty.ProcessDifficultyMapper;
import pico.erp.process.info.ProcessInfoLifecycler;
import pico.erp.process.info.type.ProcessInfoType;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.process.info.type.ProcessInfoTypeMapper;
import pico.erp.process.preparation.type.ProcessPreparationType;
import pico.erp.process.preparation.type.ProcessPreparationTypeData;
import pico.erp.process.preparation.type.ProcessPreparationTypeMapper;
import pico.erp.process.type.ProcessType;
import pico.erp.process.type.ProcessTypeExceptions.NotFoundException;
import pico.erp.process.type.ProcessTypeId;
import pico.erp.process.type.ProcessTypeMapper;
import pico.erp.process.type.ProcessTypeRepository;

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
  private ProcessPreparationTypeMapper processPreparationTypeMapper;

  @Lazy
  @Autowired
  private ProcessInfoTypeMapper processInfoTypeMapper;

  @Lazy
  @Autowired
  protected ItemService itemService;


  protected ProcessType map(ProcessTypeId typeId) {
    return Optional.ofNullable(typeId)
      .map(id -> processTypeRepository.findBy(id)
        .orElseThrow(NotFoundException::new))
      .orElse(null);
  }


  @AfterMapping
  protected void afterMapping(ProcessRequests.CreateRequest request,
    @MappingTarget ProcessMessages.Create.Request message) {
    message.setOrder(
      (int) processRepository.countBy(request.getItemId())
    );
  }


  @AfterMapping
  protected void afterMapping(Process domain, @MappingTarget ProcessEntity entity) {
    entity.setInfo(
      processInfoLifecycler.stringify(domain.getType().getInfoTypeId(), domain.getInfo())
    );
  }


  @Mappings({
    @Mapping(target = "typeId", source = "type.id"),
    @Mapping(target = "itemId", source = "item.id"),
    @Mapping(target = "info", ignore = true),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract ProcessEntity jpa(Process process);

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
      .item(map(entity.getItemId()))
      .order(entity.getOrder())
      .build();
  }

  public abstract ProcessMessages.CompletePlan.Request map(
    ProcessRequests.CompletePlanRequest request);

  public abstract ProcessMessages.Delete.Request map(ProcessRequests.DeleteRequest request);


  public Process map(ProcessId processId) {
    return Optional.ofNullable(processId)
      .map(id -> processRepository.findBy(id)
        .orElseThrow(NotFoundException::new))
      .orElse(null);
  }

  public abstract ProcessMessages.ChangeOrder.Request map(
    ProcessRequests.ChangeOrderRequest request);

  @Mappings({
    @Mapping(target = "type", source = "typeId"),
    @Mapping(target = "processInfoLifecycler", expression = "java(processInfoLifecycler)")
  })
  public abstract ProcessMessages.Update.Request map(ProcessRequests.UpdateRequest request);

  @Mappings({
    @Mapping(target = "typeId", source = "type.id"),
    @Mapping(target = "itemId", source = "item.id")
  })
  public abstract ProcessData map(Process process);





  protected ProcessPreparationTypeData map(ProcessPreparationType type) {
    return processPreparationTypeMapper.map(type);
  }

  protected ProcessInfoType map(ProcessInfoTypeId infoTypeId) {
    return processInfoTypeMapper.map(infoTypeId);
  }

  @Mappings({
    @Mapping(target = "type", source = "typeId"),
    @Mapping(target = "processInfoLifecycler", expression = "java(processInfoLifecycler)"),
    @Mapping(target = "item", source = "itemId"),
    @Mapping(target = "order", ignore = true),
  })
  public abstract ProcessMessages.Create.Request map(ProcessRequests.CreateRequest request);

  public abstract void pass(ProcessEntity from, @MappingTarget ProcessEntity to);

  protected ItemData map(ItemId id) {
    return Optional.ofNullable(id)
      .map(itemService::get)
      .orElse(null);
  }

}
