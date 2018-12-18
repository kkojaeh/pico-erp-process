package pico.erp.process.preparation;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pico.erp.process.ProcessId;
import pico.erp.process.preparation.type.ProcessPreparationTypeId;
import pico.erp.shared.TypeDefinitions;

public interface ProcessPreparationRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    ProcessPreparationId id;

    @Valid
    @NotNull
    ProcessId processId;

    @Valid
    @NotNull
    ProcessPreparationTypeId typeId;

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    BigDecimal chargeCost;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    ProcessPreparationId id;

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    BigDecimal chargeCost;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class DeleteRequest {

    @Valid
    @NotNull
    ProcessPreparationId id;

  }

  @Getter
  @Builder
  class GenerateRequest {

    @Valid
    @NotNull
    ProcessId processId;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CommitRequest {

    @Valid
    @NotNull
    ProcessPreparationId id;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CompleteRequest {

    @Valid
    @NotNull
    ProcessPreparationId id;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CancelRequest {

    @Valid
    @NotNull
    ProcessPreparationId id;

  }
}
