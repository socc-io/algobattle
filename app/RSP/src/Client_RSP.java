
//abstract class Player_RSP extends Client_RSP {}
public abstract class Client_RSP extends AlgoBattleClient {
	
	public final static int ROCK = 0;
	public final static int SISSOR = 1;
	public final static int PAPER = 2;
	
	@Override
	public void gInit() {
	}

	@Override
	public void gServerCalled(AlgoBattlePacket receivedPacket) {
		int inputData = pYourTurn(receivedPacket);
		System.out.println("[SEND] data : " + inputData);
		AlgoBattlePacket sendPacket = new AlgoBattlePacket("" + inputData);
		sendToServer(sendPacket);
	}

	// Player GAME API
	public void walk_front() {
	}

	// Player ABSTRACT METHOD
	public abstract int pYourTurn(AlgoBattlePacket receivedPacket);
}
