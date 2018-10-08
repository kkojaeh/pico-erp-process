package pico.erp.process.preprocess;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.attachment.AttachmentId;
import pico.erp.process.ProcessId;
import pico.erp.process.preprocess.type.PreprocessTypeId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.user.UserId;

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

    @Size(min = 3, max = TypeDefinitions.NAME_X3_LENGTH)
    @NotNull
    String name;

    @Valid
    @NotNull
    PreprocessTypeId typeId;

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    UserId managerId;

    BigDecimal chargeCost;

    @Valid
    AttachmentId attachmentId;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    PreprocessId id;

    @Size(min = 3, max = TypeDefinitions.NAME_X2_LENGTH)
    @NotNull
    String name;

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

    UserId managerId;

    BigDecimal chargeCost;

    @Valid
    AttachmentId attachmentId;

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
