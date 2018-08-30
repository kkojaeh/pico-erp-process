package pico.erp.process.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pico.erp.attachment.data.AttachmentId;
import pico.erp.comment.data.CommentSubjectId;
import pico.erp.user.data.UserId;

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

  UserId managerId;

  CommentSubjectId commentSubjectId;

  AttachmentId attachmentId;

  boolean deleted;

  OffsetDateTime deletedDate;

}
