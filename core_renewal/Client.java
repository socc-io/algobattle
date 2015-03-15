import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	String ip = "192.168.0.155";
	int port = 5000;
	private Socket socket = null;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private BufferedReader console = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client c = new Client();
		c.start();
		c.listen();
	}

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
				// TODO Auto-generated method stub
				try {
					while (true) {
						System.out.println("connected wating...");
						AlgoPacket ap = (AlgoPacket) in.readObject();
						System.out.println(ap.getvalue1());
						send();
					}
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		th.start();

	}

	private void send() throws IOException {
		// 사용자한테 입력을 받는다
		console = new BufferedReader(new InputStreamReader(System.in));
		String hand = console.readLine();
		out.writeObject(new AlgoPacket(hand));
		out.flush();

	}

}