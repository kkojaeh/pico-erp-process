package pico.erp.process

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.config.process.info.PrintingProcessInfo
import pico.erp.process.cost.ProcessCostRatesData
import pico.erp.process.difficulty.grade.ProcessDifficultyGradeData
import pico.erp.process.difficulty.grade.ProcessDifficultyKind
import pico.erp.process.info.type.ClassBasedProcessInfoType
import pico.erp.process.type.ProcessTypeExceptions
import pico.erp.process.type.ProcessTypeId
import pico.erp.process.type.ProcessTypeRequests
import pico.erp.process.type.ProcessTypeService
import pico.erp.shared.IntegrationConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
class ProcessTypeServiceSpec extends Specification {

  @Autowired
  ProcessTypeService processTypeService

  def setup() {
    def infoType = new ClassBasedProcessInfoType(PrintingProcessInfo)
    processTypeService.create(
      new ProcessTypeRequests.CreateRequest(id: ProcessTypeId.from("P1"), name: "인쇄 - UV", infoTypeId: infoType.id,
        baseUnitCost: 100,
        lossRate: 0.01,
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
        lossRate: 0.01,
        difficultyGrades: [
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.EASY, costRate: 0.9),
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.NORMAL, costRate: 1),
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.HARD, costRate: 1.1),
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.VERY_HARD, costRate: 1.2)
        ], costRates: new ProcessCostRatesData(directLabor: 0.4, indirectLabor: 0.1, indirectMaterial: 0.25, indirectExpenses: 0.25)
      )
    )
  }

  def "아이디로 존재하는 공정 유형 확인"() {
    when:
    def exists = processTypeService.exists(ProcessTypeId.from("P1"))

    then:
    exists == true
  }

  def "아이디로 존재하지 않는 공정 유형 확인"() {
    when:
    def exists = processTypeService.exists(ProcessTypeId.from("!P1"))

    then:
    exists == false
  }

  def "아이디로 존재하는 공정 유형를 조회"() {
    when:
    def process = processTypeService.get(ProcessTypeId.from("P1"))

    then:
    process.id.value == "P1"
    process.name == "인쇄 - UV"
  }

  def "아이디로 존재하지 않는 공정 유형를 조회"() {
    when:
    processTypeService.get(ProcessTypeId.from("!P1"))

    then:
    thrown(ProcessTypeExceptions.NotFoundException)
  }

}
