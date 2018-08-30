package pico.erp.process.core;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.process.ProcessInfoTypExceptions.NotFoundException;
import pico.erp.process.ProcessInfoTypeService;
import pico.erp.process.data.ProcessInfoTypeData;
import pico.erp.process.data.ProcessInfoTypeId;
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
