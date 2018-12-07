package pico.erp.process.type;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pico.erp.process.cost.ProcessCostRatesData;
import pico.erp.process.difficulty.ProcessDifficultyData;
import pico.erp.process.difficulty.ProcessDifficultyKind;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.process.preprocess.type.PreprocessTypeData;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessTypeData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProcessTypeId id;

  String name;

  ProcessInfoTypeId infoTypeId;

  BigDecimal baseUnitCost;

  BigDecimal lossRate;

  ProcessCostRatesData costRates;

  Map<ProcessDifficultyKind, ProcessDifficultyData> difficulties;

  List<PreprocessTypeData> preprocessTypes;

}
