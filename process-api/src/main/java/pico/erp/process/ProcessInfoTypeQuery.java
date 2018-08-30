package pico.erp.process;

import java.util.List;
import javax.validation.constraints.NotNull;
import pico.erp.shared.data.LabeledValuable;

public interface ProcessInfoTypeQuery {

  List<? extends LabeledValuable> asLabels(@NotNull String keyword, long limit);

}
