package fr.excilys.cdb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomSQLException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(EmptyError.class);
	LoggerInstance loggerInstance = LoggerInstance.getLoggerInstance();

	public CustomSQLException() {

	}

	public CustomSQLException(String msg) {
		super(msg);
	}
	
	public String noContentDectect() {
		logger.info(LoggerInstance.Messages.wrongDateFormat.getMessage());
		return LoggerInstance.Messages.wrongDateFormat.getMessage();
	
	}

	public String connectionLostDetected() {
		logger.info(LoggerInstance.Messages.connectionFailed.getMessage());
		return LoggerInstance.Messages.connectionFailed.getMessage();
	}

	public String noDataDetected() {
		logger.info(LoggerInstance.Messages.noCompanyFound.getMessage());
		return LoggerInstance.Messages.noCompanyFound.getMessage();
	}
}
