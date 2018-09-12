package pico.erp.process.type;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
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
import lombok.val;
import pico.erp.audit.annotation.Audit;
import pico.erp.process.Process;
import pico.erp.process.ProcessCost;
import pico.erp.process.ProcessCostRates;
import pico.erp.process.ProcessDifficultyGrade;
import pico.erp.process.preprocess.PreprocessExceptions;
import pico.erp.process.preprocess.type.PreprocessType;
import pico.erp.process.type.ProcessTypeEvents.CostChangedEvent;
import pico.erp.process.type.ProcessTypeEvents.CreatedEvent;
import pico.erp.process.type.ProcessTypeEvents.DeletedEvent;
import pico.erp.process.type.ProcessTypeEvents.UpdatedEvent;
import pico.erp.process.data.ProcessDifficultyKind;
import pico.erp.process.info.ProcessInfo;
import pico.erp.process.info.type.data.ProcessInfoType;
import pico.erp.process.type.data.ProcessTypeId;
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

  @Transient
  ProcessInfoType infoType;

  ProcessCostRates costRates;

  List<ProcessDifficultyGrade> difficultyGrades;

  List<PreprocessType> preprocessTypes;

  public ProcessType() {
    preprocessTypes = new LinkedList<>();
  }

  public ProcessTypeMessages.CreateResponse apply(ProcessTypeMessages.CreateRequest request) {
    this.id = request.getId();
    this.name = request.getName();
    this.baseUnitCost = request.getBaseUnitCost();
    this.costRates = request.getCostRates();
    this.infoType = request.getInfoType();
    this.difficultyGrades = request.getDifficultyGrades();
    return new ProcessTypeMessages.CreateResponse(
      Arrays.asList(new CreatedEvent(this.id))
    );
  }

  public ProcessTypeMessages.UpdateResponse apply(ProcessTypeMessages.UpdateRequest request) {
    val old = toBuilder().build();
    val events = new LinkedList<Event>();
    this.name = request.getName();
    this.baseUnitCost = request.getBaseUnitCost();
    this.costRates = request.getCostRates();
    this.infoType = request.getInfoType();
    this.difficultyGrades = request.getDifficultyGrades();
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
    val preprocessType = request.getPreprocessType();
    if (preprocessTypes.contains(preprocessType)) {
      throw new PreprocessExceptions.AlreadyExistsException();
    }
    preprocessTypes.add(preprocessType);
    return new ProcessTypeMessages.AddPreprocessTypeResponse(
      Collections.emptyList()
    );
  }

  public ProcessTypeMessages.RemovePreprocessTypeResponse apply(
    ProcessTypeMessages.RemovePreprocessTypeRequest request) {
    val preprocessType = request.getPreprocessType();
    if (!preprocessTypes.contains(preprocessType)) {
      throw new PreprocessExceptions.NotFoundException();
    }
    preprocessTypes.remove(preprocessType);
    return new ProcessTypeMessages.RemovePreprocessTypeResponse(
      Collections.emptyList()
    );
  }

  public ProcessCost createEstimatedCost(Process process) {
    val difficulty = Optional.ofNullable(process.getDifficulty())
      .orElse(ProcessDifficultyKind.NORMAL);
    val grade = difficultyGrades.stream()
      .filter(g -> difficulty == g.getDifficulty())
      .findFirst().get();
    val cost = baseUnitCost.multiply(grade.getCostRate()).add(process.getAdjustCost());
    return costRates.calculate(cost);
  }

  public ProcessInfo createInfo() {
    return infoType.create();
  }

}
