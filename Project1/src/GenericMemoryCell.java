/**
 * Created by nxc141130 on 9/28/16.
 */

public class GenericMemoryCell<AnyType>
{
    public AnyType read()
    { return storedValue; }

    public void write(AnyType x)
    { storedValue = x; }

    private AnyType storedValue;
}


 class TestGenericMemoryCell
{
    public static void main(String [] args)
    {
        GenericMemoryCell<Integer> m=
                new GenericMemoryCell<Integer> ( );
         //illegal
        //GenericMemoryCell<Integer> arr[] = new GenericMemoryCell<Integer>[10];

        m.write( 37 );
        int val =  m.read();
        System.out.println("Cell contents: " + val );
    }
}

// Example 2
class Person{

}
class Worker extends Person{

}

class  Employee extends Person{
    Person x= new Employee();
    Person y[]= new Employee[7];


}
