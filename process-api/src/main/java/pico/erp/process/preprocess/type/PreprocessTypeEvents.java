package pico.erp.process.preprocess.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.shared.event.Event;

public interface PreprocessTypeEvents {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CostChangedEvent implements Event {

    public final static String CHANNEL = "event.preprocess-type.cost-changed";

    private PreprocessTypeId preprocessTypeId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CreatedEvent implements Event {

    public final static String CHANNEL = "event.preprocess-type.created";

    private PreprocessTypeId preprocessTypeId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class DeletedEvent implements Event {

    public final static String CHANNEL = "event.preprocess-type.deleted";

    private PreprocessTypeId preprocessTypeId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class UpdatedEvent implements Event {

    public final static String CHANNEL = "event.preprocess-type.updated";

    private PreprocessTypeId preprocessTypeId;

    public String channel() {
      return CHANNEL;
    }

  }
}