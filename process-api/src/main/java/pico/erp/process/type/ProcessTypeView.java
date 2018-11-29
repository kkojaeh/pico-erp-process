package pico.erp.process.type;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.shared.data.Auditor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProcessTypeView {

  ProcessTypeId id;

  String name;

  BigDecimal baseUnitCost;

  BigDecimal lossRate;

  ProcessInfoTypeId infoTypeId;

  Auditor createdBy;

  OffsetDateTime createdDate;

  Auditor lastModifiedBy;

  OffsetDateTime lastModifiedDate;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    String name;

    ProcessInfoTypeId infoTypeId;

  }

}
