package pico.erp.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.process.type.ProcessTypeEvents;
import pico.erp.process.type.ProcessTypeEvents.CostChangedEvent;

@SuppressWarnings("unused")
@Component
@Transactional
public class ProcessEventListener {

  private static final String LISTENER_NAME = "listener.process-event-listener";

  @Lazy
  @Autowired
  private ProcessServiceLogic processService;

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "."
    + CostChangedEvent.CHANNEL)
  public void onProcessTypeCostChanged(ProcessTypeEvents.CostChangedEvent event) {
    processService.recalculateCostByType(
      ProcessServiceLogic.RecalculateCostByTypeRequest.builder()
        .processTypeId(event.getProcessTypeId())
        .build()
    );
  }

}
