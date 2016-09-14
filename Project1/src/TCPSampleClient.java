import java.io.*;
import java.net.*;
public class TCPSampleClient
{
    public void go()
    {
        String message;
        String message2="(1, 2, 3, 4)";
        try
        {
            //Create a client socket and connect to server at 127.0.0.1 port 5000
            Socket clientSocket = new Socket("127.0.0.1",5000);
            //Read messages from server. Input stream are in bytes. They are converted to characters by InputStreamReader
            //Characters from the InputStreamReader are converted to buffered characters by BufferedReader
            //BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //The method readLine is blocked until a message is received
            //message = reader.readLine();

            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
            writer.println(message2);
            writer.close();
            System.out.println("Client sent says:" + message2);

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