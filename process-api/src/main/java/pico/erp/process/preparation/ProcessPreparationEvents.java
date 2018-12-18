package pico.erp.process.preparation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.process.preparation.type.ProcessPreparationTypeId;
import pico.erp.shared.event.Event;

public interface ProcessPreparationEvents {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CreatedEvent implements Event {

    public final static String CHANNEL = "event.process-preparation.created";

    private ProcessPreparationId processPreparationId;

    private ProcessPreparationTypeId processPreparationTypeId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class DeletedEvent implements Event {

    public final static String CHANNEL = "event.process-preparation.deleted";

    private ProcessPreparationId processPreparationId;

    private ProcessPreparationTypeId processPreparationTypeId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class UpdatedEvent implements Event {

    public final static String CHANNEL = "event.process-preparation.updated";

    private ProcessPreparationId processPreparationId;

    private ProcessPreparationTypeId processPreparationTypeId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CommittedEvent implements Event {

    public final static String CHANNEL = "event.process-preparation.committed";

    private ProcessPreparationId processPreparationId;

    private ProcessPreparationTypeId processPreparationTypeId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CompletedEvent implements Event {

    public final static String CHANNEL = "event.process-preparation.completed";

    private ProcessPreparationId processPreparationId;

    private ProcessPreparationTypeId processPreparationTypeId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CanceledEvent implements Event {

    public final static String CHANNEL = "event.process-preparation.canceled";

    private ProcessPreparationId processPreparationId;

    private ProcessPreparationTypeId processPreparationTypeId;

    public String channel() {
      return CHANNEL;
    }

  }
}
