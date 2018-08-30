package pico.erp.process;

import java.util.List;
import pico.erp.process.data.ProcessInfoTypeData;
import pico.erp.process.data.ProcessInfoTypeId;

public interface ProcessInfoTypeService {

  ProcessInfoTypeData get(ProcessInfoTypeId id);

  List<ProcessInfoTypeData> getAll();

}
