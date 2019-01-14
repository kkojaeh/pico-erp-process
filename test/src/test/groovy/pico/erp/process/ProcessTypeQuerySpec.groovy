package pico.erp.process

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.process.type.ProcessTypeQuery
import pico.erp.process.type.ProcessTypeView
import pico.erp.shared.IntegrationConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
class ProcessTypeQuerySpec extends Specification {

  @Autowired
  ProcessTypeQuery processQuery


  def "조회 조건에 맞게 조회"() {
    expect:
    def page = processQuery.retrieve(condition, pageable)
    page.totalElements == totalElements


    where:
    condition                              | pageable               || totalElements
    new ProcessTypeView.Filter(name: "인쇄") | new PageRequest(0, 10) || 7
    new ProcessTypeView.Filter(name: "UV") | new PageRequest(0, 10) || 3
  }

}