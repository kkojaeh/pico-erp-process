package pico.erp.process.type;

import java.math.BigDecimal;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.process.cost.ProcessCostRatesData;
import pico.erp.process.difficulty.ProcessDifficultyData;
import pico.erp.process.difficulty.ProcessDifficultyKind;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.process.preprocess.type.PreprocessTypeId;
import pico.erp.shared.TypeDefinitions;

public interface ProcessTypeRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    ProcessTypeId id;

    @Size(min = 2, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    @Min(0)
    @NotNull
    BigDecimal baseUnitCost;

    @NotNull
    @Min(0)
    BigDecimal lossRate;

    @Valid
    @NotNull
    ProcessInfoTypeId infoTypeId;

    @Valid
    @NotNull
    ProcessCostRatesData costRates;

    @NotNull
    Map<ProcessDifficultyKind, ProcessDifficultyData> difficulties;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    ProcessTypeId id;

    @Size(min = 2, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    @NotNull
    @Min(0)
    BigDecimal baseUnitCost;

    @NotNull
    @Min(0)
    BigDecimal lossRate;

    @Valid
    @NotNull
    ProcessInfoTypeId infoTypeId;

    @Valid
    @NotNull
    ProcessCostRatesData costRates;

    @NotNull
    Map<ProcessDifficultyKind, ProcessDifficultyData> difficulties;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class DeleteRequest {

    @Valid
    @NotNull
    ProcessTypeId id;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class AddPreprocessTypeRequest {

    @Valid
    @NotNull
    ProcessTypeId id;

    @Valid
    @NotNull
    PreprocessTypeId preprocessTypeId;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class RemovePreprocessTypeRequest {

    @Valid
    @NotNull
    ProcessTypeId id;

    @Valid
    @NotNull
    PreprocessTypeId preprocessTypeId;

  }
}
