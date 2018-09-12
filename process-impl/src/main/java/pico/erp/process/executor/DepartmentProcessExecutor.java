package pico.erp.process.executor;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pico.erp.process.data.ProcessExecutorId;
import pico.erp.user.data.DepartmentId;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentProcessExecutor implements ProcessExecutor {

  @Getter
  ProcessExecutorId processExecutorId;

  DepartmentId departmentId;

  String departmentName;

  @Getter
  boolean enabled;

  @Override
  public String getProcessExecutorName() {
    return departmentName;
  }

}
