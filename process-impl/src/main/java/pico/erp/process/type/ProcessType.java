package pico.erp.process.type;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.val;
import pico.erp.audit.annotation.Audit;
import pico.erp.process.Process;
import pico.erp.process.cost.ProcessCost;
import pico.erp.process.cost.ProcessCostRates;
import pico.erp.process.difficulty.ProcessDifficulty;
import pico.erp.process.difficulty.ProcessDifficultyKind;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.process.preparation.ProcessPreparationExceptions;
import pico.erp.process.preparation.type.ProcessPreparationType;
import pico.erp.process.type.ProcessTypeEvents.CostChangedEvent;
import pico.erp.process.type.ProcessTypeEvents.CreatedEvent;
import pico.erp.process.type.ProcessTypeEvents.DeletedEvent;
import pico.erp.process.type.ProcessTypeEvents.UpdatedEvent;
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
@Audit(alias = "process-type")
public class ProcessType implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProcessTypeId id;

  String name;

  /**
   * 공정 기준 단가 해당 공정 유형의 기준이 되는 단가 등급 난이도에 따라서 공정의 단가가 변경된다
   */
  BigDecimal baseUnitCost;

  ProcessInfoTypeId infoTypeId;

  BigDecimal lossRate;

  ProcessCostRates costRates;

  Map<ProcessDifficultyKind, ProcessDifficulty> difficulties;

  List<ProcessPreparationType> preparationTypes;

  public ProcessType() {
    preparationTypes = new LinkedList<>();
  }

  public ProcessTypeMessages.CreateResponse apply(ProcessTypeMessages.CreateRequest request) {
    this.id = request.getId();
    this.name = request.getName();
    this.baseUnitCost = request.getBaseUnitCost();
    this.lossRate = request.getLossRate();
    this.costRates = request.getCostRates();
    this.infoTypeId = request.getInfoTypeId();
    this.difficulties = request.getDifficulties();
    return new ProcessTypeMessages.CreateResponse(
      Arrays.asList(new CreatedEvent(this.id))
    );
  }

  public ProcessTypeMessages.UpdateResponse apply(ProcessTypeMessages.UpdateRequest request) {
    val old = toBuilder().build();
    val events = new LinkedList<Event>();
    this.name = request.getName();
    this.baseUnitCost = request.getBaseUnitCost();
    this.lossRate = request.getLossRate();
    this.costRates = request.getCostRates();
    this.infoTypeId = request.getInfoTypeId();
    this.difficulties = request.getDifficulties();
    events.add(new UpdatedEvent(this.id));
    if (
      !Optional.ofNullable(old.baseUnitCost)
        .equals(Optional.ofNullable(this.baseUnitCost)) ||
        !Optional.ofNullable(old.costRates)
          .equals(Optional.ofNullable(this.costRates))) {
      events.add(new CostChangedEvent(this.id));
    }
    return new ProcessTypeMessages.UpdateResponse(events);
  }

  public ProcessTypeMessages.DeleteResponse apply(ProcessTypeMessages.DeleteRequest request) {
    return new ProcessTypeMessages.DeleteResponse(
      Arrays.asList(new DeletedEvent(this.id))
    );
  }

  public ProcessTypeMessages.AddPreprocessTypeResponse apply(
    ProcessTypeMessages.AddPreprocessTypeRequest request) {
    val preparationType = request.getPreparationType();
    if (preparationTypes.contains(preparationType)) {
      throw new ProcessPreparationExceptions.AlreadyExistsException();
    }
    preparationTypes.add(preparationType);
    return new ProcessTypeMessages.AddPreprocessTypeResponse(
      Collections.emptyList()
    );
  }

  public ProcessTypeMessages.RemovePreprocessTypeResponse apply(
    ProcessTypeMessages.RemovePreprocessTypeRequest request) {
    val preparationType = request.getPreparationType();
    if (!preparationTypes.contains(preparationType)) {
      throw new ProcessPreparationExceptions.NotFoundException();
    }
    preparationTypes.remove(preparationType);
    return new ProcessTypeMessages.RemovePreprocessTypeResponse(
      Collections.emptyList()
    );
  }

  public ProcessTypeMessages.PrepareImportResponse apply(
    ProcessTypeMessages.PrepareImportRequest request) {

    return new ProcessTypeMessages.PrepareImportResponse(
      Collections.emptyList()
    );
  }

  public ProcessCost createEstimatedCost(Process process) {
    val level = Optional.ofNullable(process.getDifficulty())
      .orElse(ProcessDifficultyKind.NORMAL);
    val difficulty = difficulties.get(level);
    val costRate = Optional.ofNullable(difficulty.getCostRate())
      .orElse(BigDecimal.ONE);
    val cost = baseUnitCost.multiply(costRate).add(process.getAdjustCost());
    return costRates.calculate(cost);
  }

}
