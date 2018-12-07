package pico.erp.process;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pico.erp.attachment.AttachmentId;
import pico.erp.comment.subject.CommentSubjectId;
import pico.erp.item.ItemId;
import pico.erp.process.cost.ProcessCostData;
import pico.erp.process.difficulty.ProcessDifficultyKind;
import pico.erp.process.info.ProcessInfo;
import pico.erp.process.type.ProcessTypeId;
import pico.erp.user.UserId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessData implements Serializable {

  ProcessId id;

  String name;

  ItemId itemId;

  ProcessTypeId typeId;

  ProcessStatusKind status;

  ProcessDifficultyKind difficulty;

  ProcessInfo info;

  String description;

  UserId managerId;

  BigDecimal lossRate;

  CommentSubjectId commentSubjectId;

  ProcessCostData estimatedCost;

  AttachmentId attachmentId;

  boolean deleted;

  OffsetDateTime deletedDate;

  boolean planned;

  BigDecimal adjustCost;

  String adjustCostReason;

}
