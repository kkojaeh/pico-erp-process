package pico.erp.process.data;

import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "value")
@ToString
public class PreprocessId implements Serializable {

  private static final long serialVersionUID = 1L;

  @Getter(onMethod = @__({@JsonValue}))
  @Size(min = 2, max = 50)
  @NotNull
  private String value;

  public static PreprocessId from(@NonNull String value) {
    return new PreprocessId(value);
  }

  public static PreprocessId generate() {
    return from(UUID.randomUUID().toString());
  }

}
