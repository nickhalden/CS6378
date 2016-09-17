import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.ConnectionPendingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
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
    private String hostname;


    TestProject(){
        //String hostname="";
        try {
            InetAddress inetAddr = InetAddress.getLocalHost();
            hostname = inetAddr.getHostName();
        }catch (UnknownHostException e){
            System.exit(0);
        }


    }

    public static void main(String[] args) {
        //Global hasmap each application maintains

        TestProject tp=new TestProject();
        System.out.println("I am "+ tp.hostname );
        HashMap hm=new HashMap<Integer,String>();

        //global array for tracking the path
        ArrayList<String> arl = new ArrayList<String>();

        if (args.length==2){

            // Read the file
            String configFile=args[1];
            Path p2 = Paths.get("/Users/nxc141130/IdeaProjects/CS6378/Project1/out/production/Project1/config.txt");
            Charset charset = Charset.forName("ISO-8859-1");
            int numberofnodes=-1;
            int count=0;

            try {
                for (String line : Files.readAllLines(p2,charset)) {
                    if (line.length() !=0){
                        if (line.toCharArray()[0] != '#'){
                            if (count==0){
                                numberofnodes=Integer.parseInt(line);
                                //count++;
                            }else {
                                if (count <=numberofnodes){
                                    //id for identifying the node
                                    int id=Integer.parseInt(line.split("\t\t\t")[0]);
                                    hm.put(id,line.split("\t\t\t")[1]);
                                }else {
                                    String ele1=line.split("\t\t\t")[0];
                                    String ele2=line.split("\t\t\t")[1].trim().trim()
                                            .replace("(","").replace(")","");
                                    arl.add(ele1+" ,"+ele2+" ,"+ele1);
                                }

                            }
                            count++;
                        }

                    }

                    }


            }catch (Exception e){
                System.out.println("File exception"+e);
            }
            for (Object key: hm.values()){
                System.out.println("printing the keys "+key);
            }
            for (String element: arl){

                System.out.println(element);
            }


            BlockingQueue globalQueue= new LinkedBlockingDeque();
            System.out.println("connection stated on "+args[0]);
            ConnectionOpener con=new ConnectionOpener("localhost",args[0]);
            Thread th1=new Thread(con,"th1");
            th1.start();
            boolean initiatorFlag=true; // Determine the inititator
            if (initiatorFlag == true){
                Thread th2=new Thread(new Sender(arl.get(0)),"th2");
                try{
                    th2.sleep(5000);
                    th2.start();
                }catch (InterruptedException ie){

                }

            }

            //new Thread(new Sender(arl.get(0))).start();
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
    private int port = 0;
    private Socket socket = null;
    private ObjectInputStream inStream = null;

    BlockingQueue queue = new ArrayBlockingQueue(1024);

    ConnectionOpener(String host,String port){
        this.host=host;
        this.port=Integer.parseInt(port);
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
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
            ServerSocket serverSock = new ServerSocket(getPort());
            while(true)
            {
                //Listens for a connection to be made to this socket and accepts it
                //The method blocks until a connection is made
                Socket sock = serverSock.accept();
                inStream = new ObjectInputStream(sock.getInputStream());
                Tocken tocken = (Tocken) inStream.readObject();
                System.out.println("Object received = " + tocken.getId());
                //PrintWriter is a bridge between character data and the socket's low-level output stream

                queue.add(tocken);
                inStream.close();

                //BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                //The method readLine is blocked until a message is received
                //dummy = reader.readLine();
                //reader.close();
                //System.out.println("Client says:" + dummy);
                //queue.add(dummy);
                Reader rea=new Reader(queue);
                new Thread(rea).start();
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
            Tocken a=(Tocken)queue.take();
            if (a.isComplete()==true){
                System.exit(0);
            }else{
                System.out.println("reader is allying the logic the queue"+a);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Sender implements Runnable{
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    Sender(String  initiatorInfo){

        System.out.println("I have to send this info to the next desintation"+ initiatorInfo);


    }
    @Override
    public void run() {
        System.out.println("sender trying to send");
        try {
            Socket clientSocket = new Socket("127.0.0.1",3333);
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            Tocken student = new Tocken(1, "dc33");
            System.out.println("Object to be written = " + student);
            outputStream.writeObject(student);
            outputStream.close();


        }catch (ConnectionPendingException cpx){
                System.out.println(cpx);
        }catch (IOException io){
                System.out.println(io);
        }

    }
}
//Message Logic
// read the message from the client
// that is written on the outputstream
// on the receiving queue add
// start the thread that reads from the quuue
// that queue calls the message method of the current process to genarte the
// the random message and add it to the value from the queue.
// After the build message open the thread(Producer) that start opens a port on the
// next client and empty's it's sending queue
