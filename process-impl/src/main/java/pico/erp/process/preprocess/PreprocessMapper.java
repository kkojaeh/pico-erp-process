package pico.erp.process.preprocess;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.process.Process;
import pico.erp.process.ProcessId;
import pico.erp.process.ProcessMapper;
import pico.erp.process.info.ProcessInfo;
import pico.erp.process.info.ProcessInfoMapper;
import pico.erp.process.preprocess.type.PreprocessType;
import pico.erp.process.preprocess.type.PreprocessTypeId;
import pico.erp.process.preprocess.type.PreprocessTypeMapper;
import pico.erp.user.UserData;
import pico.erp.user.UserId;
import pico.erp.user.UserService;

@Mapper
public abstract class PreprocessMapper {


  @Lazy
  @Autowired
  protected ProcessMapper processMapper;

  @Lazy
  @Autowired
  protected PreprocessTypeMapper preprocessTypeMapper;

  @Lazy
  @Autowired
  protected ProcessInfoMapper processInfoMapper;

  @Lazy
  @Autowired
  private UserService userService;

  public Preprocess jpa(PreprocessEntity entity) {
    PreprocessType type = map(entity.getTypeId());
    return Preprocess.builder()
      .id(entity.getId())
      .name(entity.getName())
      .process(map(entity.getProcessId()))
      .type(type)
      .status(entity.getStatus())
      .description(entity.getDescription())
      .manager(map(entity.getManagerId()))
      .commentSubjectId(entity.getCommentSubjectId())
      .chargeCost(entity.getChargeCost())
      .info(processInfoMapper.map(entity.getInfo(), type.getInfoType().getType()))
      .attachmentId(entity.getAttachmentId())
      .deleted(entity.isDeleted())
      .deletedDate(entity.getDeletedDate())
      .build();
  }

  @Mappings({
    @Mapping(target = "processId", source = "process.id"),
    @Mapping(target = "typeId", source = "type.id"),
    @Mapping(target = "managerId", source = "manager.id"),
    @Mapping(target = "managerName", source = "manager.name"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract PreprocessEntity jpa(Preprocess preprocess);

  @Mappings({
    @Mapping(target = "process", source = "processId"),
    @Mapping(target = "type", source = "typeId"),
    @Mapping(target = "manager", source = "managerId")
  })
  public abstract PreprocessMessages.CreateRequest map(PreprocessRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "manager", source = "managerId")
  })
  public abstract PreprocessMessages.UpdateRequest map(PreprocessRequests.UpdateRequest request);

  public abstract PreprocessMessages.DeleteRequest map(PreprocessRequests.DeleteRequest request);

  @Mappings({
    @Mapping(target = "processId", source = "process.id"),
    @Mapping(target = "typeId", source = "type.id"),
    @Mapping(target = "managerId", source = "manager.id")
  })
  public abstract PreprocessData map(Preprocess preprocess);

  protected UserData map(UserId userId) {
    return Optional.ofNullable(userId)
      .map(userService::get)
      .orElse(null);
  }

  protected Process map(ProcessId processId) {
    return processMapper.map(processId);
  }

  protected PreprocessType map(PreprocessTypeId preprocessTypeId) {
    return preprocessTypeMapper.map(preprocessTypeId);
  }

  protected String map(ProcessInfo info) {
    return processInfoMapper.map(info);
  }

  @Mappings({
  })
  public abstract void pass(PreprocessEntity from, @MappingTarget PreprocessEntity to);

}
