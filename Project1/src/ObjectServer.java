import com.sun.corba.se.spi.activation.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by nxc141130 on 9/16/16.
 */

public class ObjectServer {
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectInputStream inStream = null;

    public ObjectServer() {

    }

    public void communicate() {
        try {
            while (true) {
                serverSocket = new ServerSocket(4445);
                socket = serverSocket.accept();
                System.out.println("Connected");
                inStream = new ObjectInputStream(socket.getInputStream());

                Student student = (Student) inStream.readObject();
                System.out.println("Object received = " + student);
                //PrintWriter is a bridge between character data and the socket's low-level output stream
                inStream.close();
            }


        } catch (SocketException se) {
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ObjectServer server = new ObjectServer();
        server.communicate();
    }
}

 class Student implements Serializable {

    private static final long serialVersionUID = 5950169519310163575L;
    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;

        return true;
    }

    public int hashCode() {
        return id;
    }

    public String toString() {
        return "Id = " + getId() + " ; Name = " + getName();
    }
}