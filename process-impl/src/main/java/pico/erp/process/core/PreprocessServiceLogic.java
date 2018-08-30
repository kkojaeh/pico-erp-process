package pico.erp.process.core;

import java.util.List;
import java.util.stream.Collectors;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.audit.AuditService;
import pico.erp.process.PreprocessExceptions;
import pico.erp.process.PreprocessRequests;
import pico.erp.process.PreprocessService;
import pico.erp.process.data.PreprocessData;
import pico.erp.process.data.PreprocessId;
import pico.erp.process.data.ProcessId;
import pico.erp.process.domain.Preprocess;
import pico.erp.shared.Public;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class PreprocessServiceLogic implements PreprocessService {

  @Autowired
  private PreprocessRepository preprocessRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProcessMapper mapper;

  @Lazy
  @Autowired
  private AuditService auditService;

  @Override
  public PreprocessData create(PreprocessRequests.CreateRequest request) {
    if (preprocessRepository.exists(request.getId())) {
      throw new PreprocessExceptions.AlreadyExistsException();
    }
    val preprocess = new Preprocess();
    val response = preprocess.apply(mapper.map(request));
    val created = preprocessRepository.create(preprocess);
    auditService.commit(created);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(PreprocessRequests.DeleteRequest request) {
    val preprocessType = preprocessRepository.findBy(request.getId())
      .orElseThrow(PreprocessExceptions.NotFoundException::new);
    val response = preprocessType.apply(mapper.map(request));
    preprocessRepository.deleteBy(preprocessType.getId());
    auditService.delete(preprocessType);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public boolean exists(PreprocessId id) {
    return preprocessRepository.exists(id);
  }

  @Override
  public PreprocessData get(PreprocessId id) {
    return preprocessRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(PreprocessExceptions.NotFoundException::new);
  }

  @Override
  public List<PreprocessData> getAll(ProcessId processId) {
    return preprocessRepository.findAllBy(processId)
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public void update(PreprocessRequests.UpdateRequest request) {
    val preprocessType = preprocessRepository.findBy(request.getId())
      .orElseThrow(PreprocessExceptions.NotFoundException::new);
    val response = preprocessType.apply(mapper.map(request));
    preprocessRepository.update(preprocessType);
    auditService.commit(preprocessType);
    eventPublisher.publishEvents(response.getEvents());
  }

}
