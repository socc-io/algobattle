import java.io.IOException;

// java Server RSP
class Server_RSP extends AlgoBattleServer {
    public AlgoBattlePacket[] packets = new AlgoBattlePacket[2];
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
                            packets[finalI] = (AlgoBattlePacket) ins[finalI].readObject();
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
                outs[i].writeObject(new AlgoBattlePacket());
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