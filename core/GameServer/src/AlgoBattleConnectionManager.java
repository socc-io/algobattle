import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class AlgoBattleConnectionManager {
	private static final int CLIENT_MAX = 2;
	private AlgoBattleServer abs = null;
	private Socket[] clients = new Socket[2];
	private ObjectOutputStream[] outs = new ObjectOutputStream[2];
	private ObjectInputStream[] ins = new ObjectInputStream[2];
	private AlgoBattlePacket[] receivePackets = new AlgoBattlePacket[2];
	private boolean isNotRunning = false;
	private ServerSocket server;

	AlgoBattleConnectionManager(AlgoBattleServer abs) {
		this.abs = abs;
	}

	void sendToClient() {
		System.out.println("[SEND] Packet To Clients...");
		for (int i=0; i<outs.length; i++) {
			try {
				outs[i].writeObject(new AlgoBattlePacket());
				outs[i].flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	void start() {
		int clientCount = 0;
		try {
			server = new ServerSocket(5000);
			System.out.println("[START] ConnectionManager...");

			while (clientCount < CLIENT_MAX) {
				clients[clientCount] = server.accept();
				outs[clientCount] = new ObjectOutputStream(clients[clientCount].getOutputStream());
				ins[clientCount] = new ObjectInputStream(clients[clientCount].getInputStream());

				System.out.println("[CONNECTED] user" + clientCount++);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread packetReaderThread = new Thread(new Runnable() {
			@Override
			public void run() {
				int packetCount;
				while (!isNotRunning) {
					abs.callBackProcess(receivePackets);
					for (packetCount=0; packetCount<CLIENT_MAX; packetCount++) {
						try {
							receivePackets[packetCount] = (AlgoBattlePacket)ins[packetCount].readObject();
							System.out.println("[RECEIVE] Packet from Client" + (packetCount+1));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		packetReaderThread.setDaemon(true);
		packetReaderThread.start();
	}

	void stop() {
		isNotRunning = true;
		for (int i=0; i<CLIENT_MAX; i++) {
			try {
				if (outs[i] != null) outs[i].close();
				if (ins[i] != null) ins[i].close();
				if (clients[i] != null) clients[i].close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
