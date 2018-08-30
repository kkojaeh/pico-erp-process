package pico.erp.process.data;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.item.data.ItemId;
import pico.erp.shared.data.Auditor;
import pico.erp.user.data.UserId;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProcessView {

  ProcessId id;

  String name;

  ItemId itemId;

  ProcessTypeId typeId;

  String typeName;

  ProcessStatusKind status;

  ProcessDifficultyKind difficulty;

  UserId managerId;

  String managerName;

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

    ItemId itemId;

    UserId managerId;

    ProcessTypeId processTypeId;

  }

}
