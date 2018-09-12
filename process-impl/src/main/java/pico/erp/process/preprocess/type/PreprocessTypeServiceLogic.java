package pico.erp.process.preprocess.type;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.audit.AuditService;
import pico.erp.process.ProcessMapper;
import pico.erp.process.preprocess.type.PreprocessTypeRequests.CreateRequest;
import pico.erp.process.preprocess.type.PreprocessTypeRequests.DeleteRequest;
import pico.erp.process.preprocess.type.PreprocessTypeRequests.UpdateRequest;
import pico.erp.process.preprocess.type.data.PreprocessTypeData;
import pico.erp.process.preprocess.type.data.PreprocessTypeId;
import pico.erp.shared.Public;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class PreprocessTypeServiceLogic implements PreprocessTypeService {

  @Autowired
  private PreprocessTypeRepository preprocessTypeRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProcessMapper mapper;

  @Lazy
  @Autowired
  private AuditService auditService;

  @Override
  public PreprocessTypeData create(CreateRequest request) {
    if (preprocessTypeRepository.exists(request.getId())) {
      throw new PreprocessTypeExceptions.AlreadyExistsException();
    }
    val preprocessType = new PreprocessType();
    val response = preprocessType.apply(mapper.map(request));
    val created = preprocessTypeRepository.create(preprocessType);
    auditService.commit(created);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(DeleteRequest request) {
    val preprocessType = preprocessTypeRepository.findBy(request.getId())
      .orElseThrow(PreprocessTypeExceptions.NotFoundException::new);
    val response = preprocessType.apply(mapper.map(request));
    preprocessTypeRepository.deleteBy(preprocessType.getId());
    auditService.delete(preprocessType);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public boolean exists(PreprocessTypeId id) {
    return preprocessTypeRepository.exists(id);
  }

  @Override
  public PreprocessTypeData get(PreprocessTypeId id) {
    return preprocessTypeRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(PreprocessTypeExceptions.NotFoundException::new);
  }


  @Override
  public void update(UpdateRequest request) {
    val preprocessType = preprocessTypeRepository.findBy(request.getId())
      .orElseThrow(PreprocessTypeExceptions.NotFoundException::new);
    val response = preprocessType.apply(mapper.map(request));
    preprocessTypeRepository.update(preprocessType);
    auditService.commit(preprocessType);
    eventPublisher.publishEvents(response.getEvents());
  }
}
