

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Objects;

public class ArrayDemo {

    public static void main(String[] args) throws IOException,ClassNotFoundException{

        // initializing byte array
        byte arr[] = new byte[] {1, 6, 3};


        Tocken t=new Tocken(1,"Nipiun");

        Byte mine= new Byte(arr[1]);

          //  ObjectInputStream is = new ObjectInputStream(System.in) ;
          //  System.out.println(is.readObject());
        ByteArrayInputStream bis= new ByteArrayInputStream(arr);
        ObjectInputStream is = new ObjectInputStream(bis);


        System.out.println("bis has"+is);







        // let us print the values
        System.out.println("Actual values: ");
        for (byte value : arr) {
            System.out.println("Value = " + value);
        }

        // using fill for placing 8
        // converting int to byte
        Arrays.fill(arr,(byte)8);

        System.out.println(arr);

        // let us print the values
        System.out.println("New values after using fill() method: ");
        for (byte value : arr) {
            System.out.println("Value = " + value);
        }
    }
}