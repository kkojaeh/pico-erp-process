package pico.erp.process;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pico.erp.shared.data.LocalizedNameable;

@AllArgsConstructor
public enum ProcessStatusKind implements LocalizedNameable {

  DRAFT(true, false),

  PLANNED(true, true),

  DETERMINED(false, true);

  @Getter
  private final boolean modifiable;

  @Getter
  private final boolean typeFixed;

}
