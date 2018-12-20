package pico.erp.process.cost;

import java.math.BigDecimal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessCostRatesData {

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

}
