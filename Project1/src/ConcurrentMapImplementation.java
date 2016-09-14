import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by ravichawla on 05/09/16.
 */
public class ConcurrentMapImplementation {
    public static void main(String[] args) {
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
