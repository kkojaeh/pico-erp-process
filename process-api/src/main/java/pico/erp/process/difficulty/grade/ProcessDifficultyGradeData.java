package pico.erp.process.difficulty.grade;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pico.erp.shared.TypeDefinitions;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "difficulty")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessDifficultyGradeData implements Serializable {

  @NotNull
  ProcessDifficultyKind difficulty;

  @Size(max = TypeDefinitions.DESCRIPTION_LENGTH)
  String description;

  @NotNull
  @Min(0)
  BigDecimal costRate;


}
