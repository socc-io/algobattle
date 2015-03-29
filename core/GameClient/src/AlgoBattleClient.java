import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class AlgoBattleClient {
	private String ip = "localhost";
	private int port = 5000;
	private Socket socket = null;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;

	// Client_RSP Method Define
	public abstract void gInit();
	public abstract void gServerCalled(AlgoBattlePacket receivePacket);

	private void start() {
		try {
			System.out.println("[CONNECT] Server : " + ip + "(" + port + ")");
			socket = new Socket(ip, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void stop() {
		System.out.println("[Game Over]");
		try {
			if (in != null) in.close();
			if (out != null) out.close();
			if (socket != null) socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private void listen() {
		gInit();

		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						System.out.println("[WAIT] Packet from AlgoBattleServer...");
						AlgoBattlePacket receivePacket = (AlgoBattlePacket) in.readObject();
						gServerCalled(receivePacket);
					}
				} catch (ClassNotFoundException | IOException e) {
					// e.printStackTrace();
				} finally {
					stop();
				}
			}
		});
		th.setDaemon(true);
		th.start();
	}

	void sendToServer(AlgoBattlePacket sendPacket) {
		try {
			out.writeObject(sendPacket);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String gametitle = args[0];
		AlgoBattleClient abc = null;
		try {
			abc = (AlgoBattleClient) Class.forName(gametitle).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (abc != null) {
			abc.start();
			abc.listen();
		}
	}
}
