package logic;

import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Level;

import ui.Application;

/**
 * Handles the application logic.
 * @author Raj, Souza, Tatros
 *
 */
public class ClientLogic {
	private static boolean isConnected = false;
	private Socket socket;
	private Connector connector;
	private Communicator communicator;
	
	/**
	 * Creates new Logic, Initializes communicator
	 */
	public ClientLogic() {
		communicator = new Communicator();
	}
	
	/**
	 * Connect to the server
	 * @param ipAddress IPv4 address to connect to
	 * @param port Valid remote port number
	 * @return Received message as String if successful, or error message otherwise
	 */
	public String connect(String ipAddress, int port) {
		if (!isConnected) {
			connector = new Connector();
			socket = connector.connect(ipAddress, port, communicator);
			isConnected = true;
			if (socket == null) {
				Application.logger.error("Unable to connect to " + ipAddress + ":" + port);
				isConnected = false;
				return null;
			}
			//Application.logger.info("Connected to " + ipAddress + " : " + port);
			return connector.getReceivedMessage();
		} else {
			return "Already connected. Use disconnect first.";
		}
	}
	
	/**
	 * Disconnect from the server
	 * @return Success message or error message in case of failure
	 */
	public String disconnect() {
		if (isConnected) {
			return connector.disconnect(socket);
		} else {
			Application.logger.warn("User tried to disconnect while not connected.");
			return "You are not connected yet.";
		}
	}
	
	/**
	 * send message to server
	 * @param message The message that should be transmitted to the server
	 * @return Response received from server
	 */
	public String send(String message) {
		String receivedMsg = null;
		try {
			if (isConnected) {
				communicator.marshall(message, socket);
				receivedMsg = communicator.unmarshall(socket);
				Application.logger.debug("Sent message: " + message + "\n to " + socket.getInetAddress().getHostAddress() + " : " + socket.getPort());
				Application.logger.info("Sent message: " + message);
			} else {
				Application.logger.warn("User tried to send message while not connected");
				return "You are not connected. Use 'connect <ipaddress> <port>' to connect.";
			}
		} catch (IllegalArgumentException ex) {
			Application.logger.error("An Argument exception occured: " + ex.getMessage());
		} catch (IOException e) {
			Application.logger.error("An IO error occurred while sending a message. Please try again.");
		}

		if (receivedMsg == null) {
			Application.logger.error("No message was received.");
			return "No message was received from the server.";
		}
		return receivedMsg;
	}
	
	/**
	 * Exit application and close open connection
	 */
	public void quit() {
		if (isConnected) {
			disconnect();
		}

		Application.logger.info("Application terminated by user.");
	}
	
	/**
	 * set the log level for the log4j logger
	 * @param level A log4j logLevel
	 */
	public void logLevel(Level level) {
		Application.logger.setLevel(level);
		Application.logger.info("LogLevel set to " + level.toString());
	}

	/**
	 * Set the connection status
	 * @param bool The current connection status
	 */
	public static void setIsConnected(boolean bool) {
		ClientLogic.isConnected = bool;
		Application.logger.debug("isConnected set to " + bool);
	}

	/**
	 * Set the socket that is used for communication
	 * @param socket
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
		Application.logger.debug("New socket was set.");
	}

	/**
	 * Obtain the socket used for communication
	 * @return The socket used for communication
	 */
	public Socket getSocket() {
		return this.socket;
	}
}