package pico.erp.process.impl;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.process.core.ProcessTypeRepository;
import pico.erp.process.data.ProcessTypeId;
import pico.erp.process.domain.ProcessType;
import pico.erp.process.impl.jpa.ProcessTypeEntity;

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
  private ProcessJpaMapper mapper;

  @Override
  public ProcessType create(ProcessType processType) {
    val entity = mapper.map(processType);
    val created = repository.save(entity);
    return mapper.map(created);
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
      .map(mapper::map);
  }

  @Override
  public Stream<ProcessType> getAll() {
    return StreamSupport.stream(
      repository.findAll().spliterator(), false
    ).map(mapper::map);
  }

  @Override
  public void update(ProcessType processType) {
    val entity = repository.findOne(processType.getId());
    mapper.pass(mapper.map(processType), entity);
    repository.save(entity);
  }
}
