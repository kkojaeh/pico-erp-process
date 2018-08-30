package pico.erp.process;

import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pico.erp.process.data.ProcessView;

public interface ProcessQuery {

  Page<ProcessView> retrieve(@NotNull ProcessView.Filter filter,
    @NotNull Pageable pageable);

}
