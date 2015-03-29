import java.io.*;
import java.net.Socket;

/**
 * Created by oh4851 on 15. 3. 2..
 */
public class ChattingClient {
    private String loginId = null;
    private BufferedReader console = null;
    private Socket sock = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    /*
     * args[0] : host
     * args[1] : port number
     * args[2] : login id
     */
    public static void main(String... args) {
        ChattingClient clientInstance = null;

        if (args.length != 3) {
            System.out.println("Please input host, port number, login id");
        } else {
            clientInstance = new ChattingClient();
            clientInstance.start(args[0], Integer.parseInt(args[1]), args[2]);
        }
    }

    private void start(String host, int port, String id) {
        loginId = id;
        try {
            System.out.println("Connect to chatting server");
            sock = new Socket(host, port);
            out = new ObjectOutputStream(sock.getOutputStream());
            in = new ObjectInputStream(sock.getInputStream());

            Thread readThread = new Thread(new ReaderRunnable());
            readThread.setDaemon(true);
            readThread.start();
            console = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.print("Enter message: ");
                String msg = console.readLine();
                if (msg.equals("/logout")) {
                    out.writeObject(new ChattingMessage(ChattingMessage.enumMsgType.LOGOUT, loginId));
                    out.flush();
                } else {
                    out.writeObject(new ChattingMessage(ChattingMessage.enumMsgType.MSG, loginId + ">> " + msg));
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ReaderRunnable implements Runnable {
        ReaderRunnable() {
            try {
                out.writeObject(new ChattingMessage(ChattingMessage.enumMsgType.LOGIN, loginId));
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    ChattingMessage msg = (ChattingMessage)in.readObject();

                    switch (msg.getMsgType()) {
                        case MSG:
                            System.out.println(msg.getMessage());
                            break;
                        case LOGIN:
                            System.out.println("Login failure");
                            sock.close();
                            out.close();
                            in.close();
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}
