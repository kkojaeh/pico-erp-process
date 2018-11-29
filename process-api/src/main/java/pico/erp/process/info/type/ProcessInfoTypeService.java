package pico.erp.process.info.type;

import java.util.List;

public interface ProcessInfoTypeService {

  ProcessInfoTypeData get(ProcessInfoTypeId id);

  List<ProcessInfoTypeData> getAll();

  boolean exists(ProcessInfoTypeId id);

}
