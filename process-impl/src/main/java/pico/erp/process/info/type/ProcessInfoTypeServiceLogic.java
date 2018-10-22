package pico.erp.process.info.type;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.process.ProcessMapper;
import pico.erp.process.info.type.ProcessInfoTypExceptions.NotFoundException;
import pico.erp.shared.Public;

@Service
@Public
@Transactional
@Validated
public class ProcessInfoTypeServiceLogic implements ProcessInfoTypeService {

  @Autowired
  ProcessInfoTypeRepository processInfoTypeRepository;

  @Autowired
  ProcessMapper mapper;

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
}