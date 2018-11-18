import base.Entry;

public class Driver {
  public static void main(String[] args) {
    BHPriorityQueue<Integer, Integer> bhQueue = new BHPriorityQueue<>();


    bhQueue.enqueue(15, null);
    bhQueue.enqueue(2, null);
    bhQueue.enqueue(4, null);
    bhQueue.enqueue(3, null);
    bhQueue.enqueue(2, null);
    bhQueue.enqueue(14, null);
    bhQueue.enqueue(7, null);
    bhQueue.enqueue(3, null);
    bhQueue.enqueue(9, null);
    bhQueue.enqueue(6, null);
    bhQueue.enqueue(5, null);
    bhQueue.enqueue(8, null);
    bhQueue.enqueue(13, null);
    bhQueue.enqueue(1, null);
    bhQueue.enqueue(12, null);
    bhQueue.enqueue(11, null);
    bhQueue.enqueue(11, null);
    bhQueue.print("Enqueue");

    bhQueue.dequeueMin();
    bhQueue.print("1st dequeue");

    bhQueue.dequeueMin();
    bhQueue.print("2nd dequeue");


    BHPriorityQueue<Integer, Integer> margeQueue = new BHPriorityQueue<>();
    margeQueue.enqueue(5, null);
    margeQueue.enqueue(6, null);
    margeQueue.enqueue(1, null);

    bhQueue.merge(margeQueue);
    bhQueue.print("Merge");

  }
}
