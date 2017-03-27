package com.yojomapa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yojomapa on 27/03/17.
 */
@ControllerAdvice
public class DefaultExceptionHandler {

  @ExceptionHandler({ValidationException.class})
  @ResponseBody
  public ResponseEntity<String> handleException(ValidationException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

}
