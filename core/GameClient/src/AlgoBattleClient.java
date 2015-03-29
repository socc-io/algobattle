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
	public abstract void gServerCalled(AlgoBattlePacket receivePacket);
	// Players Method Define
	public abstract int pYourTurn();

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

	// 서버에서 요청이 오는걸 듣는다.
	private void listen() {
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						System.out.println("Wating packet from AlgoBattleServer...");
						AlgoBattlePacket receivePacket = (AlgoBattlePacket) in.readObject();
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
	
	public static void main(String[] args){
		String gametitle = args[0]; 
		AlgoBattleClient gfw = null;
		try {
			gfw = (AlgoBattleClient) Class.forName(gametitle).newInstance();
		} catch(Exception e){
			e.printStackTrace();
		}
		if(gfw != null){
			gfw.start();
			gfw.listen();
		}
	}
}
