package pico.erp.process.cost;

import org.mapstruct.Mapper;

@Mapper
public abstract class ProcessCostMapper {

  public ProcessCostRates domain(ProcessCostRatesEmbeddable rates) {
    return ProcessCostRates.builder()
      .directLabor(rates.getDirectLabor())
      .indirectLabor(rates.getIndirectLabor())
      .indirectMaterial(rates.getIndirectMaterial())
      .indirectExpenses(rates.getIndirectExpenses())
      .build();
  }

  public ProcessCost domain(ProcessCostEmbeddable cost) {
    return ProcessCost.builder()
      .directLabor(cost.getDirectLabor())
      .indirectLabor(cost.getIndirectLabor())
      .indirectMaterial(cost.getIndirectMaterial())
      .indirectExpenses(cost.getIndirectExpenses())
      .build();
  }

  public abstract ProcessCostEmbeddable entity(ProcessCost cost);

  public abstract ProcessCostRatesEmbeddable entity(ProcessCostRates rates);

  public abstract ProcessCostData map(ProcessCost cost);

  public ProcessCostRates map(ProcessCostRatesData data) {
    return ProcessCostRates.builder()
      .directLabor(data.getDirectLabor())
      .indirectLabor(data.getIndirectLabor())
      .indirectMaterial(data.getIndirectMaterial())
      .indirectExpenses(data.getIndirectExpenses())
      .build();
  }

  public abstract ProcessCostRatesData map(ProcessCostRates data);
}
