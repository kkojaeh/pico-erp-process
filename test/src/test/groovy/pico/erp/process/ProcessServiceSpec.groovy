package pico.erp.process

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.item.ItemId
import pico.erp.process.cost.ProcessCostRatesData
import pico.erp.process.difficulty.ProcessDifficultyData
import pico.erp.process.difficulty.ProcessDifficultyKind
import pico.erp.process.info.type.ProcessInfoTypeId
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


  def processTypeId = ProcessTypeId.from("TEST")

  def processTypeName = "인쇄 - UV"

  def processInfoTypeId = ProcessInfoTypeId.from("printing")

  def difficulties = [
    (ProcessDifficultyKind.EASY)     : new ProcessDifficultyData(costRate: 0.9),
    (ProcessDifficultyKind.NORMAL)   : new ProcessDifficultyData(costRate: 1),
    (ProcessDifficultyKind.HARD)     : new ProcessDifficultyData(costRate: 1.1),
    (ProcessDifficultyKind.VERY_HARD): new ProcessDifficultyData(costRate: 1.2)
  ]

  def processCostRates = new ProcessCostRatesData(directLabor: 0.4, indirectLabor: 0.1, indirectMaterial: 0.25, indirectExpenses: 0.25)

  def processId = ProcessId.from("process-1")

  def unknownProcessId = ProcessId.from("unknown")

  def description = "좋은 보통 작업"

  def itemId = ItemId.from("item-1")

  def inputRate = 1

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

    processService.create(
      new ProcessRequests.CreateRequest(
        id: processId,
        adjustCost: 0,
        lossRate: 0.01,
        typeId: processTypeId,
        difficulty: ProcessDifficultyKind.NORMAL,
        description: description,
        itemId: itemId,
        inputRate: inputRate
      )
    )
  }

  def "존재 - 아이디로 확인"() {
    when:
    def exists = processService.exists(processId)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = processService.exists(unknownProcessId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def process = processService.get(processId)

    then:
    process.adjustCost == 0
    process.lossRate == 0.01
    process.typeId == processTypeId
    process.difficulty == ProcessDifficultyKind.NORMAL
    process.description == description
    process.estimatedCost.directLabor == 40
    process.estimatedCost.indirectLabor == 10
    process.estimatedCost.indirectMaterial == 25
    process.estimatedCost.indirectExpenses == 25
    process.itemId == itemId
    process.inputRate == inputRate
    process.order == 0
    println process
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    processService.get(unknownProcessId)

    then:
    thrown(ProcessExceptions.NotFoundException)
  }

  def updateProcessEasyDifficulty() {
    processService.update(
      new ProcessRequests.UpdateRequest(
        id: processId,
        typeId: processTypeId,
        lossRate: 0.01,
        adjustCost: 0,
        difficulty: ProcessDifficultyKind.EASY,
        description: description
      )
    )
  }

  def "수정 - 난이도를 변경하면 가격이 변경된다"() {
    when:
    updateProcessEasyDifficulty()
    def process = processService.get(processId)

    then:
    process.estimatedCost.directLabor == 40 * 0.9
    process.estimatedCost.indirectLabor == 10 * 0.9
    process.estimatedCost.indirectMaterial == 25 * 0.9
    process.estimatedCost.indirectExpenses == 25 * 0.9
  }

}
