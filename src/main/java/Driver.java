public class Driver {
  public static void main(String[] args) {
    ALPriorityQueue<Integer,Integer> alque = new ALPriorityQueue<>();


    alque.enqueue(1,5);
    alque.enqueue(2,2);
    alque.enqueue(3,1);

    System.out.println(alque.peek().getValue());

  }
}
