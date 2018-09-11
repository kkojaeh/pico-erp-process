package pico.erp.process;

import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.data.Role;

@RequiredArgsConstructor
public enum ROLE implements Role {

  PROCESS_TYPE_MANAGER,

  PROCESS_MANAGER,

  PROCESS_ACCESSOR;

  @Id
  @Getter
  private final String id = name();

}
