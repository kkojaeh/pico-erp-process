package pico.erp.process.preprocess;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pico.erp.attachment.AttachmentId;
import pico.erp.comment.subject.CommentSubjectId;
import pico.erp.process.ProcessEntity;
import pico.erp.process.preprocess.type.PreprocessTypeEntity;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Auditor;
import pico.erp.user.UserId;

@Entity(name = "Preprocess")
@Table(name = "PRS_PREPROCESS")
@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreprocessEntity implements Serializable {

  private static final long serialVersionUID = 1L;


  @EmbeddedId
  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ID", length = TypeDefinitions.ID_LENGTH))
  })
  PreprocessId id;

  @Column(length = TypeDefinitions.NAME_X3_LENGTH)
  String name;

  @ManyToOne
  @JoinColumn(name = "PROCESS_ID")
  ProcessEntity process;

  @ManyToOne
  @JoinColumn(name = "TYPE_ID")
  PreprocessTypeEntity type;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "MANAGER_ID", length = TypeDefinitions.ID_LENGTH))
  })
  UserId managerId;

  @Column(length = TypeDefinitions.NAME_LENGTH)
  String managerName;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "COMMENT_SUBJECT_ID", length = TypeDefinitions.ID_LENGTH))
  })
  CommentSubjectId commentSubjectId;

  @Enumerated(EnumType.STRING)
  @Column(length = TypeDefinitions.ENUM_LENGTH)
  PreprocessStatusKind status;

  @Lob
  @Column(length = TypeDefinitions.CLOB_LENGTH)
  String info;

  @Lob
  @Column(length = TypeDefinitions.CLOB_LENGTH)
  String description;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ATTACHMENT_ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  AttachmentId attachmentId;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "CREATED_BY_ID", updatable = false, length = TypeDefinitions.ID_LENGTH)),
    @AttributeOverride(name = "name", column = @Column(name = "CREATED_BY_NAME", updatable = false, length = TypeDefinitions.NAME_LENGTH))
  })
  @CreatedBy
  Auditor createdBy;

  @CreatedDate
  @Column(updatable = false)
  OffsetDateTime createdDate;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "LAST_MODIFIED_BY_ID", length = TypeDefinitions.ID_LENGTH)),
    @AttributeOverride(name = "name", column = @Column(name = "LAST_MODIFIED_BY_NAME", length = TypeDefinitions.NAME_LENGTH))
  })
  @LastModifiedBy
  Auditor lastModifiedBy;

  @LastModifiedDate
  OffsetDateTime lastModifiedDate;

  boolean deleted;

  OffsetDateTime deletedDate;

  @Column(scale = 2)
  BigDecimal chargeCost;

}
