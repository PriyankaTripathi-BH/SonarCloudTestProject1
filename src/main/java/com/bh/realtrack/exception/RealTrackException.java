package com.bh.realtrack.exception;

public class RealTrackException extends Exception {

	private static final long serialVersionUID = 1L;

	public RealTrackException(String message) {
		super(message);
	}

	public RealTrackException(String message, Throwable cause) {
		super(message, cause);
	}

	public RealTrackException(Throwable cause) {
		super(cause);
	}

}
