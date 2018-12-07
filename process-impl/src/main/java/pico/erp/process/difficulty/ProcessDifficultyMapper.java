package pico.erp.process.difficulty;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper
public abstract class ProcessDifficultyMapper {

  @Mappings({
  })
  public abstract ProcessDifficultyEmbeddable entity(ProcessDifficulty grade);

  public ProcessDifficulty jpa(ProcessDifficultyEmbeddable grade) {
    return ProcessDifficulty.builder()
      .costRate(grade.getCostRate())
      .description(grade.getDescription())
      .build();
  }

  public abstract ProcessDifficultyData map(ProcessDifficulty difficultyGrade);

  public ProcessDifficulty map(ProcessDifficultyData data) {
    return ProcessDifficulty.builder()
      .costRate(data.getCostRate())
      .description(data.getDescription())
      .build();
  }


}
