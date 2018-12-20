package pico.erp.process

import pico.erp.config.process.info.OutputProcessInfo
import pico.erp.config.process.info.PrintingProcessInfo
import pico.erp.process.info.type.ClassBasedProcessInfoType
import spock.lang.Specification

class ClassBasedProcessInfoTypeSpec extends Specification {

  def "이름 및 설명은 클래스의 어노테이션을 설정 내용을 사용한다 "() {
    when:
    def processInfoType = new ClassBasedProcessInfoType("printing", PrintingProcessInfo)

    then:
    processInfoType.name == "인쇄"
    processInfoType.description == "인쇄 공정에 필요한 정보"
  }

  def "생성한 ProcessInfo 는 해당 클래스의 타입이다"() {
    when:
    def processInfoType = new ClassBasedProcessInfoType("printing", OutputProcessInfo)
    println processInfoType.metadata

    then:
    processInfoType.create() instanceof OutputProcessInfo
  }

}
