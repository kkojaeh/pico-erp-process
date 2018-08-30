package pico.erp.process.core;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.process.ProcessTypeEvents.CostChangedEvent;
import pico.erp.process.domain.ProcessMessages;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("unused")
@Component
@Transactional
public class ProcessTypeEventListener {

  private static final String LISTENER_NAME = "listener.process-type-event-listener";

  @Autowired
  private ProcessRepository processRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProcessMapper processMapper;

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "."
    + CostChangedEvent.CHANNEL)
  public void onProcessTypeCostChanged(CostChangedEvent event) {
    processRepository.findAllBy(event.getProcessTypeId())
      .forEach(related -> {
        val response = related.apply(new ProcessMessages.CalculateEstimatedCostRequest());
        processRepository.update(related);
        eventPublisher.publishEvents(response.getEvents());
      });
  }

}
