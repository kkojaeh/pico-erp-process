package pico.erp.process.preprocess.type;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public interface PreprocessTypeRepository {

  PreprocessType create(@NotNull PreprocessType preprocessType);

  void deleteBy(@NotNull PreprocessTypeId id);

  boolean exists(@NotNull PreprocessTypeId id);

  Optional<PreprocessType> findBy(@NotNull PreprocessTypeId id);

  Stream<PreprocessType> getAll();

  void update(@NotNull PreprocessType preprocessType);

}
