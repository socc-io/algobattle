import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class Client_RSP extends AlgoBattleClient {
	private BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	@Override
	public void gServerCalled(AlgoBattlePacket receivedPacket) {
/* REAL
 *		int inputData = pYourTurn();
 *		// TODO: Make AlgoBattlePacket
 *		AlgoBattlePacket sendPacket = new AlgoBattlePacket();
 *		sendToServer(sendPacket);
 */

/* TEST */
		try {
			String hand = console.readLine();
			AlgoBattlePacket sendPacket = new AlgoBattlePacket(hand);
			sendToServer(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Player GAME API
	public void go() {
		
	}
}