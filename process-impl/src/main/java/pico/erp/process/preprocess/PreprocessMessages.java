package pico.erp.process.preprocess;

import java.math.BigDecimal;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import pico.erp.process.Process;
import pico.erp.process.info.ProcessInfoLifecycler;
import pico.erp.process.preprocess.type.PreprocessType;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.event.Event;

public interface PreprocessMessages {

  @Data
  class CreateRequest {

    @Valid
    @NotNull
    PreprocessId id;

    Process process;

    @NotNull
    PreprocessType type;

    BigDecimal chargeCost;


    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    @NotNull
    ProcessInfoLifecycler processInfoLifecycler;

  }

  @Data
  class UpdateRequest {

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    BigDecimal chargeCost;

    @NotNull
    ProcessInfoLifecycler processInfoLifecycler;

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

}
