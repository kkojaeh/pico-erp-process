package pico.erp.process.jpa;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.process.preprocess.PreprocessRepository;
import pico.erp.process.preprocess.data.PreprocessId;
import pico.erp.process.data.ProcessId;
import pico.erp.process.preprocess.Preprocess;

@Repository
interface PreprocessEntityRepository extends
  CrudRepository<PreprocessEntity, PreprocessId> {

  @Query("SELECT pp FROM Preprocess pp WHERE pp.process.id = :processId")
  Stream<PreprocessEntity> findAllBy(@Param("processId") ProcessId processId);
}

@Repository
@Transactional
public class PreprocessRepositoryJpa implements PreprocessRepository {

  @Autowired
  private PreprocessEntityRepository repository;

  @Autowired
  private ProcessJpaMapper mapper;

  @Override
  public Preprocess create(Preprocess preprocess) {
    val entity = mapper.map(preprocess);
    val created = repository.save(entity);
    return mapper.map(created);
  }

  @Override
  public void deleteBy(PreprocessId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(PreprocessId id) {
    return repository.exists(id);
  }

  @Override
  public Stream<Preprocess> findAllBy(ProcessId processId) {
    return repository.findAllBy(processId)
      .map(mapper::map);
  }

  @Override
  public Optional<Preprocess> findBy(PreprocessId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::map);
  }

  @Override
  public void update(Preprocess preprocess) {
    val entity = repository.findOne(preprocess.getId());
    mapper.pass(mapper.map(preprocess), entity);
    repository.save(entity);
  }
}
