package pico.erp.process;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.process.ProcessRequests.RecalculateCostByTypeRequest;

public interface ProcessService {

  void completePlan(@Valid ProcessRequests.CompletePlanRequest request);

  ProcessData create(@Valid ProcessRequests.CreateRequest request);

  void delete(@Valid ProcessRequests.DeleteRequest request);

  boolean exists(@NotNull ProcessId id);

  ProcessData get(@NotNull ProcessId id);

  void recalculateCostByType(RecalculateCostByTypeRequest request);

  void update(@Valid ProcessRequests.UpdateRequest request);


}
