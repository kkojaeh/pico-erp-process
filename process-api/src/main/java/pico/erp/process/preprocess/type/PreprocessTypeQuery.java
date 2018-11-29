package pico.erp.process.preprocess.type;

import java.util.List;
import javax.validation.constraints.NotNull;
import pico.erp.shared.data.LabeledValuable;

public interface PreprocessTypeQuery {

  List<? extends LabeledValuable> asLabels(@NotNull String keyword, long limit);

}
