package pico.erp.process;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.process.data.ProcessTypeData;
import pico.erp.process.data.ProcessTypeId;

public interface ProcessTypeService {

  void add(@NotNull @Valid ProcessTypeRequests.AddPreprocessTypeRequest request);

  ProcessTypeData create(@NotNull @Valid ProcessTypeRequests.CreateRequest request);

  void delete(@NotNull @Valid ProcessTypeRequests.DeleteRequest request);

  boolean exists(@NotNull ProcessTypeId id);

  ProcessTypeData get(@NotNull ProcessTypeId id);

  void remove(@NotNull @Valid ProcessTypeRequests.RemovePreprocessTypeRequest request);

  void update(@NotNull @Valid ProcessTypeRequests.UpdateRequest request);

}
