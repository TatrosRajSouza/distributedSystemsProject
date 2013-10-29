package log;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ui.Application;

/**
 * Handles the logging for the application
 * @author Raj, Souza, Tatros
 *
 */
public class Logging {

	private Logger logger;
	
	/**
	 * Creates the Logger and initializes its properties.
	 * @throws IOException
	 */
	public Logging() throws IOException {
		/* Initialize Logging */
		logger = Logger.getLogger(Application.class.getName());
		PropertyConfigurator.configure("logs/log.config");
	}
	
	/**
	 * Returns the logger
	 * @return The logger
	 */
	public Logger getLogger() {
		return this.logger;
	}
	
	/**
	 * Clear the contents of log file
	 * @param name name of the file to clear
	 * @throws IOException
	 */
	public void cleanLogFile(String name) throws IOException {
		File file = new File(name);
		PrintWriter writer = new PrintWriter(file);
		writer.print("");
		writer.close();
	}
}
