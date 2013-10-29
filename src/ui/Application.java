package ui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import log.Logging;
import logic.ClientLogic;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;

/**
 * The main class for the echoClient application.
 * @author Raj, Souza, Tatros
 *
 */
public class Application {

	public static String LOG_FOLDER = "logs";
	public static String LOG_FILE = "client.log";
	public static Logger logger;
	public static ClientLogic clientLogic = new ClientLogic();
	
	/**
	 * The main entry point for the application.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {
		
		/* Initialize Logging */
		try {
			Logging logging = new Logging();
			logger = logging.getLogger();
			logger.info("New echoClient session created.");		
			
			/* Run Shell */
			Shell shell = new Shell();
			shell.displayShell();
		} catch (IOException ex) {
			System.out.println("Unable to create logger. Exiting Application. Error: " + ex.getMessage());
		}
	}
}
