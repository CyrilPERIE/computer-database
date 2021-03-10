package fr.excilys.cdb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseError extends Exception {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(EmptyError.class);

	public ParseError() {
		super();
	}

	public ParseError(String msg) {
		super(msg);
	}
	
	public String parseErrorDetected() {
		logger.info(LoggerInstance.Messages.wrongDateFormat.getMessage());
		return LoggerInstance.Messages.wrongDateFormat.getMessage();
	}
}