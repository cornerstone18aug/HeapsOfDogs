
import java.util.ArrayList;

public class Driver {
  public static void main(String[] args) {
//    ALPriorityQueue<Integer,Integer> alque = new ALPriorityQueue<>();
    ArrayList<Integer> test = new ArrayList<>();
    test.add(1);
    test.add(0,2);
//
//
//    System.out.println(test.toString()
//    );
//    alque.enqueue(1,5);
//    alque.enqueue(2,2);
//    alque.enqueue(3,1);
//
//    System.out.println(alque.peek().getValue());


      BHPPriorityQueue<Integer,Integer> bhpque = new BHPPriorityQueue<>();
      BHPPriorityQueue<Integer,Integer> bhpque2 = new BHPPriorityQueue<>();

      bhpque.enqueue(1,1);
      bhpque.enqueue(2,2);
      bhpque.enqueue(4,4);
      bhpque.enqueue(3,3);
      bhpque.enqueue(2,2);
      bhpque.enqueue(3,3);
      bhpque.dequeueMin();
      bhpque2.enqueue(3,3);


        for (Entry<Integer,Integer> i: bhpque.getQueue())
        {
            System.out.println(i.getKey());
        }

  }
}
