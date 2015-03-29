import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

abstract class GameFrameWork {
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
    public AlgoPacket[] packets = new AlgoPacket[2];
    private String[] rspStr = {"ROCK", "SISSORS", "PAPER"};
    private int turn;
    
    @Override
    public void init() {
        turn = 0;

        for (int i=0; i<ins.length; i++) {
            final int finalI = i;
            Thread readThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            packets[finalI] = (AlgoPacket) ins[finalI].readObject();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            readThread.start();
        }
    }

    @Override
    public void packet_transfer() {
        System.out.print("[TURN : " + turn + "] ");
        for (int i=0; i<outs.length; i++) {
            try {
                outs[i].writeObject(new AlgoPacket());
                outs[i].flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void valid() {
        while (packets[0] == null || packets[1] == null) {
            try {
                Thread.sleep(1000);
                System.out.print(">");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("");
    }

    @Override
    public void play() {
        int user1 = Integer.parseInt(packets[0].value1);
        int user2 = Integer.parseInt(packets[1].value1);
        int result = user1 - user2;

        if (result == 0) {
            System.out.println("[DRAW] " + rspStr[user1] + " : " + rspStr[user2] + "(user1 : user2)");
        } else if (result > 0) {
            System.out.println("[USER2 WIN] " + rspStr[user1] + " : " + rspStr[user2] + "(user1 : user2)");
        } else {
            System.out.println("[USER1 WIN] " + rspStr[user1] + " : " + rspStr[user2] + "(user1 : user2)");
        }

        if (turn < 10) {
            turn++;
            packets[0] = null;
            packets[1] = null;
        } else {
            System.out.println("Game Over");
            System.exit(0);
        }
    }

    @Override
    public void history() {

    }
}

public class Server {
    public static void main(String[] args){
        GameFactory gf = new GameFactory();
        GameFrameWork gfw = gf.getGame(args[0]);
        gfw.start();
    }
}
