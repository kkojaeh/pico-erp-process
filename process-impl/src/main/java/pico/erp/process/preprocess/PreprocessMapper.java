package pico.erp.process.preprocess;

import java.util.Optional;
import lombok.val;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.process.Process;
import pico.erp.process.ProcessId;
import pico.erp.process.ProcessMapper;
import pico.erp.process.info.ProcessInfoLifecycler;
import pico.erp.process.info.type.ProcessInfoType;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.process.info.type.ProcessInfoTypeMapper;
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
  protected ProcessInfoTypeMapper processInfoTypeMapper;

  @Lazy
  @Autowired
  protected PreprocessTypeMapper preprocessTypeMapper;

  @Lazy
  @Autowired
  protected ProcessInfoLifecycler processInfoLifecycler;

  @Lazy
  @Autowired
  private UserService userService;

  @AfterMapping
  protected void afterMapping(Preprocess domain, @MappingTarget PreprocessEntity entity) {
    entity.setInfo(
      processInfoLifecycler.stringify(domain.getType().getInfoTypeId(), domain.getInfo())
    );
  }

  @Mappings({
    @Mapping(target = "processId", source = "process.id"),
    @Mapping(target = "typeId", source = "type.id"),
    @Mapping(target = "info", ignore = true),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract PreprocessEntity jpa(Preprocess preprocess);

  public Preprocess jpa(PreprocessEntity entity) {
    val type = map(entity.getTypeId());
    return Preprocess.builder()
      .id(entity.getId())
      .name(entity.getName())
      .process(map(entity.getProcessId()))
      .type(type)
      .status(entity.getStatus())
      .description(entity.getDescription())
      .chargeCost(entity.getChargeCost())
      .info(processInfoLifecycler.parse(type.getInfoTypeId(), entity.getInfo()))
      .deleted(entity.isDeleted())
      .deletedDate(entity.getDeletedDate())
      .build();
  }

  @Mappings({
    @Mapping(target = "processInfoLifecycler", expression = "java(processInfoLifecycler)")
  })
  public abstract PreprocessMessages.UpdateRequest map(PreprocessRequests.UpdateRequest request);

  public abstract PreprocessMessages.DeleteRequest map(PreprocessRequests.DeleteRequest request);

  @Mappings({
    @Mapping(target = "processId", source = "process.id"),
    @Mapping(target = "typeId", source = "type.id")
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

  @Mappings({
    @Mapping(target = "process", source = "processId"),
    @Mapping(target = "type", source = "typeId"),
    @Mapping(target = "processInfoLifecycler", expression = "java(processInfoLifecycler)")
  })
  public abstract PreprocessMessages.CreateRequest map(PreprocessRequests.CreateRequest request);

  protected ProcessInfoType map(ProcessInfoTypeId preprocessTypeId) {
    return processInfoTypeMapper.map(preprocessTypeId);
  }


  @Mappings({
  })
  public abstract void pass(PreprocessEntity from, @MappingTarget PreprocessEntity to);

}
