package pico.erp.process.info.type;

import java.util.List;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.ComponentBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.process.info.type.ProcessInfoTypExceptions.NotFoundException;

@Service
@ComponentBean
@Transactional
@Validated
public class ProcessInfoTypeServiceLogic implements ProcessInfoTypeService {

  @Autowired
  ProcessInfoTypeRepository processInfoTypeRepository;

  @Autowired
  ProcessInfoTypeMapper mapper;

  @Override
  public ProcessInfoTypeData get(ProcessInfoTypeId id) {
    return processInfoTypeRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(NotFoundException::new);
  }

  @Override
  public List<ProcessInfoTypeData> getAll() {
    return processInfoTypeRepository.findAll()
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public boolean exists(ProcessInfoTypeId id) {
    return processInfoTypeRepository.exists(id);
  }
}
