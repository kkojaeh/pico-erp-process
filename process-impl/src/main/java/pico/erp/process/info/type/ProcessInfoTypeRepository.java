package pico.erp.process.info.type;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;
import pico.erp.process.info.type.data.ProcessInfoType;
import pico.erp.process.info.type.data.ProcessInfoTypeId;

@Repository
public interface ProcessInfoTypeRepository {

  Stream<ProcessInfoType> findAll();

  Optional<ProcessInfoType> findBy(ProcessInfoTypeId id);

}
