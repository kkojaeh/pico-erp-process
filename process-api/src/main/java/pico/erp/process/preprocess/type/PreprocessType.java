package pico.erp.process.preprocess.type;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import pico.erp.process.info.type.ProcessInfoTypeId;

public interface PreprocessType {

  BigDecimal getBaseCost();

  String getDescription();

  PreprocessTypeId getId();

  ProcessInfoTypeId getInfoTypeId();

  String getName();

  @Getter
  @Builder
  class PreprocessTypeImpl implements PreprocessType {

    PreprocessTypeId id;

    String name;

    String description;

    BigDecimal baseCost;

    ProcessInfoTypeId infoTypeId;


  }

}
