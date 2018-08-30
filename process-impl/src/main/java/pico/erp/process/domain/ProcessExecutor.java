package pico.erp.process.domain;

import pico.erp.process.data.ProcessExecutorId;

public interface ProcessExecutor {

  ProcessExecutorId getProcessExecutorId();

  String getProcessExecutorName();

  boolean isEnabled();

}
