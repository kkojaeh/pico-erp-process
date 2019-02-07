package pico.erp.process;

import pico.erp.shared.data.LocalizedNameable;

public enum ProcessStatusKind implements LocalizedNameable {

  DRAFT,

  PLANNED,

  DETERMINED;

  public boolean isTypeFixed() {
    return this == PLANNED || this == DETERMINED;
  }

  public boolean isUpdatable() {
    return this == DRAFT || this == PLANNED;
  }

}
