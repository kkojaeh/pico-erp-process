package pico.erp.process

import kkojaeh.spring.boot.component.SpringBootTestComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.CompanyApplication
import pico.erp.item.ItemApplication
import pico.erp.process.cost.ProcessCostRatesData
import pico.erp.process.difficulty.ProcessDifficultyData
import pico.erp.process.difficulty.ProcessDifficultyKind
import pico.erp.process.info.type.ProcessInfoTypeId
import pico.erp.process.type.ProcessTypeExceptions
import pico.erp.process.type.ProcessTypeId
import pico.erp.process.type.ProcessTypeRequests
import pico.erp.process.type.ProcessTypeService
import pico.erp.shared.TestParentApplication
import spock.lang.Specification

@SpringBootTest(classes = [ProcessApplication, TestConfig])
@SpringBootTestComponent(parent = TestParentApplication, siblings = [ItemApplication, CompanyApplication])
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

  def difficulties = [
    (ProcessDifficultyKind.EASY)     : new ProcessDifficultyData(costRate: 0.9),
    (ProcessDifficultyKind.NORMAL)   : new ProcessDifficultyData(costRate: 1),
    (ProcessDifficultyKind.HARD)     : new ProcessDifficultyData(costRate: 1.1),
    (ProcessDifficultyKind.VERY_HARD): new ProcessDifficultyData(costRate: 1.2)
  ]

  def processCostRates = new ProcessCostRatesData(directLabor: 0.4, indirectLabor: 0.1, indirectMaterial: 0.25, indirectExpenses: 0.25)

  def setup() {
    processTypeService.create(
      new ProcessTypeRequests.CreateRequest(id: processTypeId,
        name: processTypeName,
        infoTypeId: processInfoTypeId,
        baseUnitCost: 100,
        lossRate: 0.01,
        difficulties: difficulties,
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
    processType.difficulties[ProcessDifficultyKind.EASY].costRate == difficulties[ProcessDifficultyKind.EASY].costRate
    processType.difficulties[ProcessDifficultyKind.NORMAL].costRate == difficulties[ProcessDifficultyKind.NORMAL].costRate
    processType.difficulties[ProcessDifficultyKind.HARD].costRate == difficulties[ProcessDifficultyKind.HARD].costRate
    processType.difficulties[ProcessDifficultyKind.VERY_HARD].costRate == difficulties[ProcessDifficultyKind.VERY_HARD].costRate
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
