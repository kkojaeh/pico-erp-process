package pico.erp.process.preprocess.type;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public interface PreprocessTypeRepository {

  boolean exists(PreprocessTypeId id);

  Stream<PreprocessType> findAll();

  Optional<PreprocessType> findBy(PreprocessTypeId id);

}
