package logic;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import ui.Application;

public class Connector {
	private String receivedMessage = "";
	
	public Socket connect(String ipAdress, int port, Communicator communicator) {
		Communicator comm = new Communicator();
		Socket socket = null;
		try {
			socket = new Socket(ipAdress, port);
			receivedMessage = comm.unmarshall(socket);		
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}
	
	public String disconnect(Socket socket) {
		String message = "";
		String address = "";
		try {
			address = socket.getInetAddress().getHostAddress() + " / " + socket.getPort();
			message = "Connection terminated: " + address;
			socket.close();
			ClientLogic.setIsConnected(false);
		} catch (IOException e) {
			Application.logger.error("Unable to disconnect: " + e.getMessage());
			return "Unable to disconnect from " + address;
		}
		return message;
	}
	
	public String getReceivedMessage() {
		return receivedMessage;
	}
}
