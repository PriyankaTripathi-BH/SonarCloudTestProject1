package com.bh.realtrack.service;


import com.bh.realtrack.exception.UnknowAuthenticationException;
import javax.servlet.http.HttpServletRequest;

import com.bh.realtrack.model.CallerContext;

/**
 * @author Ganesh Mali
 */
public interface ICallerContextManager {

    //CallerContext getCallerContext(HttpServletRequest request);
	CallerContext getCallerContext(HttpServletRequest request) throws UnknowAuthenticationException;

}
