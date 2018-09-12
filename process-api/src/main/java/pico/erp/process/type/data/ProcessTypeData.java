package pico.erp.process.type.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pico.erp.process.preprocess.type.data.PreprocessTypeData;
import pico.erp.process.data.ProcessCostRatesData;
import pico.erp.process.data.ProcessDifficultyGradeData;
import pico.erp.process.info.type.data.ProcessInfoTypeId;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessTypeData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProcessTypeId id;

  String name;

  ProcessInfoTypeId infoTypeId;

  BigDecimal baseUnitCost;

  ProcessCostRatesData costRates;

  List<ProcessDifficultyGradeData> difficultyGrades;

  List<PreprocessTypeData> preprocessTypes;

}
