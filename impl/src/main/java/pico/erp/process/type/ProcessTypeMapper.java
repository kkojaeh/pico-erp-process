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
import pico.erp.process.cost.ProcessCostRates;
import pico.erp.process.cost.ProcessCostRatesData;
import pico.erp.process.difficulty.ProcessDifficulty;
import pico.erp.process.difficulty.ProcessDifficultyData;
import pico.erp.process.difficulty.ProcessDifficultyEmbeddable;
import pico.erp.process.difficulty.ProcessDifficultyMapper;
import pico.erp.process.info.type.ProcessInfoTypeMapper;
import pico.erp.process.preparation.type.ProcessPreparationType;
import pico.erp.process.preparation.type.ProcessPreparationTypeId;
import pico.erp.process.preparation.type.ProcessPreparationTypeMapper;

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
  private ProcessPreparationTypeMapper processPreparationTypeMapper;

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
      .preparationTypes(
        entity.getPreparationTypes().stream()
          .map(processPreparationTypeMapper::map)
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

  protected ProcessPreparationTypeId map(ProcessPreparationType processPreparationType) {
    return Optional.ofNullable(processPreparationType)
      .map(ProcessPreparationType::getId)
      .orElse(null);
  }

  public abstract void pass(ProcessTypeEntity from, @MappingTarget ProcessTypeEntity to);

  @Mappings({
  })
  public abstract ProcessTypeMessages.CreateRequest map(ProcessTypeRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "preparationType", source = "preparationTypeId")
  })
  public abstract ProcessTypeMessages.AddPreprocessTypeRequest map(
    ProcessTypeRequests.AddPreprocessTypeRequest request);

  @Mappings({
    @Mapping(target = "preparationType", source = "preparationTypeId")
  })
  public abstract ProcessTypeMessages.RemovePreprocessTypeRequest map(
    ProcessTypeRequests.RemovePreprocessTypeRequest request);

  @Mappings({
  })
  public abstract ProcessTypeMessages.UpdateRequest map(ProcessTypeRequests.UpdateRequest request);

  @Mappings({
  })
  public abstract ProcessTypeData map(ProcessType processType);

  public abstract ProcessTypeMessages.DeleteRequest map(ProcessTypeRequests.DeleteRequest request);

  protected ProcessCostRates map(ProcessCostRatesData data) {
    return processCostMapper.map(data);
  }

  protected ProcessDifficulty map(ProcessDifficultyData data) {
    return processDifficultyMapper.map(data);
  }

  protected ProcessPreparationType map(ProcessPreparationTypeId typeId) {
    return processPreparationTypeMapper.map(typeId);
  }

}
