package pico.erp.process.core;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;
import pico.erp.process.data.ProcessInfoType;
import pico.erp.process.data.ProcessInfoTypeId;

@Repository
public interface ProcessInfoTypeRepository {

  Stream<ProcessInfoType> findAll();

  Optional<ProcessInfoType> findBy(ProcessInfoTypeId id);

}
