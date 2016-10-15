//import com.sun.nio.sctp.SctpServerChannel;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.InetAddress;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.nio.channels.ConnectionPendingException;
//import java.nio.charset.Charset;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.BlockingQueue;
//
///**
// * Created by nxc141130 on 9/26/16.
// */
//
//import java.io.*;
//        import java.lang.reflect.Array;
//        import java.net.InetAddress;
//        import java.net.ServerSocket;
//        import java.net.Socket;
//        import java.net.UnknownHostException;
//        import java.nio.channels.ConnectionPendingException;
//        import java.nio.charset.Charset;
//        import java.nio.file.Files;
//        import java.nio.file.Path;
//        import java.nio.file.Paths;
//        import java.util.*;
//        import java.util.concurrent.ArrayBlockingQueue;
//        import java.util.concurrent.BlockingDeque;
//        import java.util.concurrent.BlockingQueue;
//        import java.util.concurrent.LinkedBlockingDeque;
//
//
//
//public class SCTPServer {
//
//
//
//    private String hostname;
//    SCTPServer(){
//        // TODO: 9/18/16
//        // Check the host and assingn it the id
//        try {
//            InetAddress inetAddr = InetAddress.getLocalHost();
//            this.hostname = inetAddr.getHostName();
//        }catch (UnknownHostException e){
//            System.exit(0);
//
//        }
//
//
//
//    }
//    public static HashMap<Integer,String> abc= new HashMap();
//
//    public static HashMap<Integer, String> getAbc() {
//        return abc;
//    }
//
//    public static void setAbc(HashMap<Integer, String> abc) {
//        SCTPServer.abc = abc;
//    }
//
//    public static void main(String[] args) {
//
//        SCTPServer tp=new SCTPServer();
//        HashMap hm=new HashMap<Integer,String>();
//        int myId=Integer.parseInt(args[2]);
//        System.out.println("connection stated on "+args[0]);
//        ConnectionOpener con=new ConnectionOpener("localhost",args[0]);
//        Thread th1=new Thread(con,"th1");
//        th1.start();
//
//        //global array for tracking the path
//        ArrayList<Integer> arl;
//
//
//        if (args.length==3){
//
//            // Read the file
//            Path p2 = Paths.get("src/config.txt");
//            Charset charset = Charset.forName("ISO-8859-1");
//            int numberofnodes=-1;
//            int count=0;
//
//            try {
//                for (String line : Files.readAllLines(p2,charset)) {
//                    if (line.length() !=0){
//                        if (line.toCharArray()[0] != '#'){
//                            if (count==0){
//                                numberofnodes=Integer.parseInt(line);
//                                //count++;
//                            }else {
//                                if (count <= numberofnodes) {
//                                    //identifies the id of the node
//                                    int id = Integer.parseInt(line.split("\t\t\t")[0]);
//                                    // hashmap for id as key and "machine and port" as the values
//                                    hm.put(id, line.split("\t\t\t")[1]);
//                                } else {
//                                    String ele1 = line.split("\t\t\t\t")[0];
//                                    if (Integer.parseInt(ele1) == myId) {
//                                        String ele2 = line.split("\t\t\t\t")[1].trim().trim()
//                                                .replace("(", "").replace(")", "");
//                                        //creates the route list from the config fil
//                                        arl= new ArrayList<Integer>();
//                                        for (String i : ele2.split(", ")) {
//                                            arl.add(Integer.parseInt(i));
//                                        }
//                                        arl.add(myId); // at the end adding my id to as I need want to on the path
//
//                                        boolean initiatorFlag = true; // Determine the inititator
//                                        BlockingQueue queue = new ArrayBlockingQueue(1024);
//
//                                        if (initiatorFlag == true) {
//                                            Tocken myMessage = new Tocken(myId, tp.hostname);
//                                            myMessage.setPathList(arl);
//                                            myMessage.setIndex(-1);
//                                            myMessage.setConfMap(hm);
//                                            //Tocken tocken1 = new Tocken(myId, "Nipun");
//                                            try {
//                                                queue.put(myMessage);
//                                            } catch (Exception e) {
//
//                                            }
//                                            Thread th3 = new Thread(new Reader(queue));
//                                            try {
//                                                th3.sleep(5000);
//                                                th3.start();
//                                            } catch (InterruptedException ie) {
//
//                                            }
//
//                                        }
//
//                                    }
//                                }
//
//                            }
//
//                            count++;
//                        }
//
//                    }
//
//                }
//
//
//            }catch (Exception e){
//                System.out.println("File exception"+e);
//            }
//
//
//
//        }else{
//            System.out.println("Please check usage: \n javac <File.java> port");
//        }
//
//
//    }
//
//
//}
//
//
//// for handling low level networking  and socket connections
//class ConnectionOpener implements  Runnable{
//    // id,host.socket
//    private String host = "";
//    private int port = 0;
//    private Socket socket = null;
//    private ObjectInputStream inStream = null;
//
//    BlockingQueue queue = new ArrayBlockingQueue(1024);
//
//    ConnectionOpener(String host,String port){
//        this.host=host;
//        this.port=Integer.parseInt(port);
//    }
//
//    public String getHost() {
//        return host;
//    }
//
//    public int getPort() {
//        return port;
//    }
//
//
//    @Override
//    public void run() {
//        final int MESSAGE_SIZE = 1000;
//
//
//        try{
//            SctpServerChannel sctpServerChannel = SctpServerChannel.open();
//            InetSocketAddress serverAddr = new InetSocketAddress(portno);
//
//            Socket open=new Socket();
//            ServerSocket serverSock = new ServerSocket(getPort());
//            while(true)
//            {
//                //Listens for a connection to be made to this socket and accepts it
//                //The method blocks until a connection is made
//                Socket sock = serverSock.accept();
//                inStream = new ObjectInputStream(sock.getInputStream());
//                Tocken tocken = (Tocken) inStream.readObject();
//                System.out.println("Object received = " + tocken);
//
//                queue.add(tocken);
//                inStream.close();
//                Reader rea=new Reader(queue);
//
//                Thread tb1=new Thread(rea);
//                tb1.sleep(4000);
//                tb1.start();
//                System.out.println("Client connected to: " + getHost());
//
//            }
//
//
//        }catch (Exception e){ System.out.println("exception occured "+e);}
//    }
//}
//
//class Reader implements Runnable{
//
//    protected BlockingQueue queue=null;
//    Reader(BlockingQueue queue){
//        this.queue=queue;
//    }
//    @Override
//    public void run() {
//        try {
//
//
//            Tocken a = (Tocken) queue.take();
//            System.out.print(" outside if loop : "+ a.getIndex()+" "+a.getPathList().size());
//
//            if (a.getIndex()<a.getPathList().size()-1){
//
//                System.out.println(" values "+a.getIndex()+" "+a.getPathList().size());
//                int sendIndex = a.getIndex();
//                System.out.print(" value before: "+a.getLabel());
//                int addedValue=a.randInt(1,11);
//                System.out.print(" added : "+addedValue);
//                a.setLabel(a.getLabel()+addedValue); //adds the new label to the old label
//                System.out.println(" value after: "+a.getLabel());
//                a.setIndex(sendIndex);
//                a.setIndex(++sendIndex);
//                try {
//
//                    //id of machine// array path // path index //
//                    //Thread th2 = new Thread(new Sender(" 2,2,2,2,2,2"), "th2");
//                    Thread th3 = new Thread(new Sender(a));
//                    th3.start();
//
//                    //th2.start();
//                } catch (Exception e) {
//                    System.out.println("Caught here :"+e);
//
//                }
////
////            if (sendIndex >= a.getPathList().size()) {
////                a.setComplete(true);// taken care of this condition in the else statement ---extra//repetition
////            }
//
//
////            if (a.isComplete() == true) {
////                //Todo
////                // write the label to the log
////                // send a comlete message to args[4]
////
////                //System.exit(0);
////            }
////            else {
////                System.out.println("reader is applying the logic the queue");
////                System.out.println("Element that was put in was " + a.getName());
////            }
//
//
//            } // if for index less than the size finishes
//            else{
//                System.out.println("\n ====== Finished the sending process for " +a.getId()+ " label: "+a.getLabel());
//            }
//
////             (a.getIndex()>=a.getPathList().size()-1){
////                // end the process and log the info
////                // System.log the label
////                System.out.println("\n ====== Finished the sending process for " +a.getId()+ " label: "+a.getLabel());
////                //System.exit(333);
////            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//class Sender  implements Runnable{
//
//    private ObjectInputStream inputStream = null;
//    private ObjectOutputStream outputStream = null;
//
//    ////id of machine// array path // path index //
//    Tocken student ;
//
//    Sender(Tocken t){
//        student=t;
//        System.out.print("sender fetches info from this logical object to be sent "
//                + "\n path:"+t.getPathList()+" \t next index: "+t.getIndex()+"\t value: "
//                +t.getLabel()+" sender-node name: "+t.getName());
//    }
//
//    @Override
//    public void run() {
//        try {
//            int sendTo=student.getIndex();;
//            Integer sendToThis=student.getPathList().get(sendTo);
//            String machine=student.getConfMap().get(sendToThis).toString().split("\t")[0];
//            int portTo=Integer.parseInt(student.getConfMap().get(sendToThis).toString().split("\t\t")[1]);
//            System.out.println(" machine: "+machine+ "full path is "+student.getPathList()
//                    +" sending on the port "+portTo);
//            //System.exit(99);
//            Socket clientSocket = new Socket(machine,portTo);
//            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
//            //Tocken student = new Tocken(1, "dc33");
//            System.out.println("Object to be written = " + student);
//            outputStream.writeObject(student);
//            outputStream.close();
//
//        }catch (ConnectionPendingException cpx){
//            System.out.println("failed at Connection Pending :"+cpx);
//        }catch (IOException io){
//            System.out.println("Got an IO exception at sending: "+io);
//        }
//
//    }
//}
//
//
