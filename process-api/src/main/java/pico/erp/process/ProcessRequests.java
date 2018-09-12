package pico.erp.process;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.attachment.data.AttachmentId;
import pico.erp.item.data.ItemId;
import pico.erp.process.data.ProcessDifficultyKind;
import pico.erp.process.data.ProcessId;
import pico.erp.process.type.data.ProcessTypeId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.user.data.UserId;

public interface ProcessRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    ProcessId id;

    @Valid
    @NotNull
    ItemId itemId;

    @Size(min = 3, max = TypeDefinitions.NAME_X2_LENGTH)
    @NotNull
    String name;

    ProcessDifficultyKind difficulty;

    @Valid
    @NotNull
    ProcessTypeId typeId;

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    UserId managerId;

    @Valid
    AttachmentId attachmentId;

    @NotNull
    BigDecimal adjustCost;

    @Size(max = TypeDefinitions.DESCRIPTION_LENGTH)
    String adjustCostReason;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    ProcessId id;

    @Size(min = 3, max = TypeDefinitions.NAME_X2_LENGTH)
    @NotNull
    String name;

    ProcessDifficultyKind difficulty;

    @Valid
    @NotNull
    ProcessTypeId typeId;

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    UserId managerId;

    @Valid
    AttachmentId attachmentId;

    @NotNull
    BigDecimal adjustCost;

    @Size(max = TypeDefinitions.DESCRIPTION_LENGTH)
    String adjustCostReason;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class DeleteRequest {

    @Valid
    @NotNull
    ProcessId id;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CompletePlanRequest {

    @Valid
    @NotNull
    ProcessId id;

  }
}
