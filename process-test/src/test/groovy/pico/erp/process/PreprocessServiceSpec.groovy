package pico.erp.process

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.process.difficulty.ProcessDifficultyKind
import pico.erp.process.preprocess.PreprocessExceptions
import pico.erp.process.preprocess.PreprocessId
import pico.erp.process.preprocess.PreprocessRequests
import pico.erp.process.preprocess.PreprocessService
import pico.erp.process.preprocess.type.PreprocessTypeId
import pico.erp.process.preprocess.type.PreprocessTypeService
import pico.erp.process.type.ProcessTypeId
import pico.erp.shared.IntegrationConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
class PreprocessServiceSpec extends Specification {

  @Autowired
  PreprocessTypeService preprocessTypeService

  @Autowired
  PreprocessService preprocessService

  @Autowired
  ProcessService processService

  def preprocessTypeId = PreprocessTypeId.from("WP")

  def processId = ProcessId.from("process-1")

  def processTypeId = ProcessTypeId.from("PO")

  def preprocessId = PreprocessId.from("PP1")

  def unknownPreprocessId = PreprocessId.from("unknown")

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

    preprocessService.create(
      new PreprocessRequests.CreateRequest(
        id: preprocessId,
        processId: processId,
        typeId: preprocessTypeId
      )
    )
  }

  def "존재 - 아이디로 확인"() {
    when:
    def exists = preprocessService.exists(preprocessId)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = preprocessService.exists(unknownPreprocessId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def preprocess = preprocessService.get(preprocessId)

    then:
    preprocess.id == preprocessId
    preprocess.processId == processId
    preprocess.typeId == preprocessTypeId
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    preprocessService.get(unknownPreprocessId)

    then:
    thrown(PreprocessExceptions.NotFoundException)
  }

}
