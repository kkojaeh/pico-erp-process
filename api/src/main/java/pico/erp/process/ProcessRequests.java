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
import pico.erp.item.ItemId;
import pico.erp.process.difficulty.ProcessDifficultyKind;
import pico.erp.process.type.ProcessTypeId;
import pico.erp.shared.TypeDefinitions;

public interface ProcessRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    ProcessId id;

    @NotNull
    ProcessDifficultyKind difficulty;

    @Valid
    @NotNull
    ProcessTypeId typeId;

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    @NotNull
    @Min(0)
    BigDecimal lossRate;

    @NotNull
    BigDecimal adjustCost;

    @Size(max = TypeDefinitions.DESCRIPTION_LENGTH)
    String adjustCostReason;

    ItemId itemId;

    @NotNull
    @Min(0)
    BigDecimal inputRate;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    ProcessId id;

    @NotNull
    ProcessDifficultyKind difficulty;

    @Valid
    @NotNull
    ProcessTypeId typeId;

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    @NotNull
    @Min(0)
    BigDecimal lossRate;

    @NotNull
    BigDecimal adjustCost;

    @Size(max = TypeDefinitions.DESCRIPTION_LENGTH)
    String adjustCostReason;

    BigDecimal inputRate;

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

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class ChangeOrderRequest {

    @Valid
    @NotNull
    ProcessId id;

    @NotNull
    @Min(0)
    Integer order;

  }
}
