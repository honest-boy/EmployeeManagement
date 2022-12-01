package com.vinove.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.vinove.utils.CommonUtil;

public class HandleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2589170145937868850L;

	private static final Logger LOG = LoggerFactory.getLogger(HandleException.class);

	private final HttpStatus status;

	public HandleException(final HttpStatus status, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.status = status;
		LOG.error(message, cause);
	}

	public HandleException(final HttpStatus status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
		LOG.error(message, cause);
	}

	public HandleException(final HttpStatus status, String message) {
		super(message, null);
		this.status = status;
		LOG.error(message);
	}

	public HandleException(final HttpStatus status, Throwable cause) {
		this(status, cause.getMessage(), cause);
		LOG.error("Error: " + cause.getMessage(), cause);
	}

	public HttpStatus getCode() {
		return status;
	}

	@Override
	public String getMessage() {
		String message = super.getMessage();
		if (CommonUtil.isEmpty(message) && super.getCause() != null) {
			StringWriter sw = new StringWriter();
			super.getCause().printStackTrace(new PrintWriter(sw));
			message = sw.toString();
		}
		return message;
	}

}
