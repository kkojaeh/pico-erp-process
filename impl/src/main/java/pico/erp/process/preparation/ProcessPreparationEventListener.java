package pico.erp.process.preparation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pico.erp.process.ProcessEvents;

@SuppressWarnings("unused")
@Component
public class ProcessPreparationEventListener {

  private static final String LISTENER_NAME = "listener.process-preparation-event-listener";

  @Lazy
  @Autowired
  private ProcessPreparationService preparationService;

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "." + ProcessEvents.PlannedEvent.CHANNEL)
  public void onProcessPlanned(ProcessEvents.PlannedEvent event) {
    preparationService.generate(
      ProcessPreparationRequests.GenerateRequest.builder()
        .processId(event.getProcessId())
        .build()
    );
  }

}
