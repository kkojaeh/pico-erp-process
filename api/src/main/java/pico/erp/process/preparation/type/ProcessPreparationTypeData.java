package pico.erp.process.preparation.type;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessPreparationTypeData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProcessPreparationTypeId id;

  String name;

  String description;

  BigDecimal baseCost;

}
