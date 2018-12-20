package pico.erp.process.preparation.type;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.process.info.type.ProcessInfoType;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.process.info.type.ProcessInfoTypeMapper;

@Mapper
public abstract class ProcessPreparationTypeMapper {

  @Lazy
  @Autowired
  private ProcessPreparationTypeRepository processPreparationTypeRepository;

  @Lazy
  @Autowired
  private ProcessInfoTypeMapper processInfoTypeMapper;

  @Mappings({
  })
  public abstract ProcessPreparationTypeData map(ProcessPreparationType processType);

  public ProcessPreparationType map(ProcessPreparationTypeId typeId) {
    return Optional.ofNullable(typeId)
      .map(id -> processPreparationTypeRepository.findBy(id)
        .orElseThrow(ProcessPreparationTypeExceptions.NotFoundException::new))
      .orElse(null);
  }

  protected ProcessInfoType map(ProcessInfoTypeId infoTypeId) {
    return processInfoTypeMapper.map(infoTypeId);
  }

}
