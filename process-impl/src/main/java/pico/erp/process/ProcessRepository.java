package pico.erp.process;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import pico.erp.item.ItemId;
import pico.erp.process.type.ProcessTypeId;

@Repository
public interface ProcessRepository {

  Process create(@NotNull Process process);

  void deleteBy(@NotNull ProcessId id);

  boolean exists(@NotNull ProcessId id);

  boolean exists(@NotNull ItemId itemId);

  Stream<Process> findAllBy(@NotNull ProcessTypeId id);

  Optional<Process> findBy(@NotNull ItemId itemId);

  Optional<Process> findBy(@NotNull ProcessId id);

  void update(@NotNull Process process);

}