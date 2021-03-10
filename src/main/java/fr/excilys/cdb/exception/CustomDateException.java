package fr.excilys.cdb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomDateException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(EmptyError.class);

	public CustomDateException() {
		super();
	}

	public CustomDateException(String msg) {
		super(msg);
	}
	
	public String notAfter() {
		logger.info(LoggerInstance.Messages.notAfter.getMessage());
		return LoggerInstance.Messages.notAfter.getMessage();
	}
}
