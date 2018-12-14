package pico.erp.process.preparation.type;

import java.util.List;
import javax.validation.constraints.NotNull;
import pico.erp.shared.data.LabeledValuable;

public interface ProcessPreparationTypeQuery {

  List<? extends LabeledValuable> asLabels(@NotNull String keyword, long limit);

}
