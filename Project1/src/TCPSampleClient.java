import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TCPSampleClient
{
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    public void go()
    {

        String message;
        String message2="1, 2, 3, 4 ,0 ; 3";

        Object string1="test";


        try
        {
            //Create a client socket and connect to server at 127.0.0.1 port 5000
            Socket clientSocket = new Socket("127.0.0.1",3333);
            //Read messages from server. Input stream are in bytes. They are converted to characters by InputStreamReader
            //Characters from the InputStreamReader are converted to buffered characters by BufferedReader
            //BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //The method readLine is blocked until a message is received
            //message = reader.readLine();

            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            Tocken student = new Tocken(1, "Bijoy");
            student.setLabel(1000);
            System.out.println("Object to be written = " + student);
            outputStream.writeObject(student);
            outputStream.close();

            //PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
            //writer.println(message2);
            //writer.close();
            //System.out.println("Client sending:" + message2);



            //System.out.println("Server says:" + message);
            //reader.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    public static void main(String args[])
    {
        TCPSampleClient SampleClientObj = new TCPSampleClient();
        SampleClientObj.go();
    }
}

class Tocken implements Serializable {

    private static final long serialVersionUID = 5950169519310163575L;
    private int id;
    private String name;
    private int label=-1;
    private boolean complete;
    private ArrayList<Integer> pathList;

    private int index;
    private HashMap confMap=new HashMap<Integer,String>();

    public HashMap getConfMap() {
        return confMap;
    }

    public void setConfMap(HashMap confMap) {
        this.confMap = confMap;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<Integer> getPathList() {
        return this.pathList;
    }

    public void setPathList(ArrayList<Integer> gotList) {
        pathList=new ArrayList<Integer>();
        for (int i=0;i<gotList.size();i++) {
            this.pathList.add(gotList.get(i));
        }
    }

    public Tocken(int id, String name) {

        this.id = id;
        this.name = name;
        this.complete=false;
    }



    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isComplete() {
        return complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tocken tocken = (Tocken) o;

        if (id != tocken.id) return false;
        if (name != null ? !name.equals(tocken.name) : tocken.name != null) return false;

        return true;
    }

    public int hashCode() {
        return id;
    }

    public String toString() {
        return "Id = " + getId() + " ; Name = " + getName()+"; Label = "+getLabel();
    }
    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

}

