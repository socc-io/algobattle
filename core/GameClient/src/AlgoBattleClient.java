import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
			System.out.println("Connecting to Server...");
			socket = new Socket(ip, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// �꽌踰꾩뿉�꽌 �슂泥��씠 �삤�뒗嫄� �뱽�뒗�떎.
	private void listen() {
		gInit();

		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						System.out
								.println("Wating packet from AlgoBattleServer...");
						AlgoBattlePacket receivePacket = (AlgoBattlePacket) in
								.readObject();
						gServerCalled(receivePacket);
					}
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}

			}
		});
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
