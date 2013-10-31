package logic;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import ui.Application;

/**
 * Responsible for establishing and terminating remote connections 
 * @author Raj, Souza, Tatros
 *
 */
public class Connector {
	private String receivedMessage = "";
	
	/**
	 * Establish a connection
	 * @param ipAddress The IP Address of the server to connect to
	 * @param port The remote port used for the connection
	 * @param communicator The communicator instance used for exchange of messages
	 * @return The socket used for communication
	 */
	public Socket connect(String ipAddress, int port, Communicator communicator) {
		Socket socket = null;
		try {
			//System.out.println(ipAddress + ": " + port);
			socket = new Socket(ipAddress, port);
			receivedMessage = communicator.unmarshall(socket);
			Application.logger.info("Connected to " + ipAddress + " : " + port);
		} catch (ConnectException ex) {
			Application.logger.info("Connection to the server timed out.");
			System.out.println("Connection to the server timed out.");
			ClientLogic.setIsConnected(false);
			socket = null;
		} catch (UnknownHostException ex) {
			Application.logger.error("Cannot resolve Hostname. Check your DNS. Error: " + ex.getMessage());
			ClientLogic.setIsConnected(false);
			socket = null;
		} catch (IOException ex) {
			Application.logger.error("An IO Exception occured: " + ex.getMessage());
			ClientLogic.setIsConnected(false);
			socket = null;
		}
		return socket;
	}
	
	/**
	 * disconnect from the server
	 * @param socket The socket used for communication
	 * @return Success or error message
	 */
	public String disconnect(Socket socket) {
		String message = "";
		String address = "";
		try {
			address = socket.getInetAddress().getHostAddress() + " / " + socket.getPort();
			message = "Connection terminated: " + address;
			socket.close();
			ClientLogic.setIsConnected(false);
			Application.logger.info("Disconnected from " + address);
		} catch (IOException e) {
			Application.logger.error("Unable to disconnect: " + e.getMessage());
			return "Unable to disconnect from " + address;
		}
		return message;
	}

	/**
	 * Obtain the handshake message received from the server after successful connection
	 * @return the message as a string
	 */
	public String getReceivedMessage() {
		Application.logger.debug("Received handshake message: " + receivedMessage);
		return receivedMessage;
	}
}
