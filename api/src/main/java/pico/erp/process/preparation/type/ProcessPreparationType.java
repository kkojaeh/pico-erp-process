package pico.erp.process.preparation.type;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

public interface ProcessPreparationType {

  BigDecimal getBaseCost();

  String getDescription();

  ProcessPreparationTypeId getId();

  String getName();

  @Getter
  @Builder
  class ProcessPreparationTypeImpl implements ProcessPreparationType {

    ProcessPreparationTypeId id;

    String name;

    String description;

    BigDecimal baseCost;

  }

}
