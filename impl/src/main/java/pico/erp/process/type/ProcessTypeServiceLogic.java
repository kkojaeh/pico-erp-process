package pico.erp.process.type;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.audit.AuditService;
import pico.erp.process.type.ProcessTypeRequests.AddPreprocessTypeRequest;
import pico.erp.process.type.ProcessTypeRequests.CreateRequest;
import pico.erp.process.type.ProcessTypeRequests.DeleteRequest;
import pico.erp.process.type.ProcessTypeRequests.RemovePreprocessTypeRequest;
import pico.erp.process.type.ProcessTypeRequests.UpdateRequest;
import pico.erp.shared.Public;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class ProcessTypeServiceLogic implements ProcessTypeService {

  @Autowired
  private ProcessTypeRepository processTypeRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProcessTypeMapper mapper;

  @Lazy
  @Autowired
  private AuditService auditService;

  @Override
  public void add(AddPreprocessTypeRequest request) {
    val processType = processTypeRepository.findBy(request.getId())
      .orElseThrow(ProcessTypeExceptions.NotFoundException::new);
    val response = processType.apply(mapper.map(request));
    processTypeRepository.update(processType);
    auditService.commit(processType);
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
    auditService.commit(created);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(DeleteRequest request) {
    val processType = processTypeRepository.findBy(request.getId())
      .orElseThrow(ProcessTypeExceptions.NotFoundException::new);
    val response = processType.apply(mapper.map(request));
    processTypeRepository.deleteBy(processType.getId());
    auditService.delete(processType);
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
    auditService.commit(processType);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void update(UpdateRequest request) {
    val processType = processTypeRepository.findBy(request.getId())
      .orElseThrow(ProcessTypeExceptions.NotFoundException::new);
    val response = processType.apply(mapper.map(request));
    processTypeRepository.update(processType);
    auditService.commit(processType);
    eventPublisher.publishEvents(response.getEvents());
  }
}
