import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * Created by ravichawla on 07/09/16.
 */

// to implement it using Gentrics
public class ProgrammingChallenge {
    public static void main(String[] args) {
        Rotate ar=new Rotate();
        ar.arrayrotate();
        ar.stringRotate();

    }
}
//rotate the array at the K'th position
class Rotate{
    private int arr[]={1,2,3,4,5,6,7};
    int k=2; // rotate at
    public void arrayrotate()throws ArrayIndexOutOfBoundsException{
        for (int i=0;i<arr.length;i++){
            //l-k
            System.out.println(arr[(i+arr.length-1-k)%arr.length]);
        }

    }
    public void stringRotate(){
        String s = "the sky is blue";
        Stack<String> stack=new Stack<>();
        for (String last: s.split(" ")) {
            System.out.print(last+"\n");
            stack.push(last);
        }
       while (stack.isEmpty()!=true){
           System.out.print(stack.pop()+" ");
       }



    }

}

