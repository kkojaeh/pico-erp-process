package pico.erp.process;

import java.math.BigDecimal;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import pico.erp.process.difficulty.ProcessDifficultyKind;
import pico.erp.process.info.ProcessInfoLifecycler;
import pico.erp.process.type.ProcessType;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.event.Event;

public interface ProcessMessages {

  @Data
  class CreateRequest {

    @Valid
    @NotNull
    ProcessId id;

    @NotNull
    ProcessType type;

    @NotNull
    ProcessDifficultyKind difficulty;

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    @NotNull
    @Min(0)
    BigDecimal lossRate;

    @NotNull
    BigDecimal adjustCost;

    @Size(max = TypeDefinitions.DESCRIPTION_LENGTH)
    String adjustCostReason;

    @NotNull
    ProcessInfoLifecycler processInfoLifecycler;

  }

  @Data
  class UpdateRequest {

    @NotNull
    ProcessType type;

    @NotNull
    ProcessDifficultyKind difficulty;

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    @NotNull
    @Min(0)
    BigDecimal lossRate;

    @NotNull
    BigDecimal adjustCost;

    @Size(max = TypeDefinitions.DESCRIPTION_LENGTH)
    String adjustCostReason;

    @NotNull
    ProcessInfoLifecycler processInfoLifecycler;

  }

  @Data
  class DeleteRequest {

  }

  @Data
  class CalculateEstimatedCostRequest {

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

  @Value
  class CalculateEstimatedCostResponse {

    Collection<Event> events;

  }

  @Data
  class CompletePlanRequest {

  }

  @Value
  class CompletePlanResponse {

    Collection<Event> events;

  }

}