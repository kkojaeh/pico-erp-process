package pico.erp.process.impl.jpa;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Embeddable
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessCostRatesEmbeddable {

  /**
   * 직접 노무비율
   */
  @Column(name = "DIRECT_LABOR_COST_RATE", precision = 7, scale = 5)
  BigDecimal directLabor;

  /**
   * 간접 노무비율
   */
  @Column(name = "INDIRECT_LABOR_COST_RATE", precision = 7, scale = 5)
  BigDecimal indirectLabor;

  /**
   * 간접 재료비율
   */
  @Column(name = "INDIRECT_MATERIAL_COST_RATE", precision = 7, scale = 5)
  BigDecimal indirectMaterial;

  /**
   * 간접 경비율
   */
  @Column(name = "INDIRECT_EXPENSES_RATE", precision = 7, scale = 5)
  BigDecimal indirectExpenses;

}
