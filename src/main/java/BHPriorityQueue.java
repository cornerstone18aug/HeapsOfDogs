import base.Entry;
import base.VCPriorityQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * BHPriorityQueue
 *
 * @author Masa, Hao-tse
 */
public class BHPriorityQueue<K extends Comparable, V> implements VCPriorityQueue<K, V> {

  private List<Entry<K, V>> heapList = new ArrayList<>();

  @Override
  public int size() {
    return heapList.size();
  }

  @Override
  public boolean isEmpty() {
    return heapList.isEmpty();
  }

  @Override
  public Entry<K, V> enqueue(K key, V value) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    Entry<K, V> element = new Entry<>(key, value);

    int newIndex = this.size();

    if (this.isEmpty()) {
      heapList.add(element);
    } else if (this.size() + 1 % 2 == 0) {
      switch (heapList.get(this.size() - 1).getKey()
          .compareTo(element.getKey())) {
        case 0:
        case 1:
          heapList.add(this.size() - 1, element);
          heapList.set
          break;
        case -1:
          heapList.add(element);
          break;
      }
    } else {
      switch (heapList.get(this.size() - 1).getKey()
          .compareTo(element.getKey())) {
        case 0:
        case 1:
          heapList.add(this.size() - 1, element);
          break;
        case -1:
          heapList.add(element);
          break;
      }
    }

    return element;
  }

  @Override
  public Entry<K, V> peek() {
    if (this.heapList.size() == 0) {
      return null;
    }
    return this.heapList.get(0);
  }

  @Override
  public Entry<K, V> dequeueMin() {
    if (this.heapList.size() == 0) {
      return null;
    }

    return null;
  }

  @Override
  public VCPriorityQueue<K, V> merge(VCPriorityQueue<K, V> other) {
    return null;
  }
}
