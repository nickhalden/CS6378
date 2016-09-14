import java.io.*;
import java.util.Vector;

/**
 * Created by nxc141130 on 9/11/16.
 */
public abstract class BasicMessage {
    protected String id;
    protected Vector argList;
    public BasicMessage() {
        argList = new Vector();
    }
    public BasicMessage(String mid) {
        id = mid;
        argList = new Vector();
    }
    protected void setId(String mid) {
        id = mid;
    }

    public void addArg(String arg) {
        argList.addElement(arg);
    }
    public String messageID() {
        return id;
    }

    public Vector argList() {
        Vector listCopy = (Vector)argList.clone();
        return listCopy;
    }

    public abstract boolean Do();

}

abstract class BasicMsgHandler implements Runnable{

    // Static message handler for applications where only one message
    // handler is used and needs to be globally accessible.
    public static BasicMsgHandler current = null;
    InputStream msgIn;
    OutputStream msgOut;
    StreamTokenizer tokenizer;
    String msgEndToken = "END";
    public BasicMsgHandler(InputStream in, OutputStream out) {
        setStreams(in, out);
        current = this;
    }
    protected void setStreams(InputStream in, OutputStream out) {
        msgIn = in;
        msgOut = out;
    }
    public BasicMsgHandler(InputStream in, OutputStream out,
                           String endToken) {
        msgEndToken = endToken;
        setStreams(in, out);
        current = this;
    }

    public BasicMessage readMsg() throws IOException {
        BasicMessage msg;
        String token;
        DataInputStream din = new DataInputStream(msgIn);

        token = din.readUTF();
        msg = buildMessage(token);

        if (msg != null) {
            boolean msgEnd = false;
            while (!msgEnd) {
                token = din.readUTF();
                if (token.compareTo(msgEndToken) == 0)
                    msgEnd = true;
                else {
                    msg.addArg(token);
                }
            }
        }
        return msg;
    }
    public void sendMsg(BasicMessage msg) throws IOException {
        boolean success = true;
        DataOutputStream dout = new DataOutputStream(msgOut);

        dout.writeUTF(msg.messageID());

        Vector args = msg.argList();
        int acnt = args.size();
        for (int i = 0; i < acnt; i++) {
            dout.writeUTF((String)args.elementAt(i));
        }

        dout.writeUTF(msgEndToken);
    }



    @Override
    public void run() {
        try {
            while (true) {
                BasicMessage msg = readMsg();
                if (msg != null)
                    msg.Do();
            }
        }
        // Treat an IOException as a termination of the message
        // exchange, and let this message-processing thread die.
        catch (IOException e) {}


    }
    protected abstract BasicMessage buildMessage(String msgId);

}