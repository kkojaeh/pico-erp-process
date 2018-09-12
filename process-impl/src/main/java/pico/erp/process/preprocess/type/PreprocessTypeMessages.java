package pico.erp.process.preprocess.type;

import java.math.BigDecimal;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import pico.erp.process.preprocess.type.data.PreprocessTypeId;
import pico.erp.process.info.type.data.ProcessInfoType;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.event.Event;

public interface PreprocessTypeMessages {

  @Data
  class CreateRequest {

    @Valid
    @NotNull
    PreprocessTypeId id;

    @Size(min = 2, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    @NotNull
    @Min(0)
    BigDecimal baseCost;

    @Valid
    @NotNull
    ProcessInfoType infoType;

  }

  @Data
  class UpdateRequest {

    @Size(min = 3, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    BigDecimal baseCost;

    @Valid
    @NotNull
    ProcessInfoType infoType;

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
