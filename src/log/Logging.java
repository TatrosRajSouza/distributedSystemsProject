package log;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import ui.Application;
import ui.SimpleLayout;

/**
 * Handles the logging for the application
 * @author Raj, Souza, Tatros
 *
 */
public class Logging {

	private String pattern = "%d{ISO8601} %-5p [%t] %c: %m%n";
	private Logger logger;
	
	/**
	 * Creates the Logger and initializes its properties.
	 * @throws IOException
	 */
	public Logging() throws IOException {
		/* Initialize Logging */
		logger = Logger.getLogger(Application.class.getName());
		logger.setLevel(Level.ALL);
		SimpleLayout sL = new SimpleLayout();
		ConsoleAppender cA = new ConsoleAppender(sL);
		logger.addAppender(cA);
		
		PatternLayout pLayout = new PatternLayout(pattern);
		FileAppender fA = new FileAppender(pLayout, Application.LOG_FOLDER + "/" + Application.LOG_FILE, true);
		logger.addAppender(fA);
	}
	
	/**
	 * Returns the logger
	 * @return The logger
	 */
	public Logger getLogger() {
		return this.logger;
	}
}
