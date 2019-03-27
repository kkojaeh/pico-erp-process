package pico.erp.process.preparation;

import java.util.List;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.Give;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.process.ProcessId;
import pico.erp.process.ProcessService;
import pico.erp.process.preparation.ProcessPreparationRequests.CancelRequest;
import pico.erp.process.preparation.ProcessPreparationRequests.CommitRequest;
import pico.erp.process.preparation.ProcessPreparationRequests.CompleteRequest;
import pico.erp.process.preparation.ProcessPreparationRequests.GenerateRequest;
import pico.erp.process.type.ProcessTypeService;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Give
@Transactional
@Validated
public class ProcessPreparationServiceLogic implements ProcessPreparationService {

  @Autowired
  private ProcessPreparationRepository processPreparationRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProcessPreparationMapper mapper;

  @Lazy
  @Autowired
  private ProcessService processService;

  @Lazy
  @Autowired
  private ProcessTypeService processTypeService;


  @Override
  public ProcessPreparationData create(ProcessPreparationRequests.CreateRequest request) {
    if (processPreparationRepository.exists(request.getId())) {
      throw new ProcessPreparationExceptions.AlreadyExistsException();
    }
    val preprocess = new ProcessPreparation();
    val response = preprocess.apply(mapper.map(request));
    val created = processPreparationRepository.create(preprocess);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(ProcessPreparationRequests.DeleteRequest request) {
    val preprocessType = processPreparationRepository.findBy(request.getId())
      .orElseThrow(ProcessPreparationExceptions.NotFoundException::new);
    val response = preprocessType.apply(mapper.map(request));
    processPreparationRepository.deleteBy(preprocessType.getId());
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public boolean exists(ProcessPreparationId id) {
    return processPreparationRepository.exists(id);
  }

  @Override
  public void generate(GenerateRequest request) {
    val process = processService.get(request.getProcessId());
    val processType = processTypeService.get(process.getTypeId());

    processType.getPreparationTypes().forEach(preprocessType -> {
      create(
        ProcessPreparationRequests.CreateRequest.builder()
          .id(ProcessPreparationId.generate())
          .processId(process.getId())
          .typeId(preprocessType.getId())
          .chargeCost(preprocessType.getBaseCost())
          .build()
      );
    });
  }

  @Override
  public ProcessPreparationData get(ProcessPreparationId id) {
    return processPreparationRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(ProcessPreparationExceptions.NotFoundException::new);
  }

  @Override
  public List<ProcessPreparationData> getAll(ProcessId processId) {
    return processPreparationRepository.findAllBy(processId)
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public void update(ProcessPreparationRequests.UpdateRequest request) {
    val preprocessType = processPreparationRepository.findBy(request.getId())
      .orElseThrow(ProcessPreparationExceptions.NotFoundException::new);
    val response = preprocessType.apply(mapper.map(request));
    processPreparationRepository.update(preprocessType);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void cancel(CancelRequest request) {
    val preprocessType = processPreparationRepository.findBy(request.getId())
      .orElseThrow(ProcessPreparationExceptions.NotFoundException::new);
    val response = preprocessType.apply(mapper.map(request));
    processPreparationRepository.update(preprocessType);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void commit(CommitRequest request) {
    val preprocessType = processPreparationRepository.findBy(request.getId())
      .orElseThrow(ProcessPreparationExceptions.NotFoundException::new);
    val response = preprocessType.apply(mapper.map(request));
    processPreparationRepository.update(preprocessType);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public void complete(CompleteRequest request) {
    val preprocessType = processPreparationRepository.findBy(request.getId())
      .orElseThrow(ProcessPreparationExceptions.NotFoundException::new);
    val response = preprocessType.apply(mapper.map(request));
    processPreparationRepository.update(preprocessType);
    eventPublisher.publishEvents(response.getEvents());
  }

}
