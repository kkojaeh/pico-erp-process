package pico.erp.process.info.type.data;

import java.io.Serializable;
import pico.erp.process.info.ProcessInfo;

public interface ProcessInfoType<T extends ProcessInfo> {

  T create();

  String getDescription();

  ProcessInfoTypeId getId();

  Serializable getMetadata();

  String getName();

  Class<T> getType();

}
