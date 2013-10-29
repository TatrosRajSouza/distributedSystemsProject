package logic;

public class ClientLogic {
	public void disconnect() {
		System.out.println("disconnect logic");
	}

	public void quit() {
		System.out.println("quit logic");
	}

	public void send(String message) {
		System.out.println("send logic" + message);
	}

	public void logLevel(String level) {
		System.out.println("loglvel  logic" + level);
	}

	public void connect(String ipAdress, int port) {
		System.out.println("connect  logic" + ipAdress + port );
	}
}
