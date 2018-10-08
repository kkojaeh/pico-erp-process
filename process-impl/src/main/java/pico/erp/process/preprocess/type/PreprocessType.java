package pico.erp.process.preprocess.type;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pico.erp.audit.annotation.Audit;
import pico.erp.process.info.ProcessInfo;
import pico.erp.process.info.type.ProcessInfoType;
import pico.erp.shared.event.Event;

/**
 * 공정의 유형 공정을 생성한다
 *
 *
 * - 인쇄-UV - 출력 - 금은박 - 형압 - 톰슨 - 합지 - 접착
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Audit(alias = "preprocess-type")
public class PreprocessType implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  PreprocessTypeId id;

  String name;

  /**
   * 사전 공정 기준 비용으로 사전 공정의 비용으로 기본 설정된다.
   */
  BigDecimal baseCost;

  @Transient
  ProcessInfoType infoType;

  public PreprocessType() {
  }

  public PreprocessTypeMessages.CreateResponse apply(PreprocessTypeMessages.CreateRequest request) {
    this.id = request.getId();
    this.name = request.getName();
    this.baseCost = request.getBaseCost();
    this.infoType = request.getInfoType();
    return new PreprocessTypeMessages.CreateResponse(
      Arrays.asList(new PreprocessTypeEvents.CreatedEvent(this.id))
    );
  }

  public PreprocessTypeMessages.UpdateResponse apply(PreprocessTypeMessages.UpdateRequest request) {
    PreprocessType old = toBuilder().build();
    List<Event> events = new LinkedList<>();
    this.name = request.getName();
    this.baseCost = request.getBaseCost();
    this.infoType = request.getInfoType();
    events.add(new PreprocessTypeEvents.UpdatedEvent(this.id));
    if (
      !Optional.ofNullable(old.baseCost)
        .equals(Optional.ofNullable(this.baseCost))) {
      events.add(new PreprocessTypeEvents.CostChangedEvent(this.id));
    }
    return new PreprocessTypeMessages.UpdateResponse(events);
  }

  public PreprocessTypeMessages.DeleteResponse apply(PreprocessTypeMessages.DeleteRequest request) {
    return new PreprocessTypeMessages.DeleteResponse(
      Arrays.asList(new PreprocessTypeEvents.DeletedEvent(this.id))
    );
  }

  public ProcessInfo createInfo() {
    return infoType.create();
  }

}
