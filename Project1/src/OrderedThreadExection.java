import javax.swing.plaf.TableHeaderUI;

/**
 * Created by ravichawla on 04/09/16.
 */

class MyClass2 implements Runnable{
    @Override
    public void run() {
        Thread t = Thread.currentThread(); // should be inside so that threads have different referrences
        System.out.println("I am thread "+t.getName());
        try {
            Thread.sleep(1000);
        }catch (Exception ie){
            System.out.println("this is a thread in execution");
        }
    }
}
public class OrderedThreadExection  {
    public static void main(String[] args) {
        // Start first thread immediately
        Thread th1=new Thread(new MyClass2(),"th1");
        th1.start();

        try {
            th1.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        Thread th2=new Thread(new MyClass2(),"th2");
        Thread th3 = new Thread(new MyClass2(), "th3");

        th3.start();

        try {
            th3.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }


        th2.start();

    }


}


