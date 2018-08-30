package pico.erp.process;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.data.Menu;
import pico.erp.shared.data.Role;

@RequiredArgsConstructor
public enum ROLE implements Role {

  PROCESS_TYPE_MANAGER(
    Stream.of(MENU.PROCESS_TYPE_MANAGEMENT, MENU.PREPROCESS_TYPE_MANAGEMENT)
      .collect(Collectors.toSet())
  ),

  PROCESS_MANAGER(
    Stream.of(MENU.PROCESS_MANAGEMENT)
      .collect(Collectors.toSet())
  ),

  PROCESS_ACCESSOR(Collections.emptySet());

  @Id
  @Getter
  private final String id = name();

  @Transient
  @Getter
  @NonNull
  private Set<Menu> menus;
}
