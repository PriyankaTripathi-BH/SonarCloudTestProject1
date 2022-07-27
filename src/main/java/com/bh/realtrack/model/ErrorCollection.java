package com.bh.realtrack.model;



import java.util.HashMap;

import com.bh.realtrack.enumerators.ErrorCode;

/**
 * @author Ganesh Mali
 */
public class ErrorCollection extends HashMap<String,ErrorCode> {

	private static final long serialVersionUID = -7650243856874148333L;

	public ErrorCollection() {
	}
	
	public ErrorCollection(String key, ErrorCode errorCode) {
	    super();
	    put(key, errorCode);
	}
	
}
