package pico.erp.process.preprocess.type;

import java.util.List;
import javax.validation.constraints.NotNull;

public interface PreprocessTypeService {

  PreprocessTypeData get(@NotNull PreprocessTypeId id);

  boolean exists(@NotNull PreprocessTypeId id);

  List<PreprocessTypeData> getAll();

}
