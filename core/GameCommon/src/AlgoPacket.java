import java.io.Serializable;

// 0 1 2 묵 찌 빠

public class AlgoPacket implements Serializable{
    int[][] map = new int[500][500];
    int col = 0;
    int row = 0;
    String value1 = "";
    String value2 = "";
    String value3 = "";
    String value4 = "";

    AlgoPacket() {

    }

    AlgoPacket(String value1){
        this.value1 = value1;
    }

    public String getvalue1(){
        return value1;
    }
}