package pico.erp.process

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.FileCopyUtils
import pico.erp.process.info.type.ProcessInfoTypeId
import pico.erp.process.type.ProcessTypeId
import pico.erp.process.type.ProcessTypeService
import pico.erp.process.type.ProcessTypeTransporter
import pico.erp.shared.IntegrationConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class ProcessTypeTransporterSpec extends Specification {

  @Autowired
  ProcessTypeTransporter processTypeTransporter

  @Autowired
  ProcessTypeService processTypeService

  @Value("classpath:process-type-import-data.xlsx")
  Resource importData

  def "export"() {
    when:
    def inputStream = processTypeTransporter.exportExcel(
      new ProcessTypeTransporter.ExportRequest(
        empty: false
      )
    )

    then:
    FileCopyUtils.copy(inputStream, new FileOutputStream("/Users/kojaehun/test.xlsx"))
    inputStream.contentLength > 0
  }

  def "import - 덮어쓴다"() {
    when:
    processTypeTransporter.importExcel(
      new ProcessTypeTransporter.ImportRequest(
        inputStream: importData.getInputStream(),
        overwrite: true
      )
    )
    def previous = processTypeService.get(ProcessTypeId.from("BB"))
    def created = processTypeService.get(ProcessTypeId.from("NEW"))
    then:
    previous.name == "접착(양면)2"
    previous.baseUnitCost == 80
    created.name == "테스트 공정 유형"
    created.baseUnitCost == 200
    created.infoTypeId == ProcessInfoTypeId.from("bonding")
    created.lossRate == 0.02
  }

  def "import - 덮어쓰지 않는다"() {
    when:
    processTypeTransporter.importExcel(
      new ProcessTypeTransporter.ImportRequest(
        inputStream: importData.getInputStream(),
        overwrite: false
      )
    )
    def previous = processTypeService.get(ProcessTypeId.from("BB"))
    then:
    previous.name != "접착(양면)2"
    previous.baseUnitCost != 80
  }
}
