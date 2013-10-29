package ui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;

public class Application {

	public static void main(String[] args) throws IOException {
		/* Initialize Logging */
		Logger logger = Logger.getLogger(Application.class.getName());
		logger.setLevel(Level.ALL);
		SimpleLayout sL = new SimpleLayout();
		ConsoleAppender cA = new ConsoleAppender(sL);
		logger.addAppender(cA);
		
		
		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
		boolean quit = false;
		
		while (!quit) {
			System.out.print("Client> ");
			String input = userInput.readLine();
			String[] tokens = input.trim().split("\\s+");
			
			if (tokens != null) {
				if (tokens.length == 2  && tokens[0].equals("help") 
						&& tokens[1].equals("send")) {
					System.out.println("Syntax: <send> <textmessage>\n"
							+ "Will send the given message to the currently connected echo server");
					
				}
			}
		}
		System.out.println("Hello World");
	}

}
