package pico.erp.process.preparation;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.process.ProcessId;
import pico.erp.process.preparation.ProcessPreparationRequests.GenerateRequest;

public interface ProcessPreparationService {

  ProcessPreparationData create(@Valid ProcessPreparationRequests.CreateRequest request);

  void delete(@Valid ProcessPreparationRequests.DeleteRequest request);

  boolean exists(@NotNull ProcessPreparationId id);

  void generate(GenerateRequest request);

  ProcessPreparationData get(@NotNull ProcessPreparationId id);

  List<ProcessPreparationData> getAll(@NotNull ProcessId processId);

  void update(@Valid ProcessPreparationRequests.UpdateRequest request);


}
