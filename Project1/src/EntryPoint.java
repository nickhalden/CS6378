import java.util.Arrays;

/**
 * Created by ravichawla on 02/09/16.
 */
class MultithreadingDemo implements Runnable {
    private Thread t;
    private String threadName;

    MultithreadingDemo(String name){
        threadName=name;
        System.out.println("creating "+threadName);
    }
    public void run(){
        System.out.println("running the run method");
        try {
            for (int i=0;i<10;i++){
                System.out.println(threadName+" is printing  the value "+i);
                Thread.sleep(50);
            }

        }catch (InterruptedException ie){
            System.out.println(threadName +" interuppted "+ ie);

        }
    }
    public void start(){
        System.out.println("Executing the start method") ;
        if (t==null){
            Thread t= new Thread(this,threadName);
            t.start();
        }

    }


}

public class EntryPoint extends Thread{



    public static void main(String args[]){
        MultithreadingDemo obj1=new MultithreadingDemo("thread-1");
        obj1.start();
        MultithreadingDemo obj2=new MultithreadingDemo("thread-2");
        obj2.start();
        EntryPoint ep=new EntryPoint(); // to check the main thread
        try
        {
            while(ep.isAlive())
            {
                System.out.println("Main thread will be alive till the child thread is live");
                Thread.sleep(300028);
            }
        }
        catch(InterruptedException e)
        {
            System.out.println("Main thread interrupted");
        }
        System.out.println("Main thread's run is over" );


    }

}



