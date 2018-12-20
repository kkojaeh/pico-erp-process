package pico.erp.process.cost;

import java.math.BigDecimal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessCostRates {

  /**
   * 직접 노무비율
   */
  @Min(0)
  @Max(1)
  BigDecimal directLabor;

  /**
   * 간접 노무비율
   */
  @Min(0)
  @Max(1)
  BigDecimal indirectLabor;

  /**
   * 간접 재료비율
   */
  @Min(0)
  @Max(1)
  BigDecimal indirectMaterial;

  /**
   * 간접 경비율
   */
  @Min(0)
  @Max(1)
  BigDecimal indirectExpenses;

  public ProcessCost calculate(@NonNull BigDecimal cost) {
    return ProcessCost.builder()
      .directLabor(cost.multiply(directLabor))
      .indirectLabor(cost.multiply(indirectLabor))
      .indirectMaterial(cost.multiply(indirectMaterial))
      .indirectExpenses(cost.multiply(indirectExpenses))
      .build();
  }

}
