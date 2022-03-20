import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class App {
    public static void main(String[] args) {
        RingCollection<Integer> myRingCollection = new RingCollection<>(5);
        myRingCollection.add(5);
        myRingCollection.add(5);
        myRingCollection.add(54);
        myRingCollection.add(43);
        myRingCollection.add(5);
        myRingCollection.add(5);
        myRingCollection.add(5);

        Iterator<Integer> iterator = myRingCollection.iterator();
        int i = 0;
        while(i < 15){
        //    System.out.println(iterator.next());
            i++;
        }
        System.out.println(Arrays.toString(myRingCollection.getArr()));
        myRingCollection.delete(4);
        System.out.println(Arrays.toString(myRingCollection.getArr()));
        myRingCollection.add(7);
        System.out.println(Arrays.toString(myRingCollection.getArr()));



    }
}
