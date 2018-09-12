package pico.erp.process

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.config.process.info.PrintingProcessInfo
import pico.erp.process.data.*
import pico.erp.process.info.type.ClassBasedProcessInfoType
import pico.erp.process.type.ProcessTypeQuery
import pico.erp.process.type.ProcessTypeRequests
import pico.erp.process.type.ProcessTypeService
import pico.erp.process.type.data.ProcessTypeId
import pico.erp.process.type.data.ProcessTypeView
import pico.erp.shared.IntegrationConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
class ProcessTypeQuerySpec extends Specification {

  def setup() {
    def infoType = new ClassBasedProcessInfoType(PrintingProcessInfo)
    processTypeService.create(
      new ProcessTypeRequests.CreateRequest(id: ProcessTypeId.from("P1"), name: "인쇄 - UV", infoTypeId: infoType.id,
        baseUnitCost: 100,
        difficultyGrades: [
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.EASY, costRate: 0.9),
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.NORMAL, costRate: 1),
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.HARD, costRate: 1.1),
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.VERY_HARD, costRate: 1.2)
        ],
        costRates: new ProcessCostRatesData(directLabor: 0.4, indirectLabor: 0.1, indirectMaterial: 0.25, indirectExpenses: 0.25)
      )
    )
    processTypeService.create(
      new ProcessTypeRequests.CreateRequest(id: ProcessTypeId.from("P2"), name: "인쇄 - Offset", infoTypeId: infoType.id,
        baseUnitCost: 100,
        difficultyGrades: [
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.EASY, costRate: 0.9),
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.NORMAL, costRate: 1),
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.HARD, costRate: 1.1),
          new ProcessDifficultyGradeData(difficulty: ProcessDifficultyKind.VERY_HARD, costRate: 1.2)
        ], costRates: new ProcessCostRatesData(directLabor: 0.4, indirectLabor: 0.1, indirectMaterial: 0.25, indirectExpenses: 0.25)
      )
    )
  }

  @Autowired
  ProcessTypeQuery processQuery

  @Autowired
  ProcessTypeService processTypeService

  def "조회 조건에 맞게 조회"() {
    expect:
    def page = processQuery.retrieve(condition, pageable)
    page.totalElements == totalElements


    where:
    condition                              | pageable               || totalElements
    new ProcessTypeView.Filter(name: "인쇄") | new PageRequest(0, 10) || 7
    new ProcessTypeView.Filter(name: "UV") | new PageRequest(0, 10) || 4
  }

}
