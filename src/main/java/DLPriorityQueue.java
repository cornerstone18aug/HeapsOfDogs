import base.Entry;
import base.VCPriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * DLPriorityQueue
 *
 * @author Masa, Hao-tse
 */
public class DLPriorityQueue<K extends Comparable, V> implements VCPriorityQueue<K, V> {

  private List<Entry<K, V>> queueList = new LinkedList<>();

  @Override
  public int size() {
    return this.queueList.size();
  }

  @Override
  public boolean isEmpty() {
    return this.queueList.isEmpty();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Entry<K, V> enqueue(K key, V value) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    Entry<K, V> element = new Entry<>(key, value);
    this.queueList.add(element);

    this.queueList.sort(Comparator.comparing(Entry::getKey));

    return element;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Entry<K, V> peek() {
    if (this.queueList.size() == 0) {
      return null;
    }

    return this.queueList.get(0);
  }

  @Override
  public Entry<K, V> dequeueMin() {
    if (this.queueList.size() == 0) {
      return null;
    }
    Entry<K, V> result = this.peek();
    this.queueList.remove(result);
    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  public DLPriorityQueue<K, V> merge(VCPriorityQueue<K, V> other) {
    DLPriorityQueue<K, V> newQueue = new DLPriorityQueue<>();

    newQueue.queueList.addAll(this.queueList);
    newQueue.queueList.addAll(((DLPriorityQueue) other).queueList);

    newQueue.queueList.sort(Comparator.comparing(Entry::getKey));

    return newQueue;
  }

}
