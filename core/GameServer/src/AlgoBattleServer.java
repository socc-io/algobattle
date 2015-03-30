
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
			if (gValid(receivePackets)) {
				gPlay(receivePackets);
			} else {
				gIlleagal();
			}
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
		//TODO : 지금까지 했던 히스토리를 저장
		// 그러고 보니 지금 까지 했던 히스토리를 저장하려면, 모든 패킷을 순서대로 모아놓고 있어야함.
		
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
