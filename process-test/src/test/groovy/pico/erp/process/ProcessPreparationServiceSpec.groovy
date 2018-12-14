package pico.erp.process

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.process.difficulty.ProcessDifficultyKind
import pico.erp.process.preparation.ProcessPreparationExceptions
import pico.erp.process.preparation.ProcessPreparationId
import pico.erp.process.preparation.ProcessPreparationRequests
import pico.erp.process.preparation.ProcessPreparationService
import pico.erp.process.preparation.type.ProcessPreparationTypeId
import pico.erp.process.preparation.type.ProcessPreparationTypeService
import pico.erp.process.type.ProcessTypeId
import pico.erp.shared.IntegrationConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
class ProcessPreparationServiceSpec extends Specification {

  @Autowired
  ProcessPreparationTypeService preparationTypeService

  @Autowired
  ProcessPreparationService preparationService

  @Autowired
  ProcessService processService

  def preprocessTypeId = ProcessPreparationTypeId.from("WP")

  def processId = ProcessId.from("process-1")

  def processTypeId = ProcessTypeId.from("PO")

  def preprocessId = ProcessPreparationId.from("PP1")

  def unknownPreprocessId = ProcessPreparationId.from("unknown")

  def setup() {
    processService.create(
      new ProcessRequests.CreateRequest(
        id: processId,
        typeId: processTypeId,
        lossRate: 0.01,
        adjustCost: 0,
        difficulty: ProcessDifficultyKind.NORMAL,
        description: "좋은 보통 작업"
      )
    )

    preparationService.create(
      new ProcessPreparationRequests.CreateRequest(
        id: preprocessId,
        processId: processId,
        typeId: preprocessTypeId
      )
    )
  }

  def "존재 - 아이디로 확인"() {
    when:
    def exists = preparationService.exists(preprocessId)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = preparationService.exists(unknownPreprocessId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def preprocess = preparationService.get(preprocessId)

    then:
    preprocess.id == preprocessId
    preprocess.processId == processId
    preprocess.typeId == preprocessTypeId
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    preparationService.get(unknownPreprocessId)

    then:
    thrown(ProcessPreparationExceptions.NotFoundException)
  }

}
