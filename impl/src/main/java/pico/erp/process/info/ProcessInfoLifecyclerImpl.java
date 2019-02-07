package pico.erp.process.info;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pico.erp.process.info.type.ProcessInfoTypExceptions;
import pico.erp.process.info.type.ProcessInfoTypeId;
import pico.erp.process.info.type.ProcessInfoTypeRepository;
import pico.erp.shared.Public;

@Public
@Component
public class ProcessInfoLifecyclerImpl implements ProcessInfoLifecycler {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Lazy
  @Autowired
  private ProcessInfoTypeRepository processInfoTypeRepository;

  {
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  @Override
  public ProcessInfo initialize(ProcessInfoTypeId typeId) {
    return processInfoTypeRepository.findBy(typeId)
      .orElseThrow(ProcessInfoTypExceptions.NotFoundException::new)
      .create();
  }

  @SneakyThrows
  @Override
  public ProcessInfo parse(ProcessInfoTypeId typeId, String text) {
    val type = processInfoTypeRepository.findBy(typeId)
      .orElseThrow(ProcessInfoTypExceptions.NotFoundException::new);

    return (ProcessInfo) objectMapper.readValue(text, type.getType());
  }

  @SneakyThrows
  @Override
  public String stringify(ProcessInfoTypeId typeId, ProcessInfo info) {
    if (info == null) {
      return null;
    }
    return objectMapper.writeValueAsString(info);
  }
}
