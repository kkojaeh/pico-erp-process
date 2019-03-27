package pico.erp.process;

import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.data.Role;

public final class ProcessApi {

  @RequiredArgsConstructor
  public enum Roles implements Role {

    PROCESS_TYPE_MANAGER,

    PROCESS_MANAGER,

    PROCESS_ACCESSOR;

    @Id
    @Getter
    private final String id = name();

  }

}
