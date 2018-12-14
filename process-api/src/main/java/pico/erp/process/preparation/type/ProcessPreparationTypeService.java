package pico.erp.process.preparation.type;

import java.util.List;
import javax.validation.constraints.NotNull;

public interface ProcessPreparationTypeService {

  boolean exists(@NotNull ProcessPreparationTypeId id);

  ProcessPreparationTypeData get(@NotNull ProcessPreparationTypeId id);

  List<ProcessPreparationTypeData> getAll();

}
