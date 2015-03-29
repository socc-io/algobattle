
public abstract class AlgoBattleServer {
	private AlgoBattleConnectionManager abcm;
	private boolean isNotFirstTime = false;
	
    public abstract void gInit();
    public abstract boolean gValid(AlgoBattlePacket[] receivePackets);
    public abstract void gIlleagal();
    public abstract void gPlay(AlgoBattlePacket[] receivePackets);

    private void startGame() {
    	gInit();
    	abcm = new AlgoBattleConnectionManager(this);
    	abcm.start();
    }
    
    void callBackProcess(AlgoBattlePacket[] receivePackets) {
    	if (isNotFirstTime) {
            gValid(receivePackets);
            gPlay(receivePackets);
    	} else {
    		isNotFirstTime = true;
    	}
    	abcm.sendToClient();
    }
    
    public void stopGame() {
    	System.out.println("[Game Over]");
    	history();
    	abcm.stop();
    	System.exit(0);
    }
    
    private void history() {
    	
    }
    
    public static void main(String[] args){
    	String gametitle = args[0];
    	AlgoBattleServer abs = null;
		try {
			abs = (AlgoBattleServer) Class.forName(gametitle).newInstance();
		} catch(Exception e){
			e.printStackTrace();
		}
		if(abs != null){
			abs.startGame();
		}
    }
}
