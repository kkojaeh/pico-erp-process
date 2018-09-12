package pico.erp.process;

import java.math.BigDecimal;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import pico.erp.attachment.data.AttachmentId;
import pico.erp.item.data.ItemData;
import pico.erp.process.data.ProcessDifficultyKind;
import pico.erp.process.data.ProcessId;
import pico.erp.process.type.ProcessType;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.event.Event;
import pico.erp.user.data.UserData;

public interface ProcessMessages {

  @Data
  class CreateRequest {

    @Valid
    @NotNull
    ProcessId id;

    @NotNull
    ItemData itemData;

    @Size(min = 3, max = TypeDefinitions.NAME_X2_LENGTH)
    @NotNull
    String name;

    @NotNull
    ProcessType type;

    @NotNull
    ProcessDifficultyKind difficulty;

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    UserData managerData;

    @Valid
    AttachmentId attachmentId;

    @NotNull
    BigDecimal adjustCost;

    @Size(max = TypeDefinitions.DESCRIPTION_LENGTH)
    String adjustCostReason;

  }

  @Data
  class UpdateRequest {

    @Size(min = 3, max = TypeDefinitions.NAME_X2_LENGTH)
    @NotNull
    String name;

    @NotNull
    ProcessType type;

    @NotNull
    ProcessDifficultyKind difficulty;

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    UserData managerData;

    @Valid
    AttachmentId attachmentId;

    @NotNull
    BigDecimal adjustCost;

    @Size(max = TypeDefinitions.DESCRIPTION_LENGTH)
    String adjustCostReason;

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

  @Data
  class RenameRequest {

  }

  @Value
  class RenameResponse {

    Collection<Event> events;

  }
}
