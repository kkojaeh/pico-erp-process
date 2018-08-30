package pico.erp.process.core;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.audit.AuditService;
import pico.erp.item.data.ItemId;
import pico.erp.process.ProcessExceptions;
import pico.erp.process.ProcessRequests.CompletePlanRequest;
import pico.erp.process.ProcessRequests.CreateRequest;
import pico.erp.process.ProcessRequests.DeleteRequest;
import pico.erp.process.ProcessRequests.UpdateRequest;
import pico.erp.process.ProcessService;
import pico.erp.process.data.ProcessData;
import pico.erp.process.data.ProcessId;
import pico.erp.process.domain.Process;
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
    if (processRepository.exists(request.getItemId())) {
      throw new ProcessExceptions.AlreadyExistsException();
    }
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
  public boolean exists(ItemId itemId) {
    return processRepository.exists(itemId);
  }

  @Override
  public boolean exists(ProcessId id) {
    return processRepository.exists(id);
  }

  @Override
  public ProcessData get(ItemId itemId) {
    return processRepository.findBy(itemId)
      .map(mapper::map)
      .orElseThrow(ProcessExceptions.NotFoundException::new);
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


}
