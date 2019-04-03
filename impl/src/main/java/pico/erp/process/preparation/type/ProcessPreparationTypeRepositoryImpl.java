package pico.erp.process.preparation.type;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import kkojaeh.spring.boot.component.ComponentAutowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProcessPreparationTypeRepositoryImpl implements ProcessPreparationTypeRepository {

  @ComponentAutowired(required = false)
  private Set<ProcessPreparationType> types;

  @Override
  public boolean exists(ProcessPreparationTypeId id) {
    for (ProcessPreparationType type : types) {
      if (type.getId().equals(id)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Stream<ProcessPreparationType> findAll() {
    return types.stream();
  }

  @Override
  public Optional<ProcessPreparationType> findBy(ProcessPreparationTypeId id) {
    return types.stream()
      .filter(type -> type.getId().equals(id))
      .findFirst();
  }

}
