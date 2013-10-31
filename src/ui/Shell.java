package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Level;

/**
 * A basic shell for the user client.
 * @author Raj, Souza, Tatros
 *
 */
public class Shell {
	private BufferedReader userInput;
	private CharsetEncoder asciiEncoder;
	private boolean quit;
	/**
	 * Creates a new shell.
	 */
	public Shell() {
		userInput = new BufferedReader(new InputStreamReader(System.in));
		Application.logger.info("Shell Created.");
		asciiEncoder = Charset.forName("US-ASCII").newEncoder();
	}

	/**
	 * Main Loop. Displays the client shell, awaiting and processing user inputs.
	 */
	public void displayShell() {
		try {
			quit = false;

			while (!quit) {
				System.out.print("EchoClient> ");
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
		boolean worked = true;

		if (tokens[0].equals("quit")) {
			Application.clientLogic.quit();
			quit = true;
		} else {
			System.out.print("EchoClient> ");
			if (tokens != null) {
				if (tokens.length == 3 && tokens[0].equals("connect")) {
					checkConnect(tokens);
				} else if (tokens.length == 2) {
					if (tokens[0].equals("help")) {
						checkHelp(tokens);
					} else if (tokens[0].equals("logLevel")) {
						checkLogLevel(tokens);
					} else {
						worked = false;
					}
				} else if (tokens.length == 1) {
					if (tokens[0].equals("help")) {
						System.out.println("Syntax: <help> <command>\n"
								+ "<command> List of commands.\n"
								+ "(connect | disconnect | send | logLevel | help | quit)");
					} else if (tokens[0].equals("disconnect")) {
						String message = Application.clientLogic.disconnect();
						System.out.println(message);
					} else {
						worked = false;
					}
				} else {
					worked = false;
				}

				if (tokens.length > 1 && tokens[0].equals("send")) {
					String combinedTokens = combineTokens(tokens);
					String message = "";
					if (isASCII(combinedTokens)) {
						message = Application.clientLogic.send(combinedTokens);
					} else {
						message = "Cannot send non ASCII character.";
					}
					System.out.println(message);
					worked = true;
				} else if (!worked) {
					printHelp();
				}
			}
		}
	}
	/**
	 * 
	 * @param tokens
	 * @return combined string with added carriage return in the end
	 */
	private String combineTokens(String[] tokens) {
		String combinedToken = "";

		for (int i = 1; i < tokens.length; i++) {
			combinedToken += tokens[i] + " ";
		}

		return combinedToken.trim() + "\r";
	}
	/**
	 * Check the message contains only ascii
	 * @param message
	 * @return boolean
	 */
	private boolean isASCII(String message) {
		return asciiEncoder.canEncode(message);
	}
	/**
	 * print instructions to the user
	 */
	private void printHelp() {
		System.out.println("Unknown command. Use 'help' for list of commands.");
	}
	/**
	 * Apply log level to the logger
	 * @param tokens
	 */
	private void checkLogLevel(String[] tokens) {
		if (tokens[1].equals("ALL") || tokens[1].equals("DEBUG") || tokens[1].equals("INFO") || tokens[1].equals("WARN")
				|| tokens[1].equals("ERROR")|| tokens[1].equals("FATAL") || tokens[1].equals("OFF")) {
			Application.clientLogic.logLevel(Level.toLevel(tokens[1]));
			System.out.println("Log level set to " + tokens[1]);
		}
		else {
			printHelp();
		}
	}
	/**
	 * print help manual for each command
	 * @param tokens
	 */
	private void checkHelp(String[] tokens) {
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
			printHelp();
		}
	}
	/**
	 * Validate ip,port and connect to server
	 * @param tokens
	 */
	private void checkConnect(String[] tokens) {
		final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(tokens[1]);
		int port = Integer.parseInt(tokens[2]);

		if (matcher.matches() && port >= 1 && port <= 65535) {
			String message = Application.clientLogic.connect(tokens[1], port);
			if (message != null) {
				System.out.println(message);
			}
			else
			{
				System.out.println("Server is not reachable. Check Your internet connection/Retry later");
			}
		} else {
			System.out.println("Unknown ip adress or port.");
		}
	}

}
