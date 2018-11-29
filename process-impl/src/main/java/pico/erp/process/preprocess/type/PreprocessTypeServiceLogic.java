package pico.erp.process.preprocess.type;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.shared.Public;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class PreprocessTypeServiceLogic implements PreprocessTypeService {

  @Autowired
  private PreprocessTypeRepository preprocessTypeRepository;


  @Autowired
  private PreprocessTypeMapper mapper;

  @Override
  public boolean exists(PreprocessTypeId id) {
    return preprocessTypeRepository.exists(id);
  }

  @Override
  public PreprocessTypeData get(PreprocessTypeId id) {
    return preprocessTypeRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(PreprocessTypeExceptions.NotFoundException::new);
  }

  @Override
  public List<PreprocessTypeData> getAll() {
    return preprocessTypeRepository.findAll()
      .map(mapper::map)
      .collect(Collectors.toList());
  }

}
