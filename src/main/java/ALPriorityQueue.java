import base.Entry;
import base.VCPriorityQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * ALPriorityQueue
 *
 * @author Masa, Hao-tse
 */
public class ALPriorityQueue<K extends Comparable, V> implements VCPriorityQueue<K, V> {

  private List<Entry<K, V>> queueList = new ArrayList<>();

  @Override
  public int size() {
    return this.queueList.size();
  }

  @Override
  public boolean isEmpty() {
    return this.queueList.isEmpty();
  }

  @Override
  public Entry<K, V> enqueue(K key, V value) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    Entry<K, V> element = new Entry<>(key, value);
    this.queueList.add(element);

    return element;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Entry<K, V> peek() {
    if (this.queueList.size() == 0) {
      return null;
    }

    Entry<K, V> result = this.queueList.get(0);
    for (Entry<K, V> entry : this.queueList) {
      if (entry.getKey().compareTo(result.getKey()) < 1) {
        result = entry;
      }
    }

    return result;
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
  public ALPriorityQueue<K, V> merge(VCPriorityQueue<K, V> other) {
    ALPriorityQueue<K, V> newQueue = new ALPriorityQueue<>();
    newQueue.queueList.addAll(this.queueList);
    newQueue.queueList.addAll(((ALPriorityQueue) other).queueList);
    return newQueue;
  }

}
