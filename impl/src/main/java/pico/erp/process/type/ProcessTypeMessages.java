package pico.erp.process.type;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import pico.erp.process.cost.ProcessCostRates;
import pico.erp.process.difficulty.ProcessDifficulty;
import pico.erp.process.difficulty.ProcessDifficultyKind;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.process.preparation.type.ProcessPreparationType;
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

    @NotNull
    @Min(0)
    BigDecimal lossRate;

    @Valid
    @NotNull
    ProcessInfoTypeId infoTypeId;

    @Valid
    ProcessCostRates costRates;

    @NotNull
    Map<ProcessDifficultyKind, ProcessDifficulty> difficulties;

  }

  @Data
  class UpdateRequest {

    @Size(min = 3, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    @NotNull
    @Min(0)
    BigDecimal baseUnitCost;

    @NotNull
    @Min(0)
    BigDecimal lossRate;

    @Valid
    @NotNull
    ProcessInfoTypeId infoTypeId;

    @Valid
    ProcessCostRates costRates;

    @NotNull
    Map<ProcessDifficultyKind, ProcessDifficulty> difficulties;

  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  class PrepareImportRequest {

    ProcessType previous;
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
    ProcessPreparationType preparationType;

  }

  @Value
  class AddPreprocessTypeResponse {

    Collection<Event> events;

  }

  @Data
  class RemovePreprocessTypeRequest {

    @NotNull
    ProcessPreparationType preparationType;

  }

  @Value
  class RemovePreprocessTypeResponse {

    Collection<Event> events;

  }

  @Value
  class PrepareImportResponse {

    Collection<Event> events;

  }
}
