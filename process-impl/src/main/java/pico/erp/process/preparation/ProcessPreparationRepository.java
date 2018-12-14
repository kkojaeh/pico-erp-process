package pico.erp.process.preparation;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import pico.erp.process.ProcessId;

@Repository
public interface ProcessPreparationRepository {

  ProcessPreparation create(@NotNull ProcessPreparation processPreparation);

  void deleteBy(@NotNull ProcessPreparationId id);

  boolean exists(@NotNull ProcessPreparationId id);

  Stream<ProcessPreparation> findAllBy(@NotNull ProcessId processId);

  Optional<ProcessPreparation> findBy(@NotNull ProcessPreparationId id);

  void update(@NotNull ProcessPreparation processPreparation);

}
