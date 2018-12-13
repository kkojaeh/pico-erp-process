package pico.erp.process.preprocess;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.process.ProcessId;
import pico.erp.process.preprocess.type.PreprocessTypeId;
import pico.erp.shared.TypeDefinitions;

public interface PreprocessRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    PreprocessId id;

    @Valid
    @NotNull
    ProcessId processId;

    @Valid
    @NotNull
    PreprocessTypeId typeId;

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
    PreprocessId id;

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
    PreprocessId id;

  }
}
