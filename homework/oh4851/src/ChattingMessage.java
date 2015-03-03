import java.io.Serializable;

/**
 * Created by oh4851 on 15. 3. 3..
 */
public class ChattingMessage implements Serializable {
    public static enum enumMsgType {MSG, LOGIN, LOGOUT};
    private enumMsgType msgType = null;
    private String message = null;

    ChattingMessage(enumMsgType msgType, String message) {
        this.msgType = msgType;
        this.message = message;
    }

    public enumMsgType getMsgType() {
        return msgType;
    }

    public String getMessage() {
        return message;
    }
}
