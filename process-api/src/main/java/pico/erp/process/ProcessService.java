package pico.erp.process;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.item.ItemId;

public interface ProcessService {

  void completePlan(@Valid ProcessRequests.CompletePlanRequest request);

  ProcessData create(@Valid ProcessRequests.CreateRequest request);

  void delete(@Valid ProcessRequests.DeleteRequest request);

  boolean exists(@NotNull ItemId itemId);

  boolean exists(@NotNull ProcessId id);

  ProcessData get(@NotNull ItemId itemId);

  ProcessData get(@NotNull ProcessId id);

  void update(@Valid ProcessRequests.UpdateRequest request);


}
