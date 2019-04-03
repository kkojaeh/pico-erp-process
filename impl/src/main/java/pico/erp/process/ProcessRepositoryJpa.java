package pico.erp.process;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.item.ItemId;
import pico.erp.process.type.ProcessTypeId;

@Repository
interface ProcessEntityRepository extends
  CrudRepository<ProcessEntity, ProcessId> {

  @Query("SELECT p FROM Process p WHERE p.typeId = :processTypeId")
  Stream<ProcessEntity> findAllBy(@Param("processTypeId") ProcessTypeId processTypeId);

  @Query("SELECT COUNT(p) FROM Process p WHERE p.itemId = :itemId")
  long countBy(@Param("itemId") ItemId itemId);

  @Query("SELECT p FROM Process p WHERE p.itemId = :itemId AND p.deleted = false ORDER BY p.order")
  Stream<ProcessEntity> findAllBy(@Param("itemId") ItemId itemId);

}

@Repository
@Transactional
public class ProcessRepositoryJpa implements ProcessRepository {

  @Autowired
  private ProcessEntityRepository repository;

  @Autowired
  private ProcessMapper mapper;

  @Override
  public Process create(Process process) {
    val entity = mapper.jpa(process);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(ProcessId id) {
    repository.deleteById(id);
  }

  @Override
  public boolean exists(ProcessId id) {
    return repository.existsById(id);
  }


  @Override
  public Stream<Process> findAllBy(ProcessTypeId id) {
    return repository.findAllBy(id)
      .map(mapper::jpa);
  }

  @Override
  public long countBy(ItemId itemId) {
    return repository.countBy(itemId);
  }

  @Override
  public Optional<Process> findBy(ProcessId id) {
    return repository.findById(id)
      .map(mapper::jpa);
  }

  @Override
  public void update(Process process) {
    ProcessEntity entity = repository.findById(process.getId()).get();
    mapper.pass(mapper.jpa(process), entity);
    repository.save(entity);
  }

  @Override
  public Stream<Process> findAllBy(ItemId itemId) {
    return repository.findAllBy(itemId)
      .map(mapper::jpa);
  }
}
