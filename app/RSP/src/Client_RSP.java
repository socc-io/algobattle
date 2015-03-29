


abstract class Player_RSP extends Client_RSP {}
public abstract class Client_RSP extends AlgoBattleClient {

	@Override
	public void gInit() {
	}

	@Override
	public void gServerCalled(AlgoBattlePacket receivedPacket) {
		int inputData = pYourTurn();
		AlgoBattlePacket sendPacket = new AlgoBattlePacket("" + inputData);
		sendToServer(sendPacket);
	}

	// Player GAME API
	public void walk_front() {

	}

	// Player ABSTRACT METHOD
	public abstract int pYourTurn();
}
