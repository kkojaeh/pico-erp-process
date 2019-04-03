package pico.erp.process

import kkojaeh.spring.boot.component.SpringBootTestComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.item.ItemId
import pico.erp.process.difficulty.ProcessDifficultyKind
import pico.erp.process.preparation.*
import pico.erp.process.preparation.type.ProcessPreparationTypeId
import pico.erp.process.preparation.type.ProcessPreparationTypeService
import pico.erp.process.type.ProcessTypeId
import pico.erp.shared.ComponentDefinitionServiceLoaderTestComponentSiblingsSupplier
import pico.erp.shared.TestParentApplication
import spock.lang.Specification

@SpringBootTest(classes = [ProcessApplication, TestConfig])
@SpringBootTestComponent(parent = TestParentApplication, siblingsSupplier = ComponentDefinitionServiceLoaderTestComponentSiblingsSupplier.class)
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

  def itemId = ItemId.from("item-1")

  def setup() {
    processService.create(
      new ProcessRequests.CreateRequest(
        id: processId,
        typeId: processTypeId,
        lossRate: 0.01,
        adjustCost: 0,
        difficulty: ProcessDifficultyKind.NORMAL,
        description: "좋은 보통 작업",
        itemId: itemId
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

  def "완료 - 완료"() {
    when:
    preparationService.commit(
      new ProcessPreparationRequests.CommitRequest(
        id: preprocessId
      )
    )
    preparationService.complete(
      new ProcessPreparationRequests.CompleteRequest(
        id: preprocessId
      )
    )

    def preprocess = preparationService.get(preprocessId)

    then:
    preprocess.status == ProcessPreparationStatusKind.COMPLETED
  }

  def "취소 - 취소"() {
    when:
    preparationService.commit(
      new ProcessPreparationRequests.CommitRequest(
        id: preprocessId
      )
    )
    preparationService.cancel(
      new ProcessPreparationRequests.CancelRequest(
        id: preprocessId
      )
    )

    def preprocess = preparationService.get(preprocessId)

    then:
    preprocess.status == ProcessPreparationStatusKind.CANCELED
  }

}
