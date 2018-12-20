package pico.erp.process;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.audit.AuditService;
import pico.erp.process.ProcessRequests.CompletePlanRequest;
import pico.erp.process.ProcessRequests.CreateRequest;
import pico.erp.process.ProcessRequests.DeleteRequest;
import pico.erp.process.ProcessRequests.RecalculateCostByTypeRequest;
import pico.erp.process.ProcessRequests.UpdateRequest;
import pico.erp.shared.Public;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class ProcessServiceLogic implements ProcessService {

  @Autowired
  private ProcessRepository processRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProcessMapper mapper;

  @Lazy
  @Autowired
  private AuditService auditService;

  @Override
  public void completePlan(CompletePlanRequest request) {
    val process = processRepository.findBy(request.getId())
      .orElseThrow(ProcessExceptions.NotFoundException::new);
    val response = process.apply(mapper.map(request));
    processRepository.update(process);
    auditService.commit(process);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public ProcessData create(CreateRequest request) {
    val process = new Process();
    val response = process.apply(mapper.map(request));

    if (processRepository.exists(process.getId())) {
      throw new ProcessExceptions.AlreadyExistsException();
    }
    val created = processRepository.create(process);
    auditService.commit(created);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(DeleteRequest request) {
    val process = processRepository.findBy(request.getId())
      .orElseThrow(ProcessExceptions.NotFoundException::new);
    val response = process.apply(mapper.map(request));
    processRepository.update(process);
    auditService.delete(process);
    eventPublisher.publishEvents(response.getEvents());
  }


  @Override
  public boolean exists(ProcessId id) {
    return processRepository.exists(id);
  }


  @Override
  public ProcessData get(ProcessId id) {
    return processRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(ProcessExceptions.NotFoundException::new);
  }

  @Override
  public void update(UpdateRequest request) {
    val process = processRepository.findBy(request.getId())
      .orElseThrow(ProcessExceptions.NotFoundException::new);
    val response = process.apply(mapper.map(request));
    processRepository.update(process);
    auditService.commit(process);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void recalculateCostByType(RecalculateCostByTypeRequest request) {
    processRepository.findAllBy(request.getProcessTypeId())
      .forEach(process -> {
        val response = process.apply(new ProcessMessages.CalculateEstimatedCostRequest());
        processRepository.update(process);
        eventPublisher.publishEvents(response.getEvents());
      });
  }


}
