package pico.erp.process.preprocess.type;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class PreprocessTypeRepositoryImpl implements PreprocessTypeRepository {

  @Lazy
  @Autowired(required = false)
  private Set<PreprocessType> types;

  @Override
  public boolean exists(PreprocessTypeId id) {
    for (PreprocessType type : types) {
      if (type.getId().equals(id)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Stream<PreprocessType> findAll() {
    return types.stream();
  }

  @Override
  public Optional<PreprocessType> findBy(PreprocessTypeId id) {
    return types.stream()
      .filter(type -> type.getId().equals(id))
      .findFirst();
  }

}
