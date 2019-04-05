package pico.erp.process.preparation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pico.erp.process.Process;
import pico.erp.process.preparation.type.ProcessPreparationType;
import pico.erp.shared.event.Event;

/**
 * 공정은 하나의 품목에 국한 될수 없다 지게차 이동 및 재고 조사 등의 작업은 도출되는 결과가 품목이 아닐 수 없다 작업과 공정의 차이가 혼동 될 수 있지만 투입과 결과라는
 * 내용은 이미 BOM 을 통해 명시하고 있기 때문에 해당 공정에서는 공정에 대한 결과를 명시하지 않는다
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessPreparation implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProcessPreparationId id;

  String name;

  Process process;

  ProcessPreparationType type;

  ProcessPreparationStatusKind status;

  BigDecimal chargeCost;

  String description;

  boolean deleted;

  OffsetDateTime deletedDate;

  OffsetDateTime canceledDate;

  OffsetDateTime committedDate;

  OffsetDateTime completedDate;

  public ProcessPreparation() {
    deleted = false;
  }

  public ProcessPreparationMessages.CreateResponse apply(
    ProcessPreparationMessages.CreateRequest request) {
    this.id = request.getId();
    this.process = request.getProcess();
    this.type = request.getType();
    this.status = ProcessPreparationStatusKind.DRAFT;
    this.description = request.getDescription();
    this.chargeCost = request.getChargeCost();
    this.name = type.getName();
    return new ProcessPreparationMessages.CreateResponse(
      Arrays.asList(new ProcessPreparationEvents.CreatedEvent(this.id, this.type.getId()))
    );
  }

  public ProcessPreparationMessages.UpdateResponse apply(
    ProcessPreparationMessages.UpdateRequest request) {
    if (!isUpdatable()) {
      throw new ProcessPreparationExceptions.CannotUpdateException();
    }
    this.description = request.getDescription();
    Collection<Event> events = new LinkedList<>();
    events.add(new ProcessPreparationEvents.UpdatedEvent(this.id, this.type.getId()));
    return new ProcessPreparationMessages.UpdateResponse(events);
  }

  public ProcessPreparationMessages.DeleteResponse apply(
    ProcessPreparationMessages.DeleteRequest request) {
    deleted = true;
    deletedDate = OffsetDateTime.now();
    return new ProcessPreparationMessages.DeleteResponse(
      Arrays.asList(new ProcessPreparationEvents.DeletedEvent(this.id, this.type.getId()))
    );
  }

  public ProcessPreparationMessages.CommitResponse apply(
    ProcessPreparationMessages.CommitRequest request) {
    if (!isCommittable()) {
      throw new ProcessPreparationExceptions.CannotCommitException();
    }
    status = ProcessPreparationStatusKind.COMMITTED;
    committedDate = OffsetDateTime.now();
    return new ProcessPreparationMessages.CommitResponse(
      Arrays.asList(new ProcessPreparationEvents.CommittedEvent(this.id, this.type.getId()))
    );
  }

  public ProcessPreparationMessages.CompleteResponse apply(
    ProcessPreparationMessages.CompleteRequest request) {
    if (!isCompletable()) {
      throw new ProcessPreparationExceptions.CannotCompleteException();
    }
    status = ProcessPreparationStatusKind.COMPLETED;
    completedDate = OffsetDateTime.now();
    return new ProcessPreparationMessages.CompleteResponse(
      Arrays.asList(new ProcessPreparationEvents.CompletedEvent(this.id, this.type.getId()))
    );
  }

  public ProcessPreparationMessages.CancelResponse apply(
    ProcessPreparationMessages.CancelRequest request) {
    if (!isCancelable()) {
      throw new ProcessPreparationExceptions.CannotCancelException();
    }
    status = ProcessPreparationStatusKind.CANCELED;
    canceledDate = OffsetDateTime.now();
    return new ProcessPreparationMessages.CancelResponse(
      Arrays.asList(new ProcessPreparationEvents.CanceledEvent(this.id, this.type.getId()))
    );
  }

  public boolean isCancelable() {
    return status.isCancelable();
  }

  public boolean isCommittable() {
    return status.isCommittable();
  }

  public boolean isCompletable() {
    return status.isCompletable();
  }

  public boolean isDone() {
    return status == ProcessPreparationStatusKind.CANCELED
      || status == ProcessPreparationStatusKind.COMPLETED;
  }

  public boolean isUpdatable() {
    return status.isUpdatable();
  }


}
