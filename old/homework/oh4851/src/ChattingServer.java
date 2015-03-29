import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by oh4851 on 15. 3. 2..
 */
public class ChattingServer {
    private Map<String, ObjectOutputStream> clients = new HashMap<String, ObjectOutputStream>();

    /*
     * args[0] : port number
     */
    public static void main(String... args) {
        ChattingServer serverInstance = null;

        if (args.length != 1) {
            System.out.println("Please start with port argument");
        } else {
            serverInstance = new ChattingServer();
            serverInstance.start(Integer.parseInt(args[0]));
        }
    }

    private void start(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server started: " + server.getInetAddress() + "[" + server.getLocalPort() + "]");

            while (true) {
                System.out.println("Wait a new client...");
                Socket client = server.accept();
                Thread readThread = new Thread(new ReaderRunnable(client));
                readThread.setDaemon(true);
                readThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void sendMessage(ChattingMessage msg, ObjectOutputStream sender) throws IOException {
        Iterator<String> iterator = clients.keySet().iterator();

        while (iterator.hasNext()) {
            String client = iterator.next();
            ObjectOutputStream writer = clients.get(client);
            if (writer.equals(sender)) continue;
            writer.writeObject(msg);
            writer.flush();
        }
    }

    class ReaderRunnable implements Runnable {
        Socket sock = null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        ReaderRunnable(Socket client) {
            try {
                sock = client;
                out = new ObjectOutputStream(sock.getOutputStream());
                in = new ObjectInputStream(sock.getInputStream());
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
                            sendMessage(msg, out);
                            break;
                        case LOGIN:
                            if (clients.containsKey(msg.getMessage())) {
                                out.writeObject(msg);
                                out.flush();
                            }
                            System.out.println("LOGIN: " + msg.getMessage());
                            clients.put(msg.getMessage(), out);
                            break;
                        case LOGOUT:
                            System.out.println("LOGOUT: " + msg.getMessage());
                            clients.remove(msg.getMessage());
                            sock.close();
                            out.close();
                            in.close();
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
