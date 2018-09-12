package pico.erp.process.jpa;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
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
import pico.erp.process.info.type.data.ProcessInfoTypeId;
import pico.erp.process.type.data.ProcessTypeId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Auditor;

@Entity(name = "ProcessType")
@Table(name = "PRS_PROCESS_TYPE")
@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessTypeEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ID", length = TypeDefinitions.ID_LENGTH))
  })
  ProcessTypeId id;

  @Column(length = TypeDefinitions.NAME_LENGTH)
  String name;

  @Column(scale = 2)
  BigDecimal baseUnitCost;

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

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "INFO_TYPE_ID", length = TypeDefinitions.CLASS_NAME_LENGTH))
  })
  ProcessInfoTypeId infoTypeId;

  @Column(length = TypeDefinitions.NAME_LENGTH)
  String infoTypeName;

  @Embedded
  ProcessCostRatesEmbeddable costRates;

  @Builder.Default
  @ElementCollection(fetch = FetchType.LAZY)
  @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @CollectionTable(name = "PRS_PROCESS_TYPE_DIFFICULTY_GRADE", joinColumns = @JoinColumn(name = "PROCESS_ID"))
  @OrderColumn
  @OrderBy("ordinal")
  List<ProcessDifficultyGradeEmbeddable> difficultyGrades = new LinkedList<>();

  @ManyToMany
  @JoinTable(name = "PRS_PROCESS_TYPE_PREPROCESS_TYPE",
    joinColumns = @JoinColumn(name = "PROCESS_TYPE_ID"),
    inverseJoinColumns = @JoinColumn(name = "PREPROCESS_TYPE_ID"))
  private List<PreprocessTypeEntity> preprocessTypes;

  public ProcessTypeEntity(ProcessTypeId id) {
    this.id = id;
  }

}
