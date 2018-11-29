package pico.erp.process.preprocess.type;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.process.info.type.ProcessInfoType;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.process.info.type.ProcessInfoTypeMapper;

@Mapper
public abstract class PreprocessTypeMapper {

  @Lazy
  @Autowired
  private PreprocessTypeRepository preprocessTypeRepository;

  @Lazy
  @Autowired
  private ProcessInfoTypeMapper processInfoTypeMapper;

  @Mappings({
  })
  public abstract PreprocessTypeData map(PreprocessType processType);

  public PreprocessType map(PreprocessTypeId typeId) {
    return Optional.ofNullable(typeId)
      .map(id -> preprocessTypeRepository.findBy(id)
        .orElseThrow(PreprocessTypeExceptions.NotFoundException::new))
      .orElse(null);
  }

  protected ProcessInfoType map(ProcessInfoTypeId infoTypeId) {
    return processInfoTypeMapper.map(infoTypeId);
  }

}
