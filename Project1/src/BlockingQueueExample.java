import java.util.concurrent.*;

/**
 * Created by ravichawla on 04/09/16.
 */

 class Producer implements Runnable{
     protected BlockingQueue queue = null;
    Producer(BlockingQueue queue){
        this.queue=queue;
    }
     @Override
    public void run() {
         try {
             queue.put("100");
             Thread.sleep(1000);
             queue.put("200");
             Thread.sleep(1000);
             queue.put("300");
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

    }
}

class Producer2 implements Runnable{
    protected BlockingQueue queue = null;
    Producer2(BlockingQueue queue){
        this.queue=queue;
    }
    @Override
    public void run() {
        try {
            queue.put("100");
            Thread.sleep(1000);
            queue.put("200");
            Thread.sleep(1000);
            queue.put("300");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
class Consumer implements Runnable{
    protected  BlockingQueue queue=null;
    Consumer(BlockingQueue queue){
        this.queue=queue;
    }
    @Override
    public void run() {
        try {
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class BlockingQueueExample   {

    public static void main(String[] args) throws Exception{
        BlockingQueue queue = new ArrayBlockingQueue(1024);
        //queue.add("1");
        //queue.add("2");
        //queue.add("2");

        Producer producer = new Producer(queue);
        new Thread(producer).start();
        Producer2 producer2 = new Producer2(queue);
        new Thread(producer2).start();



        Thread c=new Thread(new Consumer(queue),"thread");
        c.start();

        BlockingQueue<String> unbounded = new LinkedBlockingQueue<String>();
        BlockingQueue<String> bounded   = new LinkedBlockingQueue<String>(1024);

        bounded.put("Value");
        String value = bounded.take();
        System.out.println(value );

        ConcurrentMap concurrentMap = new ConcurrentHashMap();
        concurrentMap.put("firstname","nipun");
        Object mapValue = concurrentMap.get("firstname");
        System.out.println(mapValue);

        ConcurrentNavigableMap map = new ConcurrentSkipListMap();
        map.put("1", "one");
        map.put("20", "two");
        map.put("3", "three");
        map.put("4","zero");


        //head of a hash map
        ConcurrentNavigableMap headMap = map.headMap("3");

        for (Object ele : headMap.entrySet()){
            //prints out the elements in order of there insertion. So does that mean that it implments head`
            System.out.println(ele);
        }
        System.out.println("*****");
        //tail of a hash map
        ConcurrentNavigableMap tailMap = map.tailMap("20");
        for (Object ele : tailMap.entrySet()){
            //prints out the elements in order of there insertion. So does that mean that it implments head`
            System.out.println(ele);
        }
        ConcurrentNavigableMap subMap = map.subMap("1", "3");
        System.out.println("#####");
        //submap of a hashmap
        for (Object ele : subMap.navigableKeySet()){ //descendingKeySet()
            //prints out the elements in order of there insertion. So does that mean that it implments head
            System.out.println(ele);
        }
        ConcurrentNavigableMap desMap = map.subMap("1", "3");
        // hash map in desc order
        System.out.println("------");
        for (Object ele : desMap.entrySet()){ //descendingKeySet()
            //prints out the elements in order of there insertion. So does that mean that it implments head
            System.out.println(ele);
        }







    }

}
