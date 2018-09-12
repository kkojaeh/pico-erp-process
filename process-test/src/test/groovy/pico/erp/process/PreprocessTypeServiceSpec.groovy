package pico.erp.process

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.config.process.info.PrintingProcessInfo
import pico.erp.process.preprocess.type.data.PreprocessTypeId
import pico.erp.process.info.type.ClassBasedProcessInfoType
import pico.erp.process.preprocess.type.PreprocessTypeExceptions
import pico.erp.process.preprocess.type.PreprocessTypeRequests
import pico.erp.process.preprocess.type.PreprocessTypeService
import pico.erp.shared.IntegrationConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
class PreprocessTypeServiceSpec extends Specification {

  @Autowired
  PreprocessTypeService preprocessTypeService

  def setup() {
    def infoType = new ClassBasedProcessInfoType(PrintingProcessInfo)
    preprocessTypeService.create(
      new PreprocessTypeRequests.CreateRequest(
        id: PreprocessTypeId.from("P1"),
        name: "목형",
        infoTypeId: infoType.id,
        baseCost: 100
      )
    )
    preprocessTypeService.create(
      new PreprocessTypeRequests.CreateRequest(
        id: PreprocessTypeId.from("P2"),
        name: "디자인",
        infoTypeId: infoType.id,
        baseCost: 100
      )
    )
  }

  def "아이디로 존재하는 사전 공정 유형 확인"() {
    when:
    def exists = preprocessTypeService.exists(PreprocessTypeId.from("P1"))

    then:
    exists == true
  }

  def "아이디로 존재하지 않는 사전 공정 유형 확인"() {
    when:
    def exists = preprocessTypeService.exists(PreprocessTypeId.from("!P1"))

    then:
    exists == false
  }

  def "아이디로 존재하는 사전 공정 유형를 조회"() {
    when:
    def process = preprocessTypeService.get(PreprocessTypeId.from("P1"))

    then:
    process.id.value == "P1"
    process.name == "목형"
  }

  def "아이디로 존재하지 않는 사전 공정 유형를 조회"() {
    when:
    preprocessTypeService.get(PreprocessTypeId.from("!P1"))

    then:
    thrown(PreprocessTypeExceptions.NotFoundException)
  }

}
