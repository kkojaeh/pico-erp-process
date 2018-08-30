package pico.erp.process.core;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.process.PreprocessRequests;
import pico.erp.process.PreprocessService;
import pico.erp.process.ProcessEvents;
import pico.erp.process.ProcessExceptions;
import pico.erp.process.data.PreprocessId;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("unused")
@Component
@Transactional
public class PreprocessEventListener {

  private static final String LISTENER_NAME = "listener.preprocess-event-listener";

  @Autowired
  private ProcessRepository processRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProcessMapper processMapper;

  @Autowired
  private PreprocessService preprocessService;

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "." + ProcessEvents.PlannedEvent.CHANNEL)
  public void onProcessPlanned(ProcessEvents.PlannedEvent event) {
    val process = processRepository.findBy(event.getProcessId())
      .orElseThrow(ProcessExceptions.NotFoundException::new);
    process.getType().getPreprocessTypes().forEach(preprocessType -> {
      preprocessService.create(
        PreprocessRequests.CreateRequest.builder()
          .id(PreprocessId.generate())
          .processId(process.getId())
          .name(process.getName() + " / " + preprocessType.getName())
          .typeId(preprocessType.getId())
          .chargeCost(preprocessType.getBaseCost())
          .build()
      );
    });
  }

}
