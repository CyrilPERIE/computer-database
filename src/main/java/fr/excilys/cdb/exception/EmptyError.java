package fr.excilys.cdb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmptyError extends Exception {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(EmptyError.class);
	LoggerInstance loggerInstance = LoggerInstance.getLoggerInstance();

	public EmptyError() {
		super();
	}

	public EmptyError(String msg) {
		super(msg);
	}
	
	public void emptyComputerName() {
		logger.info(LoggerInstance.Messages.emptyComputerNameField.getMessage());
	}
}