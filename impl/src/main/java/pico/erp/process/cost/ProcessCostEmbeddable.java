package pico.erp.process.cost;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Embeddable
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessCostEmbeddable {

  /**
   * 직접 노무비율
   */
  @Column(name = "DIRECT_LABOR_COST", scale = 2)
  BigDecimal directLabor;

  /**
   * 간접 노무비율
   */
  @Column(name = "INDIRECT_LABOR_COST", scale = 2)
  BigDecimal indirectLabor;

  /**
   * 간접 재료비율
   */
  @Column(name = "INDIRECT_MATERIAL_COST", scale = 2)
  BigDecimal indirectMaterial;

  /**
   * 간접 경비율
   */
  @Column(name = "INDIRECT_EXPENSES", scale = 2)
  BigDecimal indirectExpenses;

}
