package pico.erp.process.type;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pico.erp.shared.data.LabeledValuable;

public interface ProcessTypeQuery {

  List<? extends LabeledValuable> asLabels(@NotNull String keyword, long limit);

  Page<ProcessTypeView> retrieve(@NotNull ProcessTypeView.Filter filter,
    @NotNull Pageable pageable);

}
