package pico.erp.process;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessCost {

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
