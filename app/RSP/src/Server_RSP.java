

// java Server RSP
class Server_RSP extends AlgoBattleServer {
	private static final int MAX_TURN = 10;
    private String[] rspStr = {"ROCK", "SISSORS", "PAPER"};
    private int turn;

	@Override
	public void gInit() {
  
	}

	@Override
	public boolean gValid(AlgoBattlePacket[] receivePackets) {
		return true;
	}

    @Override
    public void gPlay(AlgoBattlePacket[] receivePackets) {
        int user1 = Integer.parseInt(receivePackets[0].value1);
        int user2 = Integer.parseInt(receivePackets[1].value1);
        int result = user1 - user2;

        if (result == 0) {
            System.out.println("[DRAW] " + rspStr[user1] + " : " + rspStr[user2] + "(user1 : user2)");
        } else if (result > 0) {
            System.out.println("[USER2 WIN] " + rspStr[user1] + " : " + rspStr[user2] + "(user1 : user2)");
        } else {
            System.out.println("[USER1 WIN] " + rspStr[user1] + " : " + rspStr[user2] + "(user1 : user2)");
        }
        
        if (turn++ >= MAX_TURN) stopGame();
    }
    
    @Override
    public void gIlleagal() {	
    }
}