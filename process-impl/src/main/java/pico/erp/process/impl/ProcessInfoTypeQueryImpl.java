package pico.erp.process.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.process.ProcessInfoTypeQuery;
import pico.erp.process.core.ProcessInfoTypeRepository;
import pico.erp.shared.ExtendedLabeledValue;
import pico.erp.shared.Public;
import pico.erp.shared.data.LabeledValuable;

@Service
@Public
@Transactional(readOnly = true)
@Validated
public class ProcessInfoTypeQueryImpl implements ProcessInfoTypeQuery {

  @Autowired
  ProcessInfoTypeRepository processInfoTypeRepository;

  @Override
  public List<? extends LabeledValuable> asLabels(String keyword, long limit) {
    final String pattern = String.format(".*%s.*", keyword);
    return processInfoTypeRepository.findAll()
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
