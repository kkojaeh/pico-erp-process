package pico.erp.process

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.config.process.info.PrintingProcessInfo
import pico.erp.item.ItemId
import pico.erp.process.cost.ProcessCostRatesData
import pico.erp.process.difficulty.grade.ProcessDifficultyGradeData
import pico.erp.process.difficulty.grade.ProcessDifficultyKind
import pico.erp.process.info.type.ClassBasedProcessInfoType
import pico.erp.process.type.ProcessTypeId
import pico.erp.process.type.ProcessTypeRequests
import pico.erp.process.type.ProcessTypeService
import pico.erp.shared.IntegrationConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class ProcessServiceSpec extends Specification {


  @Autowired
  ProcessTypeService processTypeService

  @Autowired
  ProcessService processService

  def setup() {
    def infoType = new ClassBasedProcessInfoType(PrintingProcessInfo)
    processTypeService.create(
      new ProcessTypeRequests.CreateRequest(id: ProcessTypeId.from("P1"), name: "인쇄 - UV", infoTypeId: infoType.id,
        baseUnitCost: 100,
        difficultyGrades: [
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.EASY, costRate: 0.9),
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.NORMAL, costRate: 1),
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.HARD, costRate: 1.1),
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.VERY_HARD, costRate: 1.2)
        ], costRates: new ProcessCostRatesData(directLabor: 0.4, indirectLabor: 0.1, indirectMaterial: 0.25, indirectExpenses: 0.25)
      )
    )
    processTypeService.create(
      new ProcessTypeRequests.CreateRequest(id: ProcessTypeId.from("P2"), name: "인쇄 - Offset", infoTypeId: infoType.id,
        baseUnitCost: 100,
        difficultyGrades: [
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.EASY, costRate: 0.9),
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.NORMAL, costRate: 1),
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.HARD, costRate: 1.1),
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.VERY_HARD, costRate: 1.2)
        ], costRates: new ProcessCostRatesData(directLabor: 0.4, indirectLabor: 0.1, indirectMaterial: 0.25, indirectExpenses: 0.25)
      )
    )
    processService.create(
      new ProcessRequests.CreateRequest(
        id: ProcessId.from("process-1"),
        itemId: ItemId.from("item-1"),
        adjustCost: 0,
        name: "품목 명과 공정명 합침",
        typeId: ProcessTypeId.from("P1"),
        difficulty: ProcessDifficultyKind.NORMAL,
        description: "좋은 보통 작업"
      )
    )
  }

  def "아이디로 존재하는 공정 확인"() {
    when:
    def exists = processService.exists(ProcessId.from("process-1"))

    then:
    exists == true
  }

  def "아이디로 존재하지 않는 공정 확인"() {
    when:
    def exists = processService.exists(ProcessId.from("!process-1"))

    then:
    exists == false
  }

  def "아이디로 존재하는 공정을 조회"() {
    when:
    def process = processService.get(ProcessId.from("process-1"))

    then:
    process.typeId.value == "P1"
    process.estimatedCost.directLabor == 40
    process.estimatedCost.indirectLabor == 10
    process.estimatedCost.indirectMaterial == 25
    process.estimatedCost.indirectExpenses == 25
    println process
  }

  def "아이디로 존재하지 않는 공정을 조회"() {
    when:
    processService.get(ProcessId.from("!process-1"))

    then:
    thrown(ProcessExceptions.NotFoundException)
  }

  def "난이도를 변경하면 가격이 변경된다"() {
    when:
    processService.update(
      new ProcessRequests.UpdateRequest(
        id: ProcessId.from("process-1"),
        name: "품목 명과 공정명 합침",
        typeId: ProcessTypeId.from("P1"),
        adjustCost: 0,
        difficulty: ProcessDifficultyKind.EASY,
        description: "좋은 쉬운 작업"
      )
    )
    def process = processService.get(ProcessId.from("process-1"))

    then:
    process.estimatedCost.directLabor == 40 * 0.9
    process.estimatedCost.indirectLabor == 10 * 0.9
    process.estimatedCost.indirectMaterial == 25 * 0.9
    process.estimatedCost.indirectExpenses == 25 * 0.9
  }

}
