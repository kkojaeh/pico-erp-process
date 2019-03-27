package pico.erp.process;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.process.difficulty.ProcessDifficultyKind;
import pico.erp.process.type.ProcessTypeId;
import pico.erp.shared.data.Auditor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProcessView {

  ProcessId id;

  String name;

  ProcessTypeId typeId;

  String typeName;

  ProcessStatusKind status;

  ProcessDifficultyKind difficulty;

  BigDecimal lossRate;

  Auditor createdBy;

  LocalDateTime createdDate;

  Auditor lastModifiedBy;

  LocalDateTime lastModifiedDate;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    String name;

    ProcessTypeId typeId;

  }

}
