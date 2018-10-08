package pico.erp.process.type;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import pico.erp.process.cost.ProcessCostRates;
import pico.erp.process.difficulty.grade.ProcessDifficultyGrade;
import pico.erp.process.info.type.ProcessInfoType;
import pico.erp.process.preprocess.type.PreprocessType;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.event.Event;

public interface ProcessTypeMessages {

  @Data
  class CreateRequest {

    @Valid
    @NotNull
    ProcessTypeId id;

    @Size(min = 2, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    @NotNull
    @Min(0)
    BigDecimal baseUnitCost;

    @Valid
    @NotNull
    ProcessInfoType infoType;

    @Valid
    ProcessCostRates costRates;

    @Size(min = 4, max = 4)
    @NotNull
    List<ProcessDifficultyGrade> difficultyGrades;

  }

  @Data
  class UpdateRequest {

    @Size(min = 3, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    BigDecimal baseUnitCost;

    @Valid
    @NotNull
    ProcessInfoType infoType;

    @Valid
    ProcessCostRates costRates;

    @Size(min = 4, max = 4)
    @NotNull
    List<ProcessDifficultyGrade> difficultyGrades;

  }

  @Data
  class DeleteRequest {

  }

  @Value
  class CreateResponse {

    Collection<Event> events;

  }

  @Value
  class UpdateResponse {

    Collection<Event> events;

  }

  @Value
  class DeleteResponse {

    Collection<Event> events;

  }

  @Data
  class AddPreprocessTypeRequest {

    @NotNull
    PreprocessType preprocessType;

  }

  @Value
  class AddPreprocessTypeResponse {

    Collection<Event> events;

  }

  @Data
  class RemovePreprocessTypeRequest {

    @NotNull
    PreprocessType preprocessType;

  }

  @Value
  class RemovePreprocessTypeResponse {

    Collection<Event> events;

  }
}
