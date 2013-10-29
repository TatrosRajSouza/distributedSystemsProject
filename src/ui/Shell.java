package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A basic shell for the user client.
 * @author Raj, Souza, Tatros
 *
 */
public class Shell {
	private BufferedReader userInput;
	
	/**
	 * Creates a new shell.
	 */
	public Shell() {
		userInput = new BufferedReader(new InputStreamReader(System.in));
		Application.logger.info("Shell Created.");
	}
	
	/**
	 * Displays the client shell, awaiting and processing user inputs.
	 */
	public void displayShell() {
		try {
			boolean quit = false;
			
			while (!quit) {
				System.out.print("Client> ");
				String input = userInput.readLine();
				processInput(input);
			}
		} catch (IOException ex) {
			Application.logger.error("An IO Error occured: " + ex.getMessage());
		}
	}
	
	/**
	 * Processes the input string. 
	 * @param input input string from user
	 */
	public void processInput(String input) {
		String[] tokens = input.trim().split("\\s+");
		
		if (tokens != null) {
			if (tokens.length == 2  && tokens[0].equals("help") 
					&& tokens[1].equals("send")) {
				System.out.println("Syntax: <send> <textmessage>\n"
						+ "Will send the given message to the currently connected echo server");
			} 
		}
	}
		
}
