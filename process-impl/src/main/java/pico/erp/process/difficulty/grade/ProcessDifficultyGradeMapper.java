package pico.erp.process.difficulty.grade;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class ProcessDifficultyGradeMapper {

  public ProcessDifficultyGrade domain(ProcessDifficultyGradeEmbeddable grade) {
    return ProcessDifficultyGrade.builder()
      .difficulty(grade.getDifficulty())
      .costRate(grade.getCostRate())
      .description(grade.getDescription())
      .build();
  }

  @Mappings({
    @Mapping(target = "ordinal", expression = "java(grade.getDifficulty().ordinal())")
  })
  public abstract ProcessDifficultyGradeEmbeddable entity(ProcessDifficultyGrade grade);

  public abstract ProcessDifficultyGradeData map(ProcessDifficultyGrade difficultyGrade);

  public ProcessDifficultyGrade map(ProcessDifficultyGradeData data) {
    return ProcessDifficultyGrade.builder()
      .difficulty(data.getDifficulty())
      .costRate(data.getCostRate())
      .description(data.getDescription())
      .build();
  }


}
