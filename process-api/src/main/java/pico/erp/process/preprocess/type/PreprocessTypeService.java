package pico.erp.process.preprocess.type;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.process.preprocess.type.data.PreprocessTypeData;
import pico.erp.process.preprocess.type.data.PreprocessTypeId;

public interface PreprocessTypeService {

  PreprocessTypeData create(@Valid PreprocessTypeRequests.CreateRequest request);

  void delete(@Valid PreprocessTypeRequests.DeleteRequest request);

  boolean exists(@NotNull PreprocessTypeId id);

  PreprocessTypeData get(@NotNull PreprocessTypeId id);

  void update(@Valid PreprocessTypeRequests.UpdateRequest request);

}
