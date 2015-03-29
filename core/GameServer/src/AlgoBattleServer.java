import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AlgoBattleServer {
    public Socket[] clients = new Socket[2];
    public ObjectOutputStream[] outs = new ObjectOutputStream[2];
    public ObjectInputStream[] ins = new ObjectInputStream[2];

    public void start() {
        server_start();
        init();

        while (true) {
            packet_transfer();
            valid();
            play();
            //history();
        }
    }

    public void server_start() {
        int userCount = 0;
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server is Started...");

            while (userCount < 2) {
                clients[userCount] = server.accept();
                outs[userCount] = new ObjectOutputStream(clients[userCount].getOutputStream());
                ins[userCount] = new ObjectInputStream(clients[userCount].getInputStream());

                System.out.println("user" + userCount++ + " is Connect [IO Stream maked]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void init();
    public abstract void valid();
    public abstract void play();
    public abstract void history();
    public abstract void packet_transfer();
    public static void main(String[] args){
    	String gametitle = args[0];
    	AlgoBattleServer gfw = null;
		try {
			gfw = (AlgoBattleServer) Class.forName(gametitle).newInstance();
		} catch(Exception e){
			e.printStackTrace();
		}
		if(gfw != null){
			gfw.start();
		}
    }
}
