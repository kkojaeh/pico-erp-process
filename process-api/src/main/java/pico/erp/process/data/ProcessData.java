package pico.erp.process.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pico.erp.attachment.data.AttachmentId;
import pico.erp.comment.data.CommentSubjectId;
import pico.erp.item.data.ItemId;
import pico.erp.user.data.UserId;

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

  CommentSubjectId commentSubjectId;

  ProcessCostData estimatedCost;

  AttachmentId attachmentId;

  boolean deleted;

  OffsetDateTime deletedDate;

  boolean planned;

  BigDecimal adjustCost;

  String adjustCostReason;

}
