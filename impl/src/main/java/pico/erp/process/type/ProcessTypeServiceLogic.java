package pico.erp.process.type;

import kkojaeh.spring.boot.component.Give;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.process.type.ProcessTypeRequests.AddPreprocessTypeRequest;
import pico.erp.process.type.ProcessTypeRequests.CreateRequest;
import pico.erp.process.type.ProcessTypeRequests.DeleteRequest;
import pico.erp.process.type.ProcessTypeRequests.RemovePreprocessTypeRequest;
import pico.erp.process.type.ProcessTypeRequests.UpdateRequest;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Give
@Transactional
@Validated
public class ProcessTypeServiceLogic implements ProcessTypeService {

  @Autowired
  private ProcessTypeRepository processTypeRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProcessTypeMapper mapper;

  @Override
  public void add(AddPreprocessTypeRequest request) {
    val processType = processTypeRepository.findBy(request.getId())
      .orElseThrow(ProcessTypeExceptions.NotFoundException::new);
    val response = processType.apply(mapper.map(request));
    processTypeRepository.update(processType);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public ProcessTypeData create(CreateRequest request) {
    if (processTypeRepository.exists(request.getId())) {
      throw new ProcessTypeExceptions.AlreadyExistsException();
    }
    val processType = new ProcessType();
    val response = processType.apply(mapper.map(request));
    val created = processTypeRepository.create(processType);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(DeleteRequest request) {
    val processType = processTypeRepository.findBy(request.getId())
      .orElseThrow(ProcessTypeExceptions.NotFoundException::new);
    val response = processType.apply(mapper.map(request));
    processTypeRepository.deleteBy(processType.getId());
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public boolean exists(ProcessTypeId id) {
    return processTypeRepository.exists(id);
  }

  @Override
  public ProcessTypeData get(ProcessTypeId id) {
    return processTypeRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(ProcessTypeExceptions.NotFoundException::new);
  }

  @Override
  public void remove(RemovePreprocessTypeRequest request) {
    val processType = processTypeRepository.findBy(request.getId())
      .orElseThrow(ProcessTypeExceptions.NotFoundException::new);
    val response = processType.apply(mapper.map(request));
    processTypeRepository.update(processType);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void update(UpdateRequest request) {
    val processType = processTypeRepository.findBy(request.getId())
      .orElseThrow(ProcessTypeExceptions.NotFoundException::new);
    val response = processType.apply(mapper.map(request));
    processTypeRepository.update(processType);
    eventPublisher.publishEvents(response.getEvents());
  }
}
