package pico.erp.process.preprocess.type;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pico.erp.process.info.type.ProcessInfoTypeId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreprocessTypeData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  PreprocessTypeId id;

  String name;

  ProcessInfoTypeId infoTypeId;

  BigDecimal baseCost;

}
