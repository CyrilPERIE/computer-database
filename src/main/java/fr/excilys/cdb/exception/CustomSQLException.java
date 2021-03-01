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
	
	public void noContentDectect() {
		logger.info(LoggerInstance.Messages.wrongDateFormat.getMessage());
		
		Exception e = new Exception();
		e.printStackTrace();
	
	}

	public void connectionLostDetected() {
		logger.info(LoggerInstance.Messages.connectionFailed.getMessage());		
	}

	public void noDataDetected() {
		logger.info(LoggerInstance.Messages.noCompanyFound.getMessage());		
	}
}
