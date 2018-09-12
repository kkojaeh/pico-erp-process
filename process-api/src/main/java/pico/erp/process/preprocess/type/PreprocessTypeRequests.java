package pico.erp.process.preprocess.type;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.process.preprocess.type.data.PreprocessTypeId;
import pico.erp.process.info.type.data.ProcessInfoTypeId;
import pico.erp.shared.TypeDefinitions;

public interface PreprocessTypeRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    PreprocessTypeId id;

    @Size(min = 2, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    @Min(0)
    @NotNull
    BigDecimal baseCost;

    @Valid
    @NotNull
    ProcessInfoTypeId infoTypeId;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

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
    ProcessInfoTypeId infoTypeId;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class DeleteRequest {

    @Valid
    @NotNull
    PreprocessTypeId id;

  }
}
