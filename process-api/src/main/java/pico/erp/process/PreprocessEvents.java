package pico.erp.process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.process.data.PreprocessId;
import pico.erp.shared.event.Event;

public interface PreprocessEvents {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CreatedEvent implements Event {

    public final static String CHANNEL = "event.preprocess.created";

    private PreprocessId preprocessId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class DeletedEvent implements Event {

    public final static String CHANNEL = "event.preprocess.deleted";

    private PreprocessId preprocessId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class UpdatedEvent implements Event {

    public final static String CHANNEL = "event.preprocess.updated";

    private PreprocessId preprocessId;

    public String channel() {
      return CHANNEL;
    }

  }
}
