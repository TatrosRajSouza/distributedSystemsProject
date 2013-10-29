package ui;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

public class Application {

	private static final Logger logger = Logger.getLogger(Application.class);
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
    	logger.debug("Hello world.");
    	logger.info("What a beatiful day.");
	}

}
