
class GameFrameWork{
    public void start(){
        init();
        init_packet_transfer();
        server_start();
        while(true){
            valid();
            play();
            history();
            packet_transfer();
        }
    }
    public abstract void valid();
    public abstract void play();
    public abstract void history();
    public abstract void packet_tranfer();
}

class TicTaktoe extends GameFramework{
    
}

class GameFactory {
    public getGame(String title){
        GameFrameWork gfw = null;
        if(title.equals("Tictaktoe"){
            gfw = new Tictaktoe();
        }
        return gfw;
    }   
}

public class Server{
    public static void main(String[] ar){
        GameFactory gf = new GameFactory();
        GameFrameWork gfw = gf.getGame(ar[1]);
        gfw.start();
    }
}

// java Server TikTacktoe
