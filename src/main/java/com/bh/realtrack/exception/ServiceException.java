package com.bh.realtrack.exception;


import com.bh.realtrack.enumerators.ErrorCode;
import com.bh.realtrack.model.ErrorCollection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import java.io.Serializable;

/**
 * @author Ganesh Mali
 */
public class ServiceException extends WebApplicationException implements Serializable {

  protected static final Logger logger = LoggerFactory.getLogger(ServiceException.class);

  private static final long serialVersionUID = 258097657200775182L;

  private ErrorCode errorCode;
  private ErrorCollection errorCollection;

  public ServiceException(ErrorCode errorCode) {
    this(errorCode, null);
  }

  public ServiceException(ErrorCollection errorCollection) {
    this(ErrorCode.INTERNAL, errorCollection);
  }

  public ServiceException(ErrorCode errorCode, ErrorCollection errorCollection) {
    super(errorCode.getResponseStatus());
    this.errorCode = errorCode;
    this.errorCollection = errorCollection;
  }

  /**
   * @return the errorCode
   */
  public ErrorCode getErrorCode() {
    return errorCode;
  }

  /**
   * @param errorCode the errorCode to set
   */
  public void setErrorCode(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * @return the errorCollection
   */
  public ErrorCollection getErrorCollection() {
    return errorCollection;
  }

  /**
   * @param errorCollection the errorCollection to set
   */
  public void setErrorCollection(ErrorCollection errorCollection) {
    this.errorCollection = errorCollection;
  }
}
