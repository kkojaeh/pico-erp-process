package pico.erp.process.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.process.type.data.ProcessTypeId;
import pico.erp.shared.event.Event;

public interface ProcessTypeEvents {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CostChangedEvent implements Event {

    public final static String CHANNEL = "event.process-type.cost-changed";

    private ProcessTypeId processTypeId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CreatedEvent implements Event {

    public final static String CHANNEL = "event.process-type.created";

    private ProcessTypeId processTypeId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class DeletedEvent implements Event {

    public final static String CHANNEL = "event.process-type.deleted";

    private ProcessTypeId processTypeId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class UpdatedEvent implements Event {

    public final static String CHANNEL = "event.process-type.updated";

    private ProcessTypeId processTypeId;

    public String channel() {
      return CHANNEL;
    }

  }
}
