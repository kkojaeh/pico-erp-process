package pico.erp.process.info.type;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface ProcessInfoTypExceptions {

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "process.info.type.not.found.exception")
  class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

  }
}
