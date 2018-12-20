package pico.erp.process.info.type;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper
public class ProcessInfoTypeMapper {

  @Lazy
  @Autowired
  private ProcessInfoTypeRepository processInfoTypeRepository;

  public ProcessInfoType map(ProcessInfoTypeId infoTypeId) {
    return Optional.ofNullable(infoTypeId)
      .map(id -> processInfoTypeRepository.findBy(id)
        .orElseThrow(ProcessInfoTypExceptions.NotFoundException::new)
      )
      .orElse(null);
  }

}
