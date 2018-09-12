package pico.erp.process.executor;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pico.erp.company.data.CompanyId;
import pico.erp.process.data.ProcessExecutorId;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyProcessExecutor implements ProcessExecutor {

  @Getter
  ProcessExecutorId processExecutorId;

  CompanyId companyId;

  String companyName;

  @Getter
  boolean enabled;

  @Override
  public String getProcessExecutorName() {
    return companyName;
  }

}
