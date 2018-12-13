package pico.erp.process;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pico.erp.attachment.AttachmentId;
import pico.erp.process.difficulty.ProcessDifficultyKind;
import pico.erp.process.type.ProcessTypeId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.user.UserId;

public interface ProcessRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    ProcessId id;

    ProcessDifficultyKind difficulty;

    @Valid
    @NotNull
    ProcessTypeId typeId;

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    UserId managerId;

    @NotNull
    @Min(0)
    BigDecimal lossRate;

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

    ProcessDifficultyKind difficulty;

    @Valid
    @NotNull
    ProcessTypeId typeId;

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    UserId managerId;

    @NotNull
    @Min(0)
    BigDecimal lossRate;

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

  @Getter
  @Builder
  class RecalculateCostByTypeRequest {

    @Valid
    @NotNull
    ProcessTypeId processTypeId;

  }
}
