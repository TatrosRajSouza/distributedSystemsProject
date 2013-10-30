package ui;
import java.io.IOException;

import log.Logging;
import logic.ClientLogic;

import org.apache.log4j.Logger;

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
			logging.cleanLogFile(LOG_FOLDER + "/" + LOG_FILE);
			logger = logging.getLogger();
			logger.info("New echoClient session created.");		

			/* Run Shell */
			Shell shell = new Shell();
			shell.displayShell();
		} catch (IOException ex) {
			System.out.println("Unable to create logger. Exiting Application. Error: " + ex.getMessage());
		} catch (Exception ex) {
			System.out.println("FATAL ERROR. Exiting Application. Check log for details.");
			Application.logger.fatal("Application terminated due to fatal error: " + ex.getMessage() + "\nStacktrace: " + ex.getStackTrace());
		}
	}
}
