package pico.erp.process.core;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.item.ItemService;
import pico.erp.item.data.ItemData;
import pico.erp.item.data.ItemId;
import pico.erp.process.PreprocessRequests;
import pico.erp.process.PreprocessTypeRequests;
import pico.erp.process.ProcessRequests;
import pico.erp.process.ProcessTypeExceptions.NotFoundException;
import pico.erp.process.ProcessTypeRequests;
import pico.erp.process.data.PreprocessData;
import pico.erp.process.data.PreprocessTypeData;
import pico.erp.process.data.PreprocessTypeId;
import pico.erp.process.data.ProcessCostData;
import pico.erp.process.data.ProcessCostRatesData;
import pico.erp.process.data.ProcessData;
import pico.erp.process.data.ProcessDifficultyGradeData;
import pico.erp.process.data.ProcessId;
import pico.erp.process.data.ProcessInfoType;
import pico.erp.process.data.ProcessInfoTypeData;
import pico.erp.process.data.ProcessInfoTypeId;
import pico.erp.process.data.ProcessTypeData;
import pico.erp.process.data.ProcessTypeId;
import pico.erp.process.domain.Preprocess;
import pico.erp.process.domain.PreprocessMessages;
import pico.erp.process.domain.PreprocessType;
import pico.erp.process.domain.PreprocessTypeMessages;
import pico.erp.process.domain.Process;
import pico.erp.process.domain.ProcessCost;
import pico.erp.process.domain.ProcessCostRates;
import pico.erp.process.domain.ProcessDifficultyGrade;
import pico.erp.process.domain.ProcessMessages;
import pico.erp.process.domain.ProcessType;
import pico.erp.process.domain.ProcessTypeMessages;
import pico.erp.shared.event.EventPublisher;
import pico.erp.user.UserService;
import pico.erp.user.data.UserData;
import pico.erp.user.data.UserId;

@Mapper
public abstract class ProcessMapper {

  @Autowired
  private ProcessInfoTypeRepository processInfoTypeRepository;

  @Autowired
  private ProcessTypeRepository processTypeRepository;

  @Autowired
  private PreprocessTypeRepository preprocessTypeRepository;

  @Autowired
  private ProcessRepository processRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Lazy
  @Autowired
  private ItemService itemService;

  @Lazy
  @Autowired
  private UserService userService;

  protected ProcessInfoType map(ProcessInfoTypeId infoTypeId) {
    return Optional.ofNullable(infoTypeId)
      .map(id -> processInfoTypeRepository.findBy(id)
        .orElseThrow(pico.erp.process.ProcessInfoTypExceptions.NotFoundException::new)
      )
      .orElse(null);
  }

  protected ProcessType map(ProcessTypeId typeId) {
    return Optional.ofNullable(typeId)
      .map(id -> processTypeRepository.findBy(id)
        .orElseThrow(NotFoundException::new))
      .orElse(null);
  }

  protected PreprocessType map(PreprocessTypeId typeId) {
    return Optional.ofNullable(typeId)
      .map(id -> preprocessTypeRepository.findBy(id)
        .orElseThrow(NotFoundException::new))
      .orElse(null);
  }

  protected Process map(ProcessId processId) {
    return Optional.ofNullable(processId)
      .map(id -> processRepository.findBy(id)
        .orElseThrow(NotFoundException::new))
      .orElse(null);
  }

  protected ItemData map(ItemId itemId) {
    return Optional.ofNullable(itemId)
      .map(itemService::get)
      .orElse(null);
  }

  protected UserData map(UserId userId) {
    return Optional.ofNullable(userId)
      .map(userService::get)
      .orElse(null);
  }

