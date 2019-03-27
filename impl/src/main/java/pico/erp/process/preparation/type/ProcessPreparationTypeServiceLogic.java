package pico.erp.process.preparation.type;

import java.util.List;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.Give;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@SuppressWarnings("Duplicates")
@Service
@Give
@Transactional
@Validated
public class ProcessPreparationTypeServiceLogic implements ProcessPreparationTypeService {

  @Autowired
  private ProcessPreparationTypeRepository processPreparationTypeRepository;


  @Autowired
  private ProcessPreparationTypeMapper mapper;

  @Override
  public boolean exists(ProcessPreparationTypeId id) {
    return processPreparationTypeRepository.exists(id);
  }

  @Override
  public ProcessPreparationTypeData get(ProcessPreparationTypeId id) {
    return processPreparationTypeRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(ProcessPreparationTypeExceptions.NotFoundException::new);
  }

  @Override
  public List<ProcessPreparationTypeData> getAll() {
    return processPreparationTypeRepository.findAll()
      .map(mapper::map)
      .collect(Collectors.toList());
  }

}
