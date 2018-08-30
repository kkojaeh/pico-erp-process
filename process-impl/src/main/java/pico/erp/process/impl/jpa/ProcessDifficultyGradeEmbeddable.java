package pico.erp.process.impl.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import pico.erp.process.data.ProcessDifficultyKind;
import pico.erp.shared.TypeDefinitions;

@Embeddable
@Data
@EqualsAndHashCode(of = "difficulty")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessDifficultyGradeEmbeddable implements Serializable {

  @NotNull
  @Column(length = TypeDefinitions.ENUM_LENGTH)
  @Enumerated(EnumType.STRING)
  ProcessDifficultyKind difficulty;


  @Column(length = TypeDefinitions.DESCRIPTION_LENGTH)
  String description;

  @NotNull
  @Min(0)
  @Column(precision = 7, scale = 5)
  BigDecimal costRate;

  int ordinal;

}
