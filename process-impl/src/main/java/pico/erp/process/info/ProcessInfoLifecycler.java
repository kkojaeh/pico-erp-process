package pico.erp.process.info;

import pico.erp.process.info.type.ProcessInfoTypeId;

public interface ProcessInfoLifecycler {

  ProcessInfo initialize(ProcessInfoTypeId typeId);

  ProcessInfo parse(ProcessInfoTypeId typeId, String text);

  String stringify(ProcessInfoTypeId typeId, ProcessInfo info);


}
