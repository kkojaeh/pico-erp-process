package pico.erp.process.info;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;

@Mapper
public class ProcessInfoMapper {

  protected final ObjectMapper objectMapper = new ObjectMapper();

  {
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  @SneakyThrows
  public ProcessInfo map(String variables, Class<ProcessInfo> type) {
    if (variables == null) {
      return null;
    }
    return objectMapper.readValue(variables, type);
  }

  @SneakyThrows
  public String map(ProcessInfo info) {
    if (info == null) {
      return null;
    }
    return objectMapper.writeValueAsString(info);
  }
}
