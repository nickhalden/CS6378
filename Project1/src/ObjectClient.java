import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;

public class ObjectClient {
    private Socket socket = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private boolean isConnected = false;

    public ObjectClient() {

    }

    public void communicate() {

        while (!isConnected) {
            try {
                socket = new Socket("localHost", 4445);
                System.out.println("Connected");
                isConnected = true;
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                Student student = new Student(1, "Bijoy");
                System.out.println("Object to be written = " + student);
                outputStream.writeObject(student);

            } catch (SocketException se) {
                se.printStackTrace();
// System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ObjectClient client = new ObjectClient();
        client.communicate();
    }
}

class Student2 implements Serializable {

    private static final long serialVersionUID = 5950169519310163575L;
    private int id;
    private String name;

    public Student2(int id, String name) {
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

        Student2 student = (Student2) o;

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