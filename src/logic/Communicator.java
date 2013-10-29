package logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Communicator {
	
	public void marshall(String sendMsg, Socket socket) throws IOException
	{		
		byte[] sendByte = sendMsg.getBytes();
		OutputStream out = socket.getOutputStream();
		out.write(sendByte);
	}
	public String unmarshall(Socket socket) throws IOException
	{
		String message;
		InputStream in = socket.getInputStream();
		byte[] receivedByte = new byte[1024];
		in.read(receivedByte);
		message = new String(receivedByte);
		return message.trim();
	}
}
