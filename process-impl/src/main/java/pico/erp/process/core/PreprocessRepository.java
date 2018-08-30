package pico.erp.process.core;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import pico.erp.process.data.PreprocessId;
import pico.erp.process.data.ProcessId;
import pico.erp.process.domain.Preprocess;

@Repository
public interface PreprocessRepository {

  Preprocess create(@NotNull Preprocess preprocess);

  void deleteBy(@NotNull PreprocessId id);

  boolean exists(@NotNull PreprocessId id);

  Stream<Preprocess> findAllBy(@NotNull ProcessId processId);

  Optional<Preprocess> findBy(@NotNull PreprocessId id);

  void update(@NotNull Preprocess preprocess);

}
