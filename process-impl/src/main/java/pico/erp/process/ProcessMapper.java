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
import pico.erp.process.cost.ProcessCostRates;
import pico.erp.process.cost.ProcessCostRatesData;
import pico.erp.process.difficulty.grade.ProcessDifficultyGrade;
import pico.erp.process.difficulty.grade.ProcessDifficultyGradeData;
import pico.erp.process.difficulty.grade.ProcessDifficultyGradeMapper;
import pico.erp.process.info.ProcessInfo;
import pico.erp.process.info.ProcessInfoMapper;
import pico.erp.process.info.type.ProcessInfoType;
import pico.erp.process.info.type.ProcessInfoTypeData;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.process.info.type.ProcessInfoTypeMapper;
import pico.erp.process.preprocess.type.PreprocessType;
import pico.erp.process.preprocess.type.PreprocessTypeData;
import pico.erp.process.preprocess.type.PreprocessTypeEntity;
import pico.erp.process.preprocess.type.PreprocessTypeId;
import pico.erp.process.preprocess.type.PreprocessTypeMapper;
import pico.erp.process.type.ProcessType;
import pico.erp.process.type.ProcessTypeData;
import pico.erp.process.type.ProcessTypeEntity;
import pico.erp.process.type.ProcessTypeExceptions.NotFoundException;
import pico.erp.process.type.ProcessTypeId;
import pico.erp.process.type.ProcessTypeMapper;
import pico.erp.process.type.ProcessTypeMessages;
import pico.erp.process.type.ProcessTypeRepository;
import pico.erp.process.type.ProcessTypeRequests;
import pico.erp.shared.event.EventPublisher;
import pico.erp.user.UserData;
import pico.erp.user.UserId;
import pico.erp.user.UserService;

@Mapper
public abstract class ProcessMapper {


  @Autowired
  private ProcessTypeRepository processTypeRepository;


  @Autowired
  private ProcessRepository processRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Lazy
  @Autowired
  private ItemService itemService;

  @Lazy
  @Autowired
  private UserService userService;

  @Lazy
  @Autowired
  protected ProcessInfoMapper processInfoMapper;

  @Lazy
  @Autowired
  protected ProcessDifficultyGradeMapper processDifficultyGradeMapper;

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

  @AfterMapping
  protected void afterMapping(ProcessEntity from, @MappingTarget ProcessEntity to) {
    to.setType(from.getType());
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

  @Mappings({
    @Mapping(target = "infoType", source = "infoTypeId")
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

  public Process domain(ProcessEntity entity) {
    ProcessType type = map(entity.getType().getId());

    return Process.builder()
      .id(entity.getId())
      .name(entity.getName())
      .itemData(map(entity.getItemId()))
      .type(type)
      .status(entity.getStatus())
      .difficulty(entity.getDifficulty())
      .description(entity.getDescription())
      .manager(map(entity.getManagerId()))
      .commentSubjectId(entity.getCommentSubjectId())
      .info(processInfoMapper.map(entity.getInfo(), type.getInfoType().getType()))
      .estimatedCost(processCostMapper.domain(entity.getEstimatedCost()))
      .attachmentId(entity.getAttachmentId())
      .deleted(entity.isDeleted())
      .deletedDate(entity.getDeletedDate())
      .adjustCost(entity.getAdjustCost())
      .adjustCostReason(entity.getAdjustCostReason())
      .build();
  }

  @Mappings({
    @Mapping(target = "infoType", source = "infoTypeId")
  })
  public abstract ProcessTypeMessages.UpdateRequest map(ProcessTypeRequests.UpdateRequest request);

  @Mappings({
    @Mapping(target = "itemId", source = "itemData.id"),
    @Mapping(target = "type", source = "type.id"),
    @Mapping(target = "managerId", source = "manager.id"),
    @Mapping(target = "managerName", source = "manager.name"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract ProcessEntity entity(Process process);

  public abstract ProcessMessages.CompletePlanRequest map(
    ProcessRequests.CompletePlanRequest request);

  public abstract ProcessTypeMessages.DeleteRequest map(ProcessTypeRequests.DeleteRequest request);


  public abstract ProcessMessages.DeleteRequest map(ProcessRequests.DeleteRequest request);

  protected PreprocessTypeEntity entity(PreprocessTypeId preprocessTypeId) {
    return preprocessTypeMapper.entity(preprocessTypeId);
  }


  @Mappings({
    @Mapping(target = "infoTypeId", source = "infoType.id")
  })
  public abstract ProcessTypeData map(ProcessType processType);


  public abstract ProcessInfoTypeData map(ProcessInfoType type);

  protected ProcessTypeEntity entity(ProcessTypeId typeId) {
    return processTypeMapper.entity(typeId);
  }

  public Process map(ProcessId processId) {
    return Optional.ofNullable(processId)
      .map(id -> processRepository.findBy(id)
        .orElseThrow(NotFoundException::new))
      .orElse(null);
  }

  @Mappings({
    @Mapping(target = "itemData", source = "itemId"),
    @Mapping(target = "type", source = "typeId"),
    @Mapping(target = "manager", source = "managerId")
  })
  public abstract ProcessMessages.CreateRequest map(ProcessRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "type", source = "typeId"),
    @Mapping(target = "manager", source = "managerId")
  })
  public abstract ProcessMessages.UpdateRequest map(ProcessRequests.UpdateRequest request);

  @Mappings({
    @Mapping(target = "itemId", source = "itemData.id"),
    @Mapping(target = "typeId", source = "type.id"),
    @Mapping(target = "managerId", source = "manager.id")
  })
  public abstract ProcessData map(Process process);

  protected ProcessDifficultyGrade map(ProcessDifficultyGradeData data) {
    return processDifficultyGradeMapper.map(data);
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

  protected String map(ProcessInfo info) {
    return processInfoMapper.map(info);
  }

  @Mappings({
    @Mapping(target = "type", ignore = true)
  })
  public abstract void pass(ProcessEntity from, @MappingTarget ProcessEntity to);

}
