package pico.erp.process

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.config.process.info.PrintingProcessInfo
import pico.erp.item.ItemId
import pico.erp.process.difficulty.grade.ProcessDifficultyKind
import pico.erp.process.info.type.ClassBasedProcessInfoType
import pico.erp.process.preprocess.PreprocessExceptions
import pico.erp.process.preprocess.PreprocessId
import pico.erp.process.preprocess.PreprocessRequests
import pico.erp.process.preprocess.PreprocessService
import pico.erp.process.preprocess.type.PreprocessTypeId
import pico.erp.process.preprocess.type.PreprocessTypeRequests
import pico.erp.process.preprocess.type.PreprocessTypeService
import pico.erp.process.type.ProcessTypeId
import pico.erp.shared.IntegrationConfiguration
import pico.erp.user.UserId
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

  def setup() {
    def infoType = new ClassBasedProcessInfoType(PrintingProcessInfo)
    processService.create(
      new ProcessRequests.CreateRequest(
        id: ProcessId.from("process-1"),
        itemId: ItemId.from("item-1"),
        name: "품목 명과 공정명 합침",
        typeId: ProcessTypeId.from("printing-offset"),
        adjustCost: 0,
        difficulty: ProcessDifficultyKind.NORMAL,
        description: "좋은 보통 작업"
      )
    )
    preprocessTypeService.create(
      new PreprocessTypeRequests.CreateRequest(
        id: PreprocessTypeId.from("P1"),
        name: "목형",
        infoTypeId: infoType.id,
        baseCost: 100
      )
    )

    preprocessService.create(
      new PreprocessRequests.CreateRequest(
        id: PreprocessId.from("PP1"),
        processId: ProcessId.from("process-1"),
        name: "A - 목형",
        typeId: PreprocessTypeId.from("P1"),
        managerId: UserId.from("ysh")
      )
    )
  }

  def "아이디로 존재하는 사전 공정 확인"() {
    when:
    def exists = preprocessService.exists(PreprocessId.from("PP1"))

    then:
    exists == true
  }

  def "아이디로 존재하지 않는 사전 공정 확인"() {
    when:
    def exists = preprocessService.exists(PreprocessId.from("!PP1"))

    then:
    exists == false
  }

  def "아이디로 존재하는 사전 공정을 조회"() {
    when:
    def process = preprocessService.get(PreprocessId.from("PP1"))

    then:
    process.name == "A - 목형"
  }

  def "아이디로 존재하지 않는 사전 공정을 조회"() {
    when:
    preprocessService.get(PreprocessId.from("!PP1"))

    then:
    thrown(PreprocessExceptions.NotFoundException)
  }

}
