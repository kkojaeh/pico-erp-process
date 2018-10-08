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
import pico.erp.process.preprocess.type.PreprocessTypeEntity;
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

  public ProcessType domain(ProcessTypeEntity entity) {
    return ProcessType.builder()
      .id(entity.getId())
      .name(entity.getName())
      .baseUnitCost(entity.getBaseUnitCost())
      .infoType(processInfoTypeMapper.map(entity.getInfoTypeId()))
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
          .map(preprocessTypeMapper::domain)
          .collect(Collectors.toList())
      )
      .build();
  }

  @Mappings({
    @Mapping(target = "infoTypeId", source = "infoType.id"),
    @Mapping(target = "infoTypeName", source = "infoType.name"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract ProcessTypeEntity entity(ProcessType processType);

  protected PreprocessTypeEntity entity(PreprocessType preprocessType) {
    return preprocessTypeMapper.entity(preprocessType);
  }

  protected ProcessDifficultyGradeEmbeddable entity(ProcessDifficultyGrade grade) {
    return processDifficultyGradeMapper.entity(grade);
  }

  public ProcessTypeEntity entity(ProcessTypeId typeId) {
    return Optional.ofNullable(typeId)
      .map(processTypeEntityRepository::findOne)
      .orElse(null);
  }

  public abstract void pass(ProcessTypeEntity from, @MappingTarget ProcessTypeEntity to);

}
