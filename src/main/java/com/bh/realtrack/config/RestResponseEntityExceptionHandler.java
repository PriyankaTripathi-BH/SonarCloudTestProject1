package com.bh.realtrack.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bh.realtrack.exception.ExceptionResponseEntity;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Ganesh Mali
 */

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  protected static final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

  @ExceptionHandler(value = {ClientErrorException.class, ServerErrorException.class})
  protected ResponseEntity<Object> handleConflict(WebApplicationException ex, WebRequest request) {
    int statusCode = ex.getResponse().getStatus();
    String error = ex.getResponse().getStatusInfo().getReasonPhrase();
    ExceptionResponseEntity response = new ExceptionResponseEntity();
    response.setError(error);
    response.setMessage(ex.getLocalizedMessage());
    response.setPath(request.getDescription(false).substring(4));
    response.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
    response.setErrorId(UUID.randomUUID().toString().toLowerCase());
    logger.error("error-id:"+response.getErrorId(),ex);
    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.valueOf(statusCode), request);
  }

}
