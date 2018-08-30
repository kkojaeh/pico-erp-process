package pico.erp.process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.process.data.ProcessId;
import pico.erp.shared.event.Event;

public interface ProcessEvents {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CreatedEvent implements Event {

    public final static String CHANNEL = "event.process.created";

    private ProcessId processId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class DeletedEvent implements Event {

    public final static String CHANNEL = "event.process.deleted";

    private ProcessId processId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class EstimatedCostChangedEvent implements Event {

    public final static String CHANNEL = "event.process.estimated-cost-changed";

    private ProcessId processId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class UpdatedEvent implements Event {

    public final static String CHANNEL = "event.process.updated";

    private ProcessId processId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class PlannedEvent implements Event {

    public final static String CHANNEL = "event.process.planned";

    private ProcessId processId;

    public String channel() {
      return CHANNEL;
    }

  }
}
