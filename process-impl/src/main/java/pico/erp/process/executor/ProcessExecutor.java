package pico.erp.process.executor;

public interface ProcessExecutor {

  ProcessExecutorId getProcessExecutorId();

  String getProcessExecutorName();

  boolean isEnabled();

}
