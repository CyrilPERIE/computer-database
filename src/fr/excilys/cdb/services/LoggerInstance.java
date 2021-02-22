package fr.excilys.cdb.services;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerInstance {
	
	private static Logger logger;
	private static LoggerInstance loggerInstance;
	
	private LoggerInstance() {
		PropertyConfigurator.configure("log4j.properties");
		logger = Logger.getLogger(LoggerInstance.class);
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
	
	public enum TypeForScanner {
		
	    INT("integer"), STRING("string");
		
		private String type;
		
		private TypeForScanner(String type) {
			this.type = type;
		}
		
		public String getType() {
			return this.type;
		}
	}
	
	public enum MessageForScanner {
		wrongDateFormat("Wrong date format ! Follow the date format"), 
		wrongIDFormat("Type a valid ID format (TIP: it must be a number)"), 
		nonExistentID("This ID doesn't exist, try another one or refer to the list of computers !"), 
		emptyBase("This base is empty");
		
		private String message;
		
		private MessageForScanner(String message) {
			this.message = message;
		}
		
		public String getMessage() {
			return this.message;
		}
		
	}
	
}


