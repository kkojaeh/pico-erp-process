package pico.erp.process;

import java.math.BigDecimal;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import pico.erp.item.ItemData;
import pico.erp.process.difficulty.ProcessDifficultyKind;
import pico.erp.process.info.ProcessInfoLifecycler;
import pico.erp.process.type.ProcessType;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.event.Event;

public interface ProcessMessages {

  interface Create {

    @Data
    class Request {

      @Valid
      @NotNull
      ProcessId id;

      @NotNull
      ProcessType type;

      @NotNull
      ProcessDifficultyKind difficulty;

      @Size(max = TypeDefinitions.CLOB_LENGTH)
      String description;

      @NotNull
      @Min(0)
      BigDecimal lossRate;

      @NotNull
      BigDecimal adjustCost;

      @Size(max = TypeDefinitions.DESCRIPTION_LENGTH)
      String adjustCostReason;

      @NotNull
      ProcessInfoLifecycler processInfoLifecycler;

      @NotNull
      ItemData item;

      @NotNull
      @Min(0)
      BigDecimal inputRate;

      @NotNull
      int order;

    }

    @Value
    class Response {

      Collection<Event> events;

    }
  }

  interface Update {

    @Data
    class Request {

      @NotNull
      ProcessType type;

      @NotNull
      ProcessDifficultyKind difficulty;

      @Size(max = TypeDefinitions.CLOB_LENGTH)
      String description;

      @NotNull
      @Min(0)
      BigDecimal lossRate;

      @NotNull
      BigDecimal adjustCost;

      @Size(max = TypeDefinitions.DESCRIPTION_LENGTH)
      String adjustCostReason;

      @NotNull
      ProcessInfoLifecycler processInfoLifecycler;

      @NotNull
      @Min(0)
      BigDecimal inputRate;

    }

    @Value
    class Response {

      Collection<Event> events;

    }
  }


  interface ChangeOrder {

    @Data
    class Request {

      int order;

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }

  interface Delete {

    @Data
    class Request {

    }

    @Value
    class Response {

      Collection<Event> events;

    }
  }

  interface CalculateEstimatedCost {

    @Data
    class Request {

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }

  interface CompletePlan {

    @Data
    class Request {

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }


}
