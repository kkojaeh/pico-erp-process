package pico.erp.process.type;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import pico.erp.process.type.data.ProcessTypeId;

@Repository
public interface ProcessTypeRepository {

  ProcessType create(@NotNull ProcessType processType);

  void deleteBy(@NotNull ProcessTypeId id);

  boolean exists(@NotNull ProcessTypeId id);

  Optional<ProcessType> findBy(@NotNull ProcessTypeId id);

  Stream<ProcessType> getAll();

  void update(@NotNull ProcessType processType);

}
