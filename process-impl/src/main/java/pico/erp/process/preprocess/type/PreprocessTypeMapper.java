package pico.erp.process.preprocess.type;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.process.info.type.ProcessInfoType;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.process.info.type.ProcessInfoTypeMapper;
import pico.erp.process.type.ProcessTypeExceptions.NotFoundException;

@Mapper
public abstract class PreprocessTypeMapper {

  @Lazy
  @Autowired
  private PreprocessTypeRepository preprocessTypeRepository;

  @Lazy
  @Autowired
  private PreprocessTypeEntityRepository preprocessTypeEntityRepository;

  @Lazy
  @Autowired
  private ProcessInfoTypeMapper processInfoTypeMapper;

  public PreprocessType domain(PreprocessTypeEntity entity) {
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
  public abstract PreprocessTypeEntity entity(PreprocessType preprocessType);

  public PreprocessTypeEntity entity(PreprocessTypeId preprocessTypeId) {
    return Optional.ofNullable(preprocessTypeId)
      .map(preprocessTypeEntityRepository::findOne)
      .orElse(null);
  }

  @Mappings({
    @Mapping(target = "infoType", source = "infoTypeId")
  })
  public abstract PreprocessTypeMessages.CreateRequest map(
    PreprocessTypeRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "infoType", source = "infoTypeId")
  })
  public abstract PreprocessTypeMessages.UpdateRequest map(
    PreprocessTypeRequests.UpdateRequest request);

  public abstract PreprocessTypeMessages.DeleteRequest map(
    PreprocessTypeRequests.DeleteRequest request);

  @Mappings({
    @Mapping(target = "infoTypeId", source = "infoType.id")
  })
  public abstract PreprocessTypeData map(PreprocessType processType);

  public PreprocessType map(PreprocessTypeId typeId) {
    return Optional.ofNullable(typeId)
      .map(id -> preprocessTypeRepository.findBy(id)
        .orElseThrow(NotFoundException::new))
      .orElse(null);
  }

  protected ProcessInfoType map(ProcessInfoTypeId infoTypeId) {
    return processInfoTypeMapper.map(infoTypeId);
  }

  public abstract void pass(PreprocessTypeEntity from, @MappingTarget PreprocessTypeEntity to);


}
