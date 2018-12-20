package pico.erp.process.preparation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pico.erp.shared.data.LocalizedNameable;

@AllArgsConstructor
public enum ProcessPreparationStatusKind implements LocalizedNameable {

  /**
   * 작성중
   */
  DRAFT(true, true, false, true),

  /**
   * 요청됨
   */
  COMMITTED(false, false, true, true),

  /**
   * 취소됨
   */
  CANCELED(false, false, false, false),

  /**
   * 취소됨
   */
  COMPLETED(false, false, false, false);


  @Getter
  private final boolean updatable;

  @Getter
  private final boolean committable;

  @Getter
  private final boolean completable;

  @Getter
  private final boolean cancelable;

}
