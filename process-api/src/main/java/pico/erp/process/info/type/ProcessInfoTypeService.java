package pico.erp.process.info.type;

import java.util.List;
import pico.erp.process.info.type.data.ProcessInfoTypeData;
import pico.erp.process.info.type.data.ProcessInfoTypeId;

public interface ProcessInfoTypeService {

  ProcessInfoTypeData get(ProcessInfoTypeId id);

  List<ProcessInfoTypeData> getAll();

}
