package pico.erp.process;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import pico.erp.item.ItemData;
import pico.erp.item.spec.ItemSpecCode;
import pico.erp.process.ProcessEvents.DeletedEvent;
import pico.erp.process.ProcessExceptions.CannotUpdateException;
import pico.erp.process.cost.ProcessCost;
import pico.erp.process.difficulty.ProcessDifficultyKind;
import pico.erp.process.info.ProcessInfo;
import pico.erp.process.type.ProcessType;
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
public class Process implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProcessId id;

  String name;

  ProcessType type;

  ProcessStatusKind status;

  ProcessDifficultyKind difficulty;

  String description;

  BigDecimal lossRate;

  ProcessInfo info;

  ProcessCost estimatedCost;

  BigDecimal spareRate;

  BigDecimal adjustCost;

  String adjustCostReason;

  boolean deleted;

  LocalDateTime deletedDate;

  ItemData item;

  int order;

  public Process() {
    deleted = false;
  }

  public ProcessMessages.Create.Response apply(ProcessMessages.Create.Request request) {
    this.id = request.getId();
    this.type = request.getType();
    this.status = ProcessStatusKind.DRAFT;
    this.difficulty = request.getDifficulty();
    this.description = request.getDescription();
    this.info = request.getProcessInfoLifecycler().initialize(this.type.getInfoTypeId());
    this.lossRate = request.getLossRate();
    this.adjustCost = request.getAdjustCost();
    this.adjustCostReason = request.getAdjustCostReason();
    this.item = request.getItem();
    this.order = request.getOrder();
    this.name = type.getName();
    this.estimatedCost = this.type.createEstimatedCost(this);
    return new ProcessMessages.Create.Response(
      Arrays.asList(new ProcessEvents.CreatedEvent(this.id))
    );
  }

  public ProcessMessages.Update.Response apply(ProcessMessages.Update.Request request) {
    if (!isUpdatable()) {
      throw new CannotUpdateException();
    }
    Process old = toBuilder().build();
    this.type = request.getType();
    this.difficulty = request.getDifficulty();
    this.description = request.getDescription();
    this.lossRate = request.getLossRate();
    this.adjustCost = request.getAdjustCost();
    this.adjustCostReason = request.getAdjustCostReason();

    Collection<Event> events = new LinkedList<>();
    boolean typeChanged = !this.type.equals(old.type);
    if (typeChanged) {
      if (this.status.isTypeFixed()) {
        throw new ProcessExceptions.CannotChangeTypeException();
      } else {
        this.name = type.getName();
      }
      ProcessInfo info = request.getProcessInfoLifecycler().initialize(this.type.getInfoTypeId());
      if (info.getClass().equals(request.getInfo().getClass())) {
        this.info = request.getInfo();
      } else {
        this.info = info;
      }
    } else {
      this.info = request.getInfo();
    }
    val calculateEstimatedCostResponse = apply(
      new ProcessMessages.CalculateEstimatedCost.Request());
    events.addAll(calculateEstimatedCostResponse.getEvents());
    events.add(new ProcessEvents.UpdatedEvent(this.id));
    return new ProcessMessages.Update.Response(events);
  }

  public ProcessMessages.Delete.Response apply(ProcessMessages.Delete.Request request) {
    deleted = true;
    deletedDate = LocalDateTime.now();
    return new ProcessMessages.Delete.Response(
      Arrays.asList(new DeletedEvent(this.id))
    );
  }

  public ProcessMessages.CalculateEstimatedCost.Response apply(
    ProcessMessages.CalculateEstimatedCost.Request request) {
    /*
    확정 상태와 상관 없이 공정 유형의 영향을 받는 것이 맞다고 판단됨
    if (!isUpdatable()) {
      throw new CannotUpdateException();
    }
     */
    ProcessCost oldEstimatedCost = this.estimatedCost;
    ProcessCost newEstimatedCost = this.type.createEstimatedCost(this);
    if (!oldEstimatedCost.equals(newEstimatedCost)) {
      this.estimatedCost = newEstimatedCost;
      return new ProcessMessages.CalculateEstimatedCost.Response(
        Arrays.asList(new ProcessEvents.EstimatedCostChangedEvent(this.id)));
    }
    return new ProcessMessages.CalculateEstimatedCost.Response(Collections.emptyList());
  }

  public ProcessMessages.CompletePlan.Response apply(
    ProcessMessages.CompletePlan.Request request) {
    if (status == ProcessStatusKind.DETERMINED) {
      throw new ProcessExceptions.CannotCompletePlanException();
    }
    status = ProcessStatusKind.PLANNED;
    return new ProcessMessages.CompletePlan.Response(
      Arrays.asList(new ProcessEvents.PlannedEvent(this.id))
    );
  }

  public ProcessMessages.ChangeOrder.Response apply(
    ProcessMessages.ChangeOrder.Request request) {
    if (status == ProcessStatusKind.DETERMINED) {
      throw new ProcessExceptions.CannotChangeOrderException();
    }
    order = request.getOrder();
    return new ProcessMessages.ChangeOrder.Response(
      Arrays.asList(new ProcessEvents.UpdatedEvent(this.id))
    );
  }



  public boolean isUpdatable() {
    return status.isUpdatable();
  }

  public boolean isPlanned() {
    return status == ProcessStatusKind.PLANNED || status == ProcessStatusKind.DETERMINED;
  }

  public ItemSpecCode getItemSpecCode() {
    return ItemSpecCode.from(type.getId().getValue());
  }

}
