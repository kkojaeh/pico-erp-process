package pico.erp.process.preparation.type;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessPreparationTypeRepository {

  boolean exists(ProcessPreparationTypeId id);

  Stream<ProcessPreparationType> findAll();

  Optional<ProcessPreparationType> findBy(ProcessPreparationTypeId id);

}
