package logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import ui.Application;

/**
 * Handles the exchange of messages as byte streams between client and server over a socket 
 * @author Raj, Tatros, Souza
 *
 */
public class Communicator {
	
	/**
	 * Converts string into stream of bytes and send it to the server
	 * @param sendMsg The message that should be transmitted
	 * @param socket The socket used for communication
	 * @throws IOException
	 */
	public void marshall(String sendMsg, Socket socket) throws IOException {		
		byte[] sendByte = sendMsg.getBytes();
		Application.logger.debug("Size of Message: " + sendByte.length + " Bytes");
		if (sendByte.length > 128 * 1024) {
			throw new IllegalArgumentException("The message was too long. (" + sendByte.length + " kB). Maximum allowed is 128 kB.");
		}
		OutputStream out = socket.getOutputStream();
		out.write(sendByte);
	}
	
	/**
	 * Receives stream of bytes from the server and converts it into a String
	 * @param socket The socket used for communication
	 * @return The message received from the server as a String
	 * @throws IOException
	 */
	public String unmarshall(Socket socket) throws IOException {
		String message;
		InputStream in = socket.getInputStream();
		byte[] receivedByte = new byte[128 * 1024];
		in.read(receivedByte);
		message = new String(receivedByte);
		return message.trim();
	}
}
