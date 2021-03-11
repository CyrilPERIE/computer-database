package fr.excilys.cdb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomSQLException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(EmptyError.class);

	public CustomSQLException() {

	}

	public CustomSQLException(String msg) {
		super(msg);
	}

	public String noContentDectect() {
		LOGGER.info(LoggerInstance.Messages.wrongDateFormat.getMessage());
		return LoggerInstance.Messages.wrongDateFormat.getMessage();

	}

	public String connectionLostDetected() {
		LOGGER.info(LoggerInstance.Messages.connectionFailed.getMessage());
		return LoggerInstance.Messages.connectionFailed.getMessage();
	}

	public String noDataDetected() {
		LOGGER.info(LoggerInstance.Messages.noCompanyFound.getMessage());
		return LoggerInstance.Messages.noCompanyFound.getMessage();
	}
}
