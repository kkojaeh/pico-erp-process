package pico.erp.process.preprocess.type;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pico.erp.process.preprocess.type.data.PreprocessTypeView;
import pico.erp.shared.data.LabeledValuable;

public interface PreprocessTypeQuery {

  List<? extends LabeledValuable> asLabels(@NotNull String keyword, long limit);

  Page<PreprocessTypeView> retrieve(@NotNull PreprocessTypeView.Filter filter,
    @NotNull Pageable pageable);

}
