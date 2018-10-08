package pico.erp.process.info.type;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class ProcessInfoTypeRepositoryImpl implements ProcessInfoTypeRepository {

  @Lazy
  @Autowired(required = false)
  private Set<ProcessInfoType> types;

  @Override
  public Stream<ProcessInfoType> findAll() {
    return types.stream();
  }

  @Override
  public Optional<ProcessInfoType> findBy(ProcessInfoTypeId id) {
    return types.stream()
      .filter(type -> type.getId().equals(id))
      .findFirst();
  }

}
