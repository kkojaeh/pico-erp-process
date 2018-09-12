package pico.erp.process;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.item.ItemEvents.UpdatedEvent;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("unused")
@Component
@Transactional
public class ProcessEventListener {

  private static final String LISTENER_NAME = "listener.process-event-listener";

  @Autowired
  private ProcessRepository processRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProcessMapper processMapper;

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "." + UpdatedEvent.CHANNEL)
  public void onItemUpdated(UpdatedEvent event) {
    if (event.isNameChanged()) {
      processRepository.findBy(event.getItemId())
        .ifPresent(process -> {
          val response = process.apply(new ProcessMessages.CompletePlanRequest());
          processRepository.update(process);
          eventPublisher.publishEvents(response.getEvents());
        });
    }
  }

}
