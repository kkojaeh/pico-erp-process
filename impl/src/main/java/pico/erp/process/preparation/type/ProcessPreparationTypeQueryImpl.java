package pico.erp.process.preparation.type;

import java.util.List;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.ComponentBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.shared.ExtendedLabeledValue;
import pico.erp.shared.data.LabeledValuable;

@SuppressWarnings("Duplicates")
@Service
@ComponentBean
@Transactional(readOnly = true)
@Validated
public class ProcessPreparationTypeQueryImpl implements ProcessPreparationTypeQuery {

  @Autowired
  ProcessPreparationTypeRepository processPreparationTypeRepository;

  @Override
  public List<? extends LabeledValuable> asLabels(String keyword, long limit) {
    final String pattern = String.format(".*%s.*", keyword);
    return processPreparationTypeRepository.findAll()
      .filter(specType -> specType.getName().matches(pattern) || specType.getDescription()
        .matches(pattern))
      .limit(limit)
      .map(specType -> ExtendedLabeledValue.builder()
        .value(specType.getId().getValue())
        .label(specType.getName())
        .subLabel(specType.getDescription())
        .build()
      ).collect(Collectors.toList());
  }

}
