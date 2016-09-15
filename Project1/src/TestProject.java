import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by nxc141130 on 9/11/16.
 */
public class TestProject {
    // this is where the main thread starts
    // 1) initialize the connecctions
    // 2) maintain a Synchronous queue
    // 3) drop the data to the queue ( initially empty) receiving queue.
    // 4) pick the emements
    // whenver a



    public static void main(String[] args) {

        if (args.length==1){
            BlockingQueue globalQueue= new LinkedBlockingDeque();
            System.out.println("connection stated on "+args[0]);
            ConnectionOpener con=new ConnectionOpener("localhost",args[0]);
            Thread th1=new Thread(con,"th1");
            th1.start();
            //Thread th2=new Thread(new Reciever(globalQueue),"th1");
            //th1.start();
        }else{
            System.out.println("Please check usage: \n javac <File.java> port");
        }


    }


}


// for handling low level networking  and socket connections
class ConnectionOpener implements  Runnable{
    // id,host.socket
    private String host = "";
    private String port = "";
    BlockingQueue queue = new ArrayBlockingQueue(1024);

    ConnectionOpener(String host,String port){
        this.host=host;
        this.port=port;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    @Override
    public void run() {

        //create a thread to process the message on the execution
        // after creation of sockets keep listesing to the queue for the data
        //
        String message= "hello from the server";
        String message2= "hello from the server";
        String dummy;
        try{
            Socket open=new Socket();
            ServerSocket serverSock = new ServerSocket(5000);
            while(true)
            {
                //Listens for a connection to be made to this socket and accepts it
                //The method blocks until a connection is made
                Socket sock = serverSock.accept();
                //PrintWriter is a bridge between character data and the socket's low-level output stream
                //PrintWriter writer = new PrintWriter(sock.getInputStream());
                //writer.println(message);
                //writer.close();
                BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                //The method readLine is blocked until a message is received
                dummy = reader.readLine();
                reader.close();
                System.out.println("Client says:" + dummy);
                queue.add(dummy);
                Reader rea=new Reader(queue);
                Thread th2= new Thread(rea,"thread-2");
                th2.start();

                //BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                //String message3 = reader.readLine();

                //System.out.println("Message from the client connected to: " + message3);
                System.out.println("Client connected to: " + getHost());

            }


        }catch (Exception e){ System.out.println("exception occured "+e);}
    }
}
class Reciever implements Runnable {
    protected BlockingQueue queue = null;

    Reciever(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            queue.put("100");
            Thread.sleep(1000);
            queue.put("200");
            Thread.sleep(1000);
            queue.put("300");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class Reader implements Runnable{
    protected BlockingQueue queue=null;
    Reader(BlockingQueue queue){
        this.queue=queue;
    }
    @Override
    public void run() {
        try {
            System.out.println("from the  second(reader) thread");
            System.out.println(queue.size());
            System.out.println(queue.take());
            System.out.println(queue.take());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

