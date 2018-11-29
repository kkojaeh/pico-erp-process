package pico.erp.process

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.process.cost.ProcessCostRatesData
import pico.erp.process.difficulty.grade.ProcessDifficultyGradeData
import pico.erp.process.difficulty.grade.ProcessDifficultyKind
import pico.erp.process.info.type.ProcessInfoTypeId
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

  def processTypeId = ProcessTypeId.from("TEST")

  def unknownProcessTypeId = ProcessTypeId.from("unknown")

  def processTypeName = "인쇄 - UV"

  def processInfoTypeId = ProcessInfoTypeId.from("printing")

  def difficultyGrades = [
    new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.EASY, costRate: 0.9),
    new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.NORMAL, costRate: 1),
    new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.HARD, costRate: 1.1),
    new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.VERY_HARD, costRate: 1.2)
  ]

  def processCostRates = new ProcessCostRatesData(directLabor: 0.4, indirectLabor: 0.1, indirectMaterial: 0.25, indirectExpenses: 0.25)

  def setup() {
    processTypeService.create(
      new ProcessTypeRequests.CreateRequest(id: processTypeId,
        name: processTypeName,
        infoTypeId: processInfoTypeId,
        baseUnitCost: 100,
        lossRate: 0.01,
        difficultyGrades: difficultyGrades,
        costRates: processCostRates
      )
    )
  }

  def "존재 - 아이디로 확인"() {
    when:
    def exists = processTypeService.exists(processTypeId)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = processTypeService.exists(unknownProcessTypeId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def processType = processTypeService.get(processTypeId)

    then:
    processType.id == processTypeId
    processType.name == processTypeName
    processType.lossRate == 0.01
    processType.difficultyGrades[0].difficulty == difficultyGrades[0].difficulty
    processType.difficultyGrades[1].difficulty == difficultyGrades[1].difficulty
    processType.difficultyGrades[2].difficulty == difficultyGrades[2].difficulty
    processType.difficultyGrades[3].difficulty == difficultyGrades[3].difficulty
    processType.difficultyGrades[0].costRate == difficultyGrades[0].costRate
    processType.difficultyGrades[1].costRate == difficultyGrades[1].costRate
    processType.difficultyGrades[2].costRate == difficultyGrades[2].costRate
    processType.difficultyGrades[3].costRate == difficultyGrades[3].costRate
    processType.costRates.directLabor == processCostRates.directLabor
    processType.costRates.indirectLabor == processCostRates.indirectLabor
    processType.costRates.indirectMaterial == processCostRates.indirectMaterial
    processType.costRates.indirectExpenses == processCostRates.indirectExpenses
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    processTypeService.get(unknownProcessTypeId)

    then:
    thrown(ProcessTypeExceptions.NotFoundException)
  }

}
