package pico.erp.process.preprocess;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pico.erp.attachment.AttachmentId;
import pico.erp.comment.subject.CommentSubjectId;
import pico.erp.process.ProcessId;
import pico.erp.process.info.ProcessInfo;
import pico.erp.process.preprocess.type.PreprocessTypeId;
import pico.erp.user.UserId;

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
