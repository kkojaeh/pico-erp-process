package pico.erp.process;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.val;
import pico.erp.attachment.AttachmentId;
import pico.erp.audit.annotation.Audit;
import pico.erp.comment.subject.CommentSubjectId;
import pico.erp.item.ItemData;
import pico.erp.process.ProcessEvents.DeletedEvent;
import pico.erp.process.ProcessExceptions.CannotModifyException;
import pico.erp.process.cost.ProcessCost;
import pico.erp.process.difficulty.grade.ProcessDifficultyKind;
import pico.erp.process.info.ProcessInfo;
import pico.erp.process.type.ProcessType;
import pico.erp.shared.event.Event;
import pico.erp.user.UserData;

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
@Audit(alias = "process")
public class Process implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProcessId id;

  String name;

  ItemData itemData;

  ProcessType type;

  ProcessStatusKind status;

  ProcessDifficultyKind difficulty;

  String description;

  UserData manager;

  CommentSubjectId commentSubjectId;

  AttachmentId attachmentId;

  ProcessInfo info;

  ProcessCost estimatedCost;

  BigDecimal spareRate;

  BigDecimal adjustCost;

  String adjustCostReason;

  boolean deleted;

  OffsetDateTime deletedDate;

  public Process() {
    deleted = false;
  }

  public ProcessMessages.CreateResponse apply(ProcessMessages.CreateRequest request) {
    this.id = request.getId();
    this.name = request.getName();
    this.type = request.getType();
    this.status = ProcessStatusKind.DRAFT;
    this.difficulty = request.getDifficulty();
    this.description = request.getDescription();
    this.itemData = request.getItemData();
    this.manager = request.getManager();
    this.commentSubjectId = CommentSubjectId.from(this.id.getValue().toString());
    this.attachmentId = request.getAttachmentId();
    this.info = this.type.createInfo();
    this.adjustCost = request.getAdjustCost();
    this.adjustCostReason = request.getAdjustCostReason();
    this.estimatedCost = this.type.createEstimatedCost(this);
    return new ProcessMessages.CreateResponse(
      Arrays.asList(new ProcessEvents.CreatedEvent(this.id))
    );
  }

  public ProcessMessages.UpdateResponse apply(ProcessMessages.UpdateRequest request) {
    if (!canModify()) {
      throw new CannotModifyException();
    }
    Process old = toBuilder().build();
    this.name = request.getName();
    this.type = request.getType();
    this.difficulty = request.getDifficulty();
    this.description = request.getDescription();
    this.manager = request.getManager();
    this.attachmentId = request.getAttachmentId();
    this.adjustCost = request.getAdjustCost();
    this.adjustCostReason = request.getAdjustCostReason();

    Collection<Event> events = new LinkedList<>();
    if (!this.type.equals(old.type)) {
      if (this.status.isTypeFixed()) {
        throw new ProcessExceptions.CannotChangeTypeException();
      }
      ProcessInfo info = this.type.createInfo();
      if (!info.getClass().equals(old.info.getClass())) {
        this.info = info;
      }
    }
    val calculateEstimatedCostResponse = apply(
      new ProcessMessages.CalculateEstimatedCostRequest());
    events.addAll(calculateEstimatedCostResponse.getEvents());
    events.add(new ProcessEvents.UpdatedEvent(this.id));
    return new ProcessMessages.UpdateResponse(events);
  }

  public ProcessMessages.DeleteResponse apply(ProcessMessages.DeleteRequest request) {
    deleted = true;
    deletedDate = OffsetDateTime.now();
    return new ProcessMessages.DeleteResponse(
      Arrays.asList(new DeletedEvent(this.id))
    );
  }

  public ProcessMessages.CalculateEstimatedCostResponse apply(
    ProcessMessages.CalculateEstimatedCostRequest request) {
    /*
    확정 상태와 상관 없이 공정 유형의 영향을 받는 것이 맞다고 판단됨
    if (!canModify()) {
      throw new CannotModifyException();
    }
     */
    ProcessCost oldEstimatedCost = this.estimatedCost;
    ProcessCost newEstimatedCost = this.type.createEstimatedCost(this);
    if (!oldEstimatedCost.equals(newEstimatedCost)) {
      this.estimatedCost = newEstimatedCost;
      return new ProcessMessages.CalculateEstimatedCostResponse(
        Arrays.asList(new ProcessEvents.EstimatedCostChangedEvent(this.id)));
    }
    return new ProcessMessages.CalculateEstimatedCostResponse(Collections.emptyList());
  }

  public ProcessMessages.RenameResponse apply(ProcessMessages.RenameRequest request) {
    this.name = String.format("[%s] %s", type.getName(), itemData.getName());
    return new ProcessMessages.RenameResponse(
      Collections.emptyList()
    );
  }

  public ProcessMessages.CompletePlanResponse apply(
    ProcessMessages.CompletePlanRequest request) {
    if (status == ProcessStatusKind.DETERMINED) {
      throw new ProcessExceptions.CannotCompletePlanException();
    }
    status = ProcessStatusKind.PLANNED;
    return new ProcessMessages.CompletePlanResponse(
      Arrays.asList(new ProcessEvents.PlannedEvent(this.id))
    );
  }

  public boolean canModify() {
    return status.isModifiable();
  }

  public boolean isPlanned() {
    return status == ProcessStatusKind.PLANNED || status == ProcessStatusKind.DETERMINED;
  }

}
