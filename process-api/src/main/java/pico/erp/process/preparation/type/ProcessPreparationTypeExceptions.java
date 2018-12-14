package pico.erp.process.preparation.type;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface ProcessPreparationTypeExceptions {

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "preprocess.type.not.found.exception")
  class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

  }
}
