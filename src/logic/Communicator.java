package logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import ui.Application;

public class Communicator {
	/**
	 * Converts string into stream of bytes and send it to the server
	 * @param sendMsg
	 * @param socket
	 * @throws IOException
	 */
	public void marshall(String sendMsg, Socket socket) throws IOException {		
		byte[] sendByte = sendMsg.getBytes();
		Application.logger.debug("Size of Message: " + sendByte.length + " Bytes");
		if (sendByte.length > 1024) {
			throw new IllegalArgumentException("The message was too long. (" + sendByte.length + " kB). Maximum allowed is 128 kB.");
		}
		OutputStream out = socket.getOutputStream();
		out.write(sendByte);
	}
	/**
	 * converts stream of bytes from the server to string
	 * @param socket
	 * @return
	 * @throws IOException
	 */
	public String unmarshall(Socket socket) throws IOException {
		String message;
		InputStream in = socket.getInputStream();
		byte[] receivedByte = new byte[1024];
		in.read(receivedByte);
		message = new String(receivedByte);
		return message.trim();
	}
}
