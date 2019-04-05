package pico.erp.process.preparation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pico.erp.process.ProcessId;
import pico.erp.process.preparation.type.ProcessPreparationTypeId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessPreparationData implements Serializable {

  ProcessPreparationId id;

  String name;

  ProcessId processId;

  ProcessPreparationTypeId typeId;

  ProcessPreparationStatusKind status;

  BigDecimal chargeCost;

  String description;

  boolean deleted;

  OffsetDateTime deletedDate;

  boolean updatable;

  boolean committable;

  boolean completable;

  boolean cancelable;

  boolean done;

}
