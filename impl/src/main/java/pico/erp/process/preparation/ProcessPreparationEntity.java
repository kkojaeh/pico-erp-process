package pico.erp.process.preparation;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Lob;
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
import pico.erp.process.ProcessId;
import pico.erp.process.preparation.type.ProcessPreparationTypeId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Auditor;

@Entity(name = "ProcessPreparation")
@Table(name = "PRS_PROCESS_PREPARATION", indexes = {
  @Index(columnList = "PROCESS_ID")
})
@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessPreparationEntity implements Serializable {

  private static final long serialVersionUID = 1L;


  @EmbeddedId
  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  ProcessPreparationId id;

  @Column(length = TypeDefinitions.NAME_LENGTH)
  String name;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "PROCESS_ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  ProcessId processId;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "TYPE_ID", length = TypeDefinitions.ID_LENGTH))
  })
  ProcessPreparationTypeId typeId;

  @Enumerated(EnumType.STRING)
  @Column(length = TypeDefinitions.ENUM_LENGTH)
  ProcessPreparationStatusKind status;

  @Lob
  @Column(length = TypeDefinitions.CLOB_LENGTH)
  String description;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "CREATED_BY_ID", updatable = false, length = TypeDefinitions.ID_LENGTH)),
    @AttributeOverride(name = "name", column = @Column(name = "CREATED_BY_NAME", updatable = false, length = TypeDefinitions.NAME_LENGTH))
  })
  @CreatedBy
  Auditor createdBy;

  @CreatedDate
  @Column(updatable = false)
  LocalDateTime createdDate;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "LAST_MODIFIED_BY_ID", length = TypeDefinitions.ID_LENGTH)),
    @AttributeOverride(name = "name", column = @Column(name = "LAST_MODIFIED_BY_NAME", length = TypeDefinitions.NAME_LENGTH))
  })
  @LastModifiedBy
  Auditor lastModifiedBy;

  @LastModifiedDate
  LocalDateTime lastModifiedDate;

  boolean deleted;

  LocalDateTime deletedDate;

  @Column(scale = 2)
  BigDecimal chargeCost;

  LocalDateTime canceledDate;

  LocalDateTime committedDate;

  LocalDateTime completedDate;

}
