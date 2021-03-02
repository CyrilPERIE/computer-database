package fr.excilys.cdb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerInstance {
	
	private static final Logger logger = LoggerFactory.getLogger(LoggerInstance.class);;
	private static LoggerInstance loggerInstance;
	
	private LoggerInstance() {
		
	}
	
	public void expectLoop(String message) {
        logger.debug(message); 
	}
	
	public static LoggerInstance getLoggerInstance() {
		if (logger == null) {
			loggerInstance = new LoggerInstance(); 
		}
		return loggerInstance;
	}
	
	public enum Messages {
		wrongDateFormat("Wrong date format ! Follow the date format"), 
		wrongIDFormat("Type a valid ID format (TIP: it must be a number)"), 
		nonExistentID("This ID doesn't exist, try another one or refer to the list of computers !"), 
		emptyBase("This base is empty"),
		emptyComputerNameField("This computer name field is empty"), 
		connectionFailed("Connection failed"), 
		noCompanyFound("No company found !"), 
		notAfter("Discontinued date must be after introduced Date");
		
		private String message;
		
		private Messages(String message) {
			this.message = message;
		}
		
		public String getMessage() {
			return this.message;
		}
		
	}

	public Logger getLogger() {
		return logger;
	}
	
}


