package pico.erp.process.data;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessInfoTypeData implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  ProcessInfoTypeId id;

  String name;

  String description;

}
