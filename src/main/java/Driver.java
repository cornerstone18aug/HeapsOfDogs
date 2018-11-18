import base.Entry;

public class Driver {
  public static void main(String[] args) {
    BHPriorityQueue<Integer, Integer> bhQueue = new BHPriorityQueue<>();

    bhQueue.enqueue(1, 1);
    bhQueue.enqueue(2, 2);
    bhQueue.enqueue(4, 4);
    bhQueue.enqueue(3, 3);
    bhQueue.enqueue(2, 2);
    bhQueue.enqueue(3, 3);

    for (Entry<Integer, Integer> entry: bhQueue.heapList){
      System.out.println(entry.getKey());
    }

    System.out.println("===============");

    bhQueue.dequeueMin();

    for (Entry<Integer, Integer> entry: bhQueue.heapList){
      System.out.println(entry.getKey());
    }

    System.out.println("===============");

    bhQueue.dequeueMin();

    for (Entry<Integer, Integer> entry: bhQueue.heapList){
      System.out.println(entry.getKey());
    }

    System.out.println("===============");

    BHPriorityQueue<Integer, Integer> margeQueue = new BHPriorityQueue<>();

    bhQueue.enqueue(5, 1);
    bhQueue.enqueue(6, 2);
    bhQueue.enqueue(1, 4);

    for (Entry<Integer, Integer> entry: bhQueue.merge(margeQueue).heapList){
      System.out.println(entry.getKey());
    }

  }
}
