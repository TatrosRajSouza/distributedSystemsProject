package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import logic.ClientLogic;

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
	 * Main Loop. Displays the client shell, awaiting and processing user inputs.
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
			if (tokens.length == 2) {
				if (tokens[0].equals("help")) {
					if (tokens[1].equals("send")) {
						System.out.println("Syntax: <send> <textmessage>\n"
								+ "Will send the given message to the currently connected echo server");
					} else if (tokens[1].equals("connect")) {
						System.out.println("Syntax: <connect> <address> <port>\n"
								+ "  <address>: Hostname or IP Address of the Server.\n"
								+ "  <port>: The port of the echo service on the respective server.\n"
								+ "  Connects to the given server at the specified port.");
					} else if (tokens[1].equals("disconnect")) {
						System.out.println("Syntax: <disconnect>\n"
								+ "Tries to disconnect from the connected server.");
					} else if (tokens[1].equals("disconnect")) {
						System.out.println("Syntax: <logLevel> <level>\n"
								+ "  <level>: One of the following log4j log levels: "
								+ "(ALL | DEBUG | INFO | WARN | ERROR | FATAL | OFF)"
								+ "Sets the logger to the specified log level");
					} else {
						System.out.println("Unknown command. Use 'help' for list of commands.");
					}
				}
			} else if (tokens.length == 1) {
				if (tokens[0].equals("help")) {
					System.out.println("Syntax: <help> <command>\n"
							+ "<command> List of commands.\n"
							+ "(connect | disconnect | send | logLevel | help | quit)");
				} else if (tokens[0].equals("disconnect")) {
					Application.clientLogic.disconnect();
				} else if (tokens[0].equals("quit")) {
					Application.clientLogic.quit();
				}
			}


			/*else {
				System.out.println("Unknown command. Use 'help' for list of commands.");
			}*/
		}
	}

}
