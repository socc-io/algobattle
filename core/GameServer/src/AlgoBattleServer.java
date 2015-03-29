import java.io.IOException;

public abstract class AlgoBattleServer {
	AlgoBattleConnectionManager abcm;
	boolean isNotFirstTime = false;
	
    public abstract void gInit();
    public abstract boolean gValid(AlgoBattlePacket[] packets);
    public abstract void gIlleagal();
    public abstract void gPlay(AlgoBattlePacket[] packets);

    public void startGame() {
    	gInit();
    	abcm = new AlgoBattleConnectionManager(this);
    	abcm.start();
    }
    
    public void callBackProcess(AlgoBattlePacket[] packets) {
    	if (isNotFirstTime) {
            gValid(packets);
            gPlay(packets);
    	} else {
    		isNotFirstTime = true;
    	}
    	abcm.sendClient();
    }
    
    public void stopGame() {
    	history();
    	abcm.stop();
    	System.exit(0);
    }
    
    public void history() {
    	
    }
    
    public static void main(String[] args){
    	String gametitle = args[0];
    	AlgoBattleServer gfw = null;
		try {
			gfw = (AlgoBattleServer) Class.forName(gametitle).newInstance();
		} catch(Exception e){
			e.printStackTrace();
		}
		if(gfw != null){
			gfw.startGame();
		}
    }
}
