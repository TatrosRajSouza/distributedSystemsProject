package logic;

import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Level;

import ui.Application;

public class ClientLogic {
	private static boolean isConnected = false;
	private Socket socket;
	private Connector connector;
	private Communicator communicator;

	public ClientLogic() {
		communicator = new Communicator();
	}

	public String connect(String ipAdress, int port) {
		if (!isConnected) {
			connector = new Connector();
			socket = connector.connect(ipAdress, port, communicator);
			isConnected = true;
			if (socket == null) {
				Application.logger.error("Unable to connect to " + ipAdress + ":" + port);
				isConnected = false;
				return null;
			}
			return connector.getReceivedMessage();
		} else {
			return "Already connected. Use disconnect first.";
		}
	}

	public String disconnect() {
		if (isConnected) {
			return connector.disconnect(socket);
		} else {
			return "You are not connected yet.";
		}
	}
	
	public String send(String message) {
		String receivedMsg = null;
		try {
			if (isConnected) {
				communicator.marshall(message, socket);
				receivedMsg = communicator.unmarshall(socket);
			} else {
				return "You are not connected. Use 'connect <ipaddress> <port>' to connect.";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return receivedMsg;
	}

	public void quit() {
		if (isConnected) {
			disconnect();
		}
	}

	public void logLevel(Level level) {
		Application.logger.setLevel(level);
	}



	public static void setIsConnected(boolean bool) {
		ClientLogic.isConnected = bool;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Socket getSocket() {
		return this.socket;
	}

}