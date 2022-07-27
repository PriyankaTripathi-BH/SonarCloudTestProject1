
package com.bh.realtrack.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class OperationsExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(OperationsExceptionHandler.class);

	@ExceptionHandler(value = BeanCreationException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ExceptionResponseEntity badRequestException(BeanCreationException ex, WebRequest request) {

		 Throwable rootCause = ExceptionUtils.getRootCause(ex);
		    if (rootCause != null && "com.bh.realtrack.exception.UnknowAuthenticationException".equals(rootCause.getClass().getName()))
		    {       
		    	ExceptionResponseEntity response = new ExceptionResponseEntity();
		    	response.setErrorId("401");
		    	response.setError("Unauthorized");
		    	response.setMessage("Error in reading authentication data. User authentication failed");
		    	response.setPath(request.getDescription(false));

		    	return response;
		    }
		    else
		    {
		    	ExceptionResponseEntity response = new ExceptionResponseEntity();
		    	response.setErrorId("500");
		    	response.setError("Application start failed");
		    	response.setMessage(ex.getMessage());
		    	response.setPath(request.getDescription(false));

		    	return response; 
		    }

	}

}
