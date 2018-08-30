package pico.erp.process;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.process.data.PreprocessTypeData;
import pico.erp.process.data.PreprocessTypeId;

public interface PreprocessTypeService {

  PreprocessTypeData create(@Valid PreprocessTypeRequests.CreateRequest request);

  void delete(@Valid PreprocessTypeRequests.DeleteRequest request);

  boolean exists(@NotNull PreprocessTypeId id);

  PreprocessTypeData get(@NotNull PreprocessTypeId id);

  void update(@Valid PreprocessTypeRequests.UpdateRequest request);

}
