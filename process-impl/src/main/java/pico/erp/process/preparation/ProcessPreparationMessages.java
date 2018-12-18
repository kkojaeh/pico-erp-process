package pico.erp.process.preparation;

import java.math.BigDecimal;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import pico.erp.process.Process;
import pico.erp.process.preparation.type.ProcessPreparationType;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.event.Event;

public interface ProcessPreparationMessages {

  @Data
  class CreateRequest {

    @Valid
    @NotNull
    ProcessPreparationId id;

    Process process;

    @NotNull
    ProcessPreparationType type;

    BigDecimal chargeCost;


    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

  }

  @Data
  class UpdateRequest {

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    BigDecimal chargeCost;

  }

  @Data
  class DeleteRequest {

  }

  @Data
  class CommitRequest {

  }

  @Data
  class CompleteRequest {

  }

  @Data
  class CancelRequest {

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
  class CommitResponse {

    Collection<Event> events;
  }

  @Value
  class CompleteResponse {

    Collection<Event> events;

  }

  @Value
  class CancelResponse {

    Collection<Event> events;

  }

}
