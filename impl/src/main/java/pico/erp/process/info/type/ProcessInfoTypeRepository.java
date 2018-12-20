package pico.erp.process.info.type;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessInfoTypeRepository {

  Stream<ProcessInfoType> findAll();

  Optional<ProcessInfoType> findBy(ProcessInfoTypeId id);

  boolean exists(ProcessInfoTypeId id);

}
