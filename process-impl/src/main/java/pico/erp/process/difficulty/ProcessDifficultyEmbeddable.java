package pico.erp.process.difficulty;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pico.erp.shared.TypeDefinitions;

@Embeddable
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessDifficultyEmbeddable implements Serializable {

  @Column(length = TypeDefinitions.DESCRIPTION_LENGTH)
  String description;

  @NotNull
  @Min(0)
  @Column(precision = 7, scale = 5)
  BigDecimal costRate;

}
