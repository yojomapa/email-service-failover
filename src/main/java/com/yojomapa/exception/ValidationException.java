package com.yojomapa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by yojomapa on 27/03/17.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

  public ValidationException() {
  }

  public ValidationException(String message) {
    super(message);
  }
}