  @Mappings({
    @Mapping(target = "infoType", source = "infoTypeId")
  })
  public abstract ProcessTypeMessages.CreateRequest map(ProcessTypeRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "preprocessType", source = "preprocessTypeId")
  })
  public abstract ProcessTypeMessages.AddPreprocessTypeRequest map(
    ProcessTypeRequests.AddPreprocessTypeRequest request);

  @Mappings({
    @Mapping(target = "preprocessType", source = "preprocessTypeId")
  })
  public abstract ProcessTypeMessages.RemovePreprocessTypeRequest map(
    ProcessTypeRequests.RemovePreprocessTypeRequest request);

  @Mappings({
    @Mapping(target = "process", source = "processId"),
    @Mapping(target = "type", source = "typeId"),
    @Mapping(target = "managerData", source = "managerId")
  })
  public abstract PreprocessMessages.CreateRequest map(PreprocessRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "managerData", source = "managerId")
  })
  public abstract PreprocessMessages.UpdateRequest map(PreprocessRequests.UpdateRequest request);

  public abstract PreprocessMessages.DeleteRequest map(PreprocessRequests.DeleteRequest request);

  @Mappings({
    @Mapping(target = "infoType", source = "infoTypeId")
  })
  public abstract PreprocessTypeMessages.CreateRequest map(
    PreprocessTypeRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "itemData", source = "itemId"),
    @Mapping(target = "type", source = "typeId"),
    @Mapping(target = "managerData", source = "managerId")
  })
  public abstract ProcessMessages.CreateRequest map(ProcessRequests.CreateRequest request);

  @Mappings({
    @Mapping(target = "infoType", source = "infoTypeId")
  })
  public abstract ProcessTypeMessages.UpdateRequest map(ProcessTypeRequests.UpdateRequest request);

  @Mappings({
    @Mapping(target = "infoType", source = "infoTypeId")
  })
  public abstract PreprocessTypeMessages.UpdateRequest map(
    PreprocessTypeRequests.UpdateRequest request);

  @Mappings({
    @Mapping(target = "type", source = "typeId"),
    @Mapping(target = "managerData", source = "managerId")
  })
  public abstract ProcessMessages.UpdateRequest map(ProcessRequests.UpdateRequest request);

  public abstract ProcessMessages.CompletePlanRequest map(
    ProcessRequests.CompletePlanRequest request);

  public abstract ProcessTypeMessages.DeleteRequest map(ProcessTypeRequests.DeleteRequest request);

  public abstract PreprocessTypeMessages.DeleteRequest map(
    PreprocessTypeRequests.DeleteRequest request);

  public abstract ProcessMessages.DeleteRequest map(ProcessRequests.DeleteRequest request);

  @Mappings({
    @Mapping(target = "itemId", source = "itemData.id"),
    @Mapping(target = "typeId", source = "type.id"),
    @Mapping(target = "managerId", source = "managerData.id")
  })
  public abstract ProcessData map(Process process);

  @Mappings({
    @Mapping(target = "processId", source = "process.id"),
    @Mapping(target = "typeId", source = "type.id"),
    @Mapping(target = "managerId", source = "managerData.id")
  })
  public abstract PreprocessData map(Preprocess preprocess);

  @Mappings({
    @Mapping(target = "infoTypeId", source = "infoType.id")
  })
  public abstract ProcessTypeData map(ProcessType processType);

  @Mappings({
    @Mapping(target = "infoTypeId", source = "infoType.id")
  })
  public abstract PreprocessTypeData map(PreprocessType processType);

  public abstract ProcessInfoTypeData map(ProcessInfoType type);

  public abstract ProcessDifficultyGradeData map(ProcessDifficultyGrade difficultyGrade);

  public abstract ProcessCostRatesData map(ProcessCostRates data);

  public abstract ProcessCostData map(ProcessCost cost);

  public ProcessDifficultyGrade map(ProcessDifficultyGradeData data) {
    return ProcessDifficultyGrade.builder()
      .difficulty(data.getDifficulty())
      .costRate(data.getCostRate())
      .description(data.getDescription())
      .build();
  }

  public ProcessCostRates map(ProcessCostRatesData data) {
    return ProcessCostRates.builder()
      .directLabor(data.getDirectLabor())
      .indirectLabor(data.getIndirectLabor())
      .indirectMaterial(data.getIndirectMaterial())
      .indirectExpenses(data.getIndirectExpenses())
      .build();
  }

}
