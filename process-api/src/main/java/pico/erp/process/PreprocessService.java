package pico.erp.process;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.process.data.PreprocessData;
import pico.erp.process.data.PreprocessId;
import pico.erp.process.data.ProcessId;

public interface PreprocessService {

  PreprocessData create(@Valid PreprocessRequests.CreateRequest request);

  void delete(@Valid PreprocessRequests.DeleteRequest request);

  boolean exists(@NotNull PreprocessId id);

  PreprocessData get(@NotNull PreprocessId id);

  List<PreprocessData> getAll(@NotNull ProcessId processId);

  void update(@Valid PreprocessRequests.UpdateRequest request);


}
