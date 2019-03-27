package pico.erp.process;

import java.util.List;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.Give;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.item.ItemId;
import pico.erp.process.ProcessRequests.ChangeOrderRequest;
import pico.erp.process.ProcessRequests.CompletePlanRequest;
import pico.erp.process.ProcessRequests.CreateRequest;
import pico.erp.process.ProcessRequests.DeleteRequest;
import pico.erp.process.ProcessRequests.RecalculateCostByTypeRequest;
import pico.erp.process.ProcessRequests.UpdateRequest;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Give
@Transactional
@Validated
public class ProcessServiceLogic implements ProcessService {

  @Autowired
  private ProcessRepository processRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProcessMapper mapper;

  @Override
  public void completePlan(CompletePlanRequest request) {
    val process = processRepository.findBy(request.getId())
      .orElseThrow(ProcessExceptions.NotFoundException::new);
    val response = process.apply(mapper.map(request));
    processRepository.update(process);
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
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(DeleteRequest request) {
    val process = processRepository.findBy(request.getId())
      .orElseThrow(ProcessExceptions.NotFoundException::new);
    val response = process.apply(mapper.map(request));
    processRepository.update(process);
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
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void changeOrder(ChangeOrderRequest request) {
    val process = processRepository.findBy(request.getId())
      .orElseThrow(ProcessExceptions.NotFoundException::new);
    val response = process.apply(mapper.map(request));
    processRepository.update(process);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public List<ProcessData> getAll(ItemId itemId) {
    return processRepository.findAllBy(itemId)
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public void recalculateCostByType(RecalculateCostByTypeRequest request) {
    processRepository.findAllBy(request.getProcessTypeId())
      .forEach(process -> {
        val response = process.apply(new ProcessMessages.CalculateEstimatedCost.Request());
        processRepository.update(process);
        eventPublisher.publishEvents(response.getEvents());
      });
  }


}
