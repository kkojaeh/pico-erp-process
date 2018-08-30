package pico.erp.process;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.process.data.PreprocessTypeId;
import pico.erp.process.data.ProcessCostRatesData;
import pico.erp.process.data.ProcessDifficultyGradeData;
import pico.erp.process.data.ProcessInfoTypeId;
import pico.erp.process.data.ProcessTypeId;
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

    @Valid
    @NotNull
    ProcessInfoTypeId infoTypeId;

    @Valid
    @NotNull
    ProcessCostRatesData costRates;

    @Size(min = 4, max = 4)
    @NotNull
    @Builder.Default
    List<ProcessDifficultyGradeData> difficultyGrades = new LinkedList<>();

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

    @Valid
    @NotNull
    ProcessInfoTypeId infoTypeId;

    @Valid
    @NotNull
    ProcessCostRatesData costRates;

    @Size(min = 4, max = 4)
    @NotNull
    @Builder.Default
    List<ProcessDifficultyGradeData> difficultyGrades = new LinkedList<>();

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
