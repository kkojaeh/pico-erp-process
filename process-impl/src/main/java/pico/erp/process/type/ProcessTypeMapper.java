package pico.erp.process.type;

import java.util.Optional;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.process.cost.ProcessCostMapper;
import pico.erp.process.difficulty.grade.ProcessDifficultyGrade;
import pico.erp.process.difficulty.grade.ProcessDifficultyGradeEmbeddable;
import pico.erp.process.difficulty.grade.ProcessDifficultyGradeMapper;
import pico.erp.process.info.type.ProcessInfoTypeMapper;
import pico.erp.process.preprocess.type.PreprocessType;
import pico.erp.process.preprocess.type.PreprocessTypeId;
import pico.erp.process.preprocess.type.PreprocessTypeMapper;

@Mapper
public abstract class ProcessTypeMapper {

  @Lazy
  @Autowired
  protected ProcessDifficultyGradeMapper processDifficultyGradeMapper;

  @Lazy
  @Autowired
  private ProcessInfoTypeMapper processInfoTypeMapper;

  @Lazy
  @Autowired
  private ProcessCostMapper processCostMapper;

  @Lazy
  @Autowired
  private PreprocessTypeMapper preprocessTypeMapper;

  @Autowired
  private ProcessTypeEntityRepository processTypeEntityRepository;

  public ProcessType jpa(ProcessTypeEntity entity) {
    return ProcessType.builder()
      .id(entity.getId())
      .name(entity.getName())
      .lossRate(entity.getLossRate())
      .baseUnitCost(entity.getBaseUnitCost())
      .infoTypeId(entity.getInfoTypeId())
      .difficultyGrades(
        entity.getDifficultyGrades().stream()
          .map(processDifficultyGradeMapper::domain)
          .collect(Collectors.toList())
      )
      .costRates(
        processCostMapper.domain(entity.getCostRates())
      )
      .preprocessTypes(
        entity.getPreprocessTypes().stream()
          .map(preprocessTypeMapper::map)
          .collect(Collectors.toList())
      )
      .build();
  }

  @Mappings({
    @Mapping(target = "infoTypeId", source = "infoTypeId"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract ProcessTypeEntity jpa(ProcessType processType);

  protected ProcessDifficultyGradeEmbeddable jpa(ProcessDifficultyGrade grade) {
    return processDifficultyGradeMapper.entity(grade);
  }

  protected PreprocessTypeId map(PreprocessType preprocessType) {
    return Optional.ofNullable(preprocessType)
      .map(PreprocessType::getId)
      .orElse(null);
  }

  public abstract void pass(ProcessTypeEntity from, @MappingTarget ProcessTypeEntity to);

}
