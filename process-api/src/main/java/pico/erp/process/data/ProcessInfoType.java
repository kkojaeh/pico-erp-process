package pico.erp.process.data;

import java.io.Serializable;

public interface ProcessInfoType<T extends ProcessInfo> {

  T create();

  String getDescription();

  ProcessInfoTypeId getId();

  Serializable getMetadata();

  String getName();

  Class<T> getType();

}
