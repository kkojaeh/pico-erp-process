package pico.erp.process.preprocess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pico.erp.process.ProcessEvents;

@SuppressWarnings("unused")
@Component
public class PreprocessEventListener {

  private static final String LISTENER_NAME = "listener.preprocess-event-listener";

  @Lazy
  @Autowired
  private PreprocessServiceLogic preprocessService;

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "." + ProcessEvents.PlannedEvent.CHANNEL)
  public void onProcessPlanned(ProcessEvents.PlannedEvent event) {
    preprocessService.generate(
      PreprocessServiceLogic.GenerateRequest.builder()
        .processId(event.getProcessId())
        .build()
    );
  }

}
