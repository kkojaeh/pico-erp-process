package pico.erp.process.type;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
interface ProcessTypeEntityRepository extends
  CrudRepository<ProcessTypeEntity, ProcessTypeId> {

}

@Repository
@Transactional
public class ProcessTypeRepositoryJpa implements ProcessTypeRepository {

  @Autowired
  private ProcessTypeEntityRepository repository;

  @Autowired
  private ProcessTypeMapper mapper;

  @Override
  public ProcessType create(ProcessType processType) {
    val entity = mapper.jpa(processType);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(ProcessTypeId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(ProcessTypeId id) {
    return repository.exists(id);
  }

  @Override
  public Optional<ProcessType> findBy(ProcessTypeId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::jpa);
  }

  @Override
  public Stream<ProcessType> getAll() {
    return StreamSupport.stream(
      repository.findAll().spliterator(), false
    ).map(mapper::jpa);
  }

  @Override
  public void update(ProcessType processType) {
    val entity = repository.findOne(processType.getId());
    mapper.pass(mapper.jpa(processType), entity);
    repository.save(entity);
  }
}
