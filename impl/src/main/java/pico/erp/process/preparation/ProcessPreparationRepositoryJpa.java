package pico.erp.process.preparation;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.process.ProcessId;

@Repository
interface PreprocessEntityRepository extends
  CrudRepository<ProcessPreparationEntity, ProcessPreparationId> {

  @Query("SELECT pp FROM ProcessPreparation pp WHERE pp.processId = :processId")
  Stream<ProcessPreparationEntity> findAllBy(@Param("processId") ProcessId processId);
}

@Repository
@Transactional
public class ProcessPreparationRepositoryJpa implements ProcessPreparationRepository {

  @Autowired
  private PreprocessEntityRepository repository;

  @Autowired
  private ProcessPreparationMapper mapper;

  @Override
  public ProcessPreparation create(ProcessPreparation processPreparation) {
    val entity = mapper.jpa(processPreparation);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(ProcessPreparationId id) {
    repository.deleteById(id);
  }

  @Override
  public boolean exists(ProcessPreparationId id) {
    return repository.existsById(id);
  }

  @Override
  public Stream<ProcessPreparation> findAllBy(ProcessId processId) {
    return repository.findAllBy(processId)
      .map(mapper::jpa);
  }

  @Override
  public Optional<ProcessPreparation> findBy(ProcessPreparationId id) {
    return repository.findById(id)
      .map(mapper::jpa);
  }

  @Override
  public void update(ProcessPreparation processPreparation) {
    val entity = repository.findById(processPreparation.getId()).get();
    mapper.pass(mapper.jpa(processPreparation), entity);
    repository.save(entity);
  }
}
