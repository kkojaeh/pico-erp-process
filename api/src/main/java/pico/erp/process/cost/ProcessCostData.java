package pico.erp.process.cost;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessCostData {

  public static ProcessCostData ZERO = new ProcessCostData(BigDecimal.ZERO, BigDecimal.ZERO,
    BigDecimal.ZERO, BigDecimal.ZERO);

  /**
   * 직접 노무비
   */
  BigDecimal directLabor;

  /**
   * 간접 노무비
   */
  BigDecimal indirectLabor;

  /**
   * 간접 재료비
   */
  BigDecimal indirectMaterial;

  /**
   * 간접 경비
   */
  BigDecimal indirectExpenses;
}
