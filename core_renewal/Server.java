import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

abstract class GameFrameWork {
    public AlgoPacket[] packets = new AlgoPacket[2];
    public Socket[] clients = new Socket[2];
    public void start(){
        init();
        server_start();

        packet_transfer();
        while (true) {
            valid();
            play();
            //history();
            //packet_transfer();
        }
    }

    public void server_start() {
        int userCount = 0;
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server is Started...");

            while (userCount < 2) {
                clients[userCount] = server.accept();
                Thread readerThread = new Thread(new ReaderThread(userCount));
                readerThread.start();
                userCount++;

                System.out.println("user" + userCount + " is Connect");
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

    class ReaderThread implements Runnable {
        int number;
        Socket client = null;
        ObjectInputStream in = null;

        ReaderThread(int index) {
            number = index;
            client = clients[index];
            try {
                this.in = new ObjectInputStream(client.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    AlgoPacket packet = (AlgoPacket)in.readObject();
                    packets[number] = packet;
                    System.out.println(number + " : " + packet.value1);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class GameFactory {
    public GameFrameWork getGame(String title) {
        GameFrameWork gfw = null;
        if (title.equals("Tictaktoe")) {
//            gfw = new Tictaktoe();
        } else if (title.equals("RSP")) {
            gfw = new RSP();
        }
        return gfw;
    }
}

// java Server RSP
class RSP extends GameFrameWork {
    private int turn;
    private ObjectOutputStream[] outs = new ObjectOutputStream[2];
    @Override
    public void init() {
        turn = 0;
    }

    @Override
    public synchronized void valid() {
        while (packets[0] == null && packets[1] == null) {
            // Packet value range 0 ~ 2
        }
    }

    @Override
    public void play() {
        int result = Integer.parseInt(packets[0].value1) - Integer.parseInt(packets[1].value1);

        if (result == 0) {
            System.out.println("Draw");
        } else if (result > 0) {
            System.out.println("User2 Win");
        } else {
            System.out.println("User1 Win");
        }
    }

    @Override
    public void history() {

    }

    @Override
    public void packet_transfer() {
        if (turn <= 10) {
            for (int i = 0; i<outs.length; i++) {
                if (outs[i] == null) {
                    try {
                        outs[i] = new ObjectOutputStream(clients[i].getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    outs[i].writeObject(new AlgoPacket());
                    outs[i].flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

public class Server {
    public static void main(String[] ar){
        GameFactory gf = new GameFactory();
        GameFrameWork gfw = gf.getGame(ar[0]);
        gfw.start();
    }
}