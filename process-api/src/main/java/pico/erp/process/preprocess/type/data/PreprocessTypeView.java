package pico.erp.process.preprocess.type.data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.process.info.type.data.ProcessInfoTypeId;
import pico.erp.shared.data.Auditor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PreprocessTypeView {

  PreprocessTypeId id;

  String name;

  BigDecimal baseCost;

  ProcessInfoTypeId infoTypeId;

  String infoTypeName;

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

    ProcessInfoTypeId processInfoTypeId;

  }

}
