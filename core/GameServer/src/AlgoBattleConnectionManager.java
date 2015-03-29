import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class AlgoBattleConnectionManager {
	public static final int CLIENT_MAX = 2;
	AlgoBattleServer abs = null;
    Socket[] clients = new Socket[2];
    ObjectOutputStream[] outs = new ObjectOutputStream[2];
    ObjectInputStream[] ins = new ObjectInputStream[2];
    AlgoBattlePacket[] packets = new AlgoBattlePacket[2];
    boolean isNotRunning = false;
	
	AlgoBattleConnectionManager(AlgoBattleServer abs) {
		this.abs = abs;
	}
	
	public void sendClient() {
    	System.out.print("SendPacket To Clients...");
        for (int i=0; i<outs.length; i++) {
            try {
                outs[i].writeObject(new AlgoBattlePacket());
                outs[i].flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	public void start() {
		int clientCount = 0;
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("ConnectionManager is Started...");

            while (clientCount < CLIENT_MAX) {
                clients[clientCount] = server.accept();
                outs[clientCount] = new ObjectOutputStream(clients[clientCount].getOutputStream());
                ins[clientCount] = new ObjectInputStream(clients[clientCount].getInputStream());

                System.out.println("user" + clientCount++ + " is Connected [IO Stream maked]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Thread packetReaderThread = new Thread(new Runnable() {
			@Override
			public void run() {
				int packetCount;
				while (!isNotRunning) {
					abs.callBackProcess(packets);
					for (packetCount=0; packetCount<CLIENT_MAX; packetCount++) {
						try {
							packets[packetCount] = (AlgoBattlePacket)ins[packetCount].readObject();
							System.out.println("Client" + packetCount+1 + " is Received");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
        packetReaderThread.start();
	}
	
	public void stop() {
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
