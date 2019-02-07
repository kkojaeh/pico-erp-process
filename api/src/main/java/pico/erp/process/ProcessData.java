package pico.erp.process;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pico.erp.item.ItemId;
import pico.erp.process.cost.ProcessCostData;
import pico.erp.process.difficulty.ProcessDifficultyKind;
import pico.erp.process.info.ProcessInfo;
import pico.erp.process.type.ProcessTypeId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessData implements Serializable {

  ProcessId id;

  String name;

  ProcessTypeId typeId;

  ProcessStatusKind status;

  ProcessDifficultyKind difficulty;

  ProcessInfo info;

  String description;

  BigDecimal lossRate;

  ProcessCostData estimatedCost;

  boolean deleted;

  OffsetDateTime deletedDate;

  boolean planned;

  BigDecimal adjustCost;

  String adjustCostReason;

  ItemId itemId;

  int order;

  public ProcessRequests.UpdateRequest toUpdate() {
    return ProcessRequests.UpdateRequest.builder()
      .id(id)
      .difficulty(difficulty)
      .typeId(typeId)
      .info(info)
      .description(description)
      .lossRate(lossRate)
      .adjustCost(adjustCost)
      .adjustCostReason(adjustCostReason)
      .build();
  }

}
