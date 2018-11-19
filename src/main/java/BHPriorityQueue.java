import base.Entry;
import base.VCPriorityQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * BHPriorityQueue
 *
 * @author Masa, Hao-tse
 */
public class BHPriorityQueue<K extends Comparable, V> implements VCPriorityQueue<K, V> {

  private List<Entry<K, V>> heapList = new ArrayList<>();

  private enum CompResult {
    EQUAL(0), BIGGER(1), SMALLER(-1);

    CompResult(int num) {
      this.num = num;
    }

    public int num;

    public static CompResult find(int compareToNum) {
      return Arrays.stream(values())
          .filter(compResult -> compResult.num == compareToNum)
          .findFirst().get();
    }
  }

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

    // Create new Entry and add at the bottom
    Entry<K, V> newEntry = new Entry<>(key, value);
    this.heapList.add(newEntry);

    // Heapify up
    int currentIndex;
    int parentIndex;
    int adjacentIndex;
    Entry<K, V> parentEntry;
    Entry<K, V> adjacentEntry;

    ALL_DONE:
    while (!isRoot(newEntry)) {
      currentIndex = this.getCurrentIndex(newEntry);
      // Get parent
      parentIndex = this.getParentIndex(currentIndex);
      parentEntry = this.getEntry(parentIndex);

      // If it resides left or right
      if (isLeft(newEntry)) {
        switch (this.compare(parentEntry, newEntry)) {
          case EQUAL:
          case BIGGER:
            this.swap(parentEntry, newEntry);
            break;
          case SMALLER:
            break ALL_DONE;
        }
      } else {
        // Get adjacent
        adjacentIndex = this.getAdjacentIndex(currentIndex);
        adjacentEntry = this.getEntry(adjacentIndex);
        switch (this.compare(adjacentEntry, newEntry)) {
          case EQUAL:
          case SMALLER:
            break ALL_DONE;
          case BIGGER:
            switch (this.compare(parentEntry, newEntry)) {
              case EQUAL:
              case SMALLER:
                break ALL_DONE;
              case BIGGER:
                this.swap(parentEntry, newEntry);
                break;
            }
            break;
        }
      }
    }

    return newEntry;
  }

  @Override
  public Entry<K, V> peek() {
    if (this.isEmpty()) {
      return null;
    }
    return this.heapList.get(0);
  }

  @Override
  public Entry<K, V> dequeueMin() {
    if (this.isEmpty()) {
      return null;
    }

    // Swap bottom and top
    Entry<K, V> resultEntry = this.getEntry(0);
    Entry<K, V> rootEntry = this.getEntry(this.heapList.size() - 1);
    this.heapList.remove(rootEntry);
    // When isEmpty, List.set() causes Exception
    if (this.isEmpty()) {
      return resultEntry;
    } else {
      this.heapList.set(0, rootEntry);
    }

    // Heapify down
    int currentIndex;
    int leftChildIndex;
    int rightChildIndex;
    Entry<K, V> leftChildEntry;
    Entry<K, V> rightChildEntry;

    while (true) {
      currentIndex = this.getCurrentIndex(rootEntry);
      // Get left child
      leftChildIndex = this.getLeftChildIndex(currentIndex);
      leftChildEntry = this.getEntry(leftChildIndex);
      // Get right child
      rightChildIndex = this.getRightChildIndex(currentIndex);
      rightChildEntry = this.getEntry(rightChildIndex);

      // If children were null
      if (leftChildEntry == null && rightChildEntry == null) {
        break;
      } else if (leftChildEntry == null || rightChildEntry == null) {
        Entry<K, V> comparingChild;
        if (leftChildEntry != null) {
          comparingChild = leftChildEntry;
        } else {
          comparingChild = rightChildEntry;
        }
        if (this.compare(comparingChild, rootEntry) == CompResult.SMALLER) {
          this.swap(comparingChild, rootEntry);
        }
        break;
      }

      // Swap parent and either left child  or right child
      switch (this.compare(leftChildEntry, rightChildEntry)) {
        case BIGGER:
        case EQUAL:
          this.swap(rightChildEntry, rootEntry);
          break;
        case SMALLER:
          this.swap(leftChildEntry, rootEntry);
          break;
      }

    }

    return resultEntry;
  }

  @Override
  public BHPriorityQueue<K, V> merge(VCPriorityQueue<K, V> other) {
    while (!other.isEmpty()) {
      Entry<K, V> entry = other.dequeueMin();
      this.enqueue(entry.getKey(), entry.getValue());
    }
    return this;
  }

  private boolean isLeft(Entry<K, V> entry) {
    return this.getCurrentIndex(entry) % 2 == 1;
  }

  private boolean isRoot(Entry<K, V> entry) {
    return this.getCurrentIndex(entry) == 0;
  }

  private int getCurrentIndex(Entry<K, V> entry) {
    return this.heapList.indexOf(entry);
  }

  private int getParentIndex(int currentIndex) {
    if (this.isLeft(this.heapList.get(currentIndex))) {
      return (currentIndex - 1) / 2;
    } else {
      return (currentIndex / 2) - 1;
    }
  }

  private int getAdjacentIndex(int currentIndex) {
    if (this.isLeft(this.heapList.get(currentIndex))) {
      return currentIndex + 1;
    } else {
      return currentIndex - 1;
    }
  }

  private int getLeftChildIndex(int currentIndex) {
    return (currentIndex * 2) + 1;
  }

  private int getRightChildIndex(int currentIndex) {
    return (currentIndex + 1) * 2;
  }

  private CompResult compare(Entry<K, V> e1, Entry<K, V> e2) {
    return CompResult.find(e1.getKey().compareTo(e2.getKey()));
  }

  private void swap(Entry<K, V> e1, Entry<K, V> e2) {
    int swapIndex = this.getCurrentIndex(e2);
    this.heapList.set(getCurrentIndex(e1), e2);
    this.heapList.set(swapIndex, e1);
  }

  private Entry<K, V> getEntry(int index) {
    Entry<K, V> result = null;

    if (this.heapList.size() > index) {
      result = this.heapList.get(index);
    }

    return result;
  }

  /**
   * Output all entries into console
   * @param msg description
   */
  public void print(String msg){
    System.out.println("===============" + msg);
    for (Entry<K, V> entry: this.heapList){
      System.out.println(entry.getKey());
    }
    System.out.println("===============" + msg);
  }
}
