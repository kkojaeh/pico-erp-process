package pico.erp.process.preprocess;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pico.erp.process.ProcessId;
import pico.erp.process.info.ProcessInfo;
import pico.erp.process.preprocess.type.PreprocessTypeId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreprocessData implements Serializable {

  PreprocessId id;

  String name;

  ProcessId processId;

  PreprocessTypeId typeId;

  PreprocessStatusKind status;

  BigDecimal chargeCost;

  ProcessInfo info;

  String description;

  boolean deleted;

  OffsetDateTime deletedDate;

}
