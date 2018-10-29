package pico.erp.process.preprocess;

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
  CrudRepository<PreprocessEntity, PreprocessId> {

  @Query("SELECT pp FROM Preprocess pp WHERE pp.processId = :processId")
  Stream<PreprocessEntity> findAllBy(@Param("processId") ProcessId processId);
}

@Repository
@Transactional
public class PreprocessRepositoryJpa implements PreprocessRepository {

  @Autowired
  private PreprocessEntityRepository repository;

  @Autowired
  private PreprocessMapper mapper;

  @Override
  public Preprocess create(Preprocess preprocess) {
    val entity = mapper.jpa(preprocess);
    val created = repository.save(entity);
    return mapper.jpa(created);
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
      .map(mapper::jpa);
  }

  @Override
  public Optional<Preprocess> findBy(PreprocessId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::jpa);
  }

  @Override
  public void update(Preprocess preprocess) {
    val entity = repository.findOne(preprocess.getId());
    mapper.pass(mapper.jpa(preprocess), entity);
    repository.save(entity);
  }
}
