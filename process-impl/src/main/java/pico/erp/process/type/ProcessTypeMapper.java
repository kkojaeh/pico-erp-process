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
import pico.erp.process.difficulty.ProcessDifficulty;
import pico.erp.process.difficulty.ProcessDifficultyEmbeddable;
import pico.erp.process.difficulty.ProcessDifficultyMapper;
import pico.erp.process.info.type.ProcessInfoTypeMapper;
import pico.erp.process.preprocess.type.PreprocessType;
import pico.erp.process.preprocess.type.PreprocessTypeId;
import pico.erp.process.preprocess.type.PreprocessTypeMapper;

@Mapper
public abstract class ProcessTypeMapper {

  @Lazy
  @Autowired
  protected ProcessDifficultyMapper processDifficultyMapper;

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
      .difficulties(
        entity.getDifficulties().entrySet().stream()
          .collect(
            Collectors.toMap(e -> e.getKey(), e -> processDifficultyMapper.jpa(e.getValue())))
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

  protected ProcessDifficultyEmbeddable jpa(ProcessDifficulty grade) {
    return processDifficultyMapper.entity(grade);
  }

  protected PreprocessTypeId map(PreprocessType preprocessType) {
    return Optional.ofNullable(preprocessType)
      .map(PreprocessType::getId)
      .orElse(null);
  }

  public abstract void pass(ProcessTypeEntity from, @MappingTarget ProcessTypeEntity to);

}
