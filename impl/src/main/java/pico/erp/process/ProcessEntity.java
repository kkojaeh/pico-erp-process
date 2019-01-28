package pico.erp.process;


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
import pico.erp.item.ItemId;
import pico.erp.process.cost.ProcessCostEmbeddable;
import pico.erp.process.difficulty.ProcessDifficultyKind;
import pico.erp.process.type.ProcessTypeId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Auditor;

@Entity(name = "Process")
@Table(name = "PRS_PROCESS", indexes = {
  @Index(columnList = "ITEM_ID", unique = false)
})
@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessEntity implements Serializable {

  private static final long serialVersionUID = 1L;


  @EmbeddedId
  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  ProcessId id;

  @Column(length = TypeDefinitions.NAME_LENGTH)
  String name;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "TYPE_ID", length = TypeDefinitions.ID_LENGTH))
  })
  ProcessTypeId typeId;

  @Column(precision = 7, scale = 5)
  BigDecimal lossRate;

  @Enumerated(EnumType.STRING)
  @Column(length = TypeDefinitions.ENUM_LENGTH)
  ProcessStatusKind status;

  @Enumerated(EnumType.STRING)
  @Column(length = TypeDefinitions.ENUM_LENGTH)
  ProcessDifficultyKind difficulty;

  @Lob
  @Column(length = TypeDefinitions.CLOB_LENGTH)
  String info;

  @Lob
  @Column(length = TypeDefinitions.CLOB_LENGTH)
  String description;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "directLabor", column = @Column(name = "ESTIMATED_COST_DIRECT_LABOR", scale = 2)),
    @AttributeOverride(name = "indirectLabor", column = @Column(name = "ESTIMATED_COST_INDIRECT_LABOR", scale = 2)),
    @AttributeOverride(name = "indirectMaterial", column = @Column(name = "ESTIMATED_COST_INDIRECT_MATERIAL", scale = 2)),
    @AttributeOverride(name = "indirectExpenses", column = @Column(name = "ESTIMATED_COST_INDIRECT_EXPENSES", scale = 2))
  })
  ProcessCostEmbeddable estimatedCost;

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
  BigDecimal adjustCost;

  @Column(length = TypeDefinitions.DESCRIPTION_LENGTH)
  String adjustCostReason;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ITEM_ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  ItemId itemId;

  @Column(precision = 7, scale = 5)
  BigDecimal inputRate;

  @Column(name = "ITEM_ORDER")
  int order;

}
