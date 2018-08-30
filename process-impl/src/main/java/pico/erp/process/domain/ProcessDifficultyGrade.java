package pico.erp.process.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pico.erp.process.data.ProcessDifficultyKind;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "difficulty")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessDifficultyGrade implements Serializable {

  private static final long serialVersionUID = 1L;

  ProcessDifficultyKind difficulty;

  String description;

  BigDecimal costRate;

}
