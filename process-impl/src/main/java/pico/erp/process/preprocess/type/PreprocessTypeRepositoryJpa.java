package pico.erp.process.preprocess.type;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
interface PreprocessTypeEntityRepository extends
  CrudRepository<PreprocessTypeEntity, PreprocessTypeId> {

}

@Repository
@Transactional
public class PreprocessTypeRepositoryJpa implements PreprocessTypeRepository {

  @Autowired
  private PreprocessTypeEntityRepository repository;

  @Autowired
  private PreprocessTypeMapper mapper;

  @Override
  public PreprocessType create(PreprocessType preprocessType) {
    val entity = mapper.entity(preprocessType);
    val created = repository.save(entity);
    return mapper.domain(created);
  }

  @Override
  public void deleteBy(PreprocessTypeId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(PreprocessTypeId id) {
    return repository.exists(id);
  }

  @Override
  public Optional<PreprocessType> findBy(PreprocessTypeId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::domain);
  }

  @Override
  public Stream<PreprocessType> getAll() {
    return StreamSupport.stream(
      repository.findAll().spliterator(), false
    ).map(mapper::domain);
  }

  @Override
  public void update(PreprocessType preprocessType) {
    val entity = repository.findOne(preprocessType.getId());
    mapper.pass(mapper.entity(preprocessType), entity);
    repository.save(entity);
  }
}