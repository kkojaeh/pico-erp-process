package pico.erp.process;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.item.ItemId;
import pico.erp.process.difficulty.grade.ProcessDifficultyKind;
import pico.erp.process.type.ProcessTypeId;
import pico.erp.shared.data.Auditor;
import pico.erp.user.UserId;

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