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

    // Create new Entry and add
    Entry<K, V> newEntry = new Entry<>(key, value);
    this.heapList.add(newEntry);

    int currentIndex;
    int parentIndex;
    int adjacentIndex;
    Entry<K, V> parentEntry;
    Entry<K, V> adjacentEntry;

    while (!isRoot(newEntry)) {
      currentIndex = this.getCurrentIndex(newEntry);
      // Get parent
      parentIndex = this.getParentIndex(currentIndex);
      parentEntry = this.heapList.get(parentIndex);

      // If it resides left or right
      if (isLeft(newEntry)) {
        switch (this.compare(parentEntry, newEntry)) {
          case EQUAL:
          case BIGGER:
            this.swap(parentEntry, newEntry);
            break;
          case SMALLER:
            return newEntry;
        }
      } else {
        // Get adjacent
        adjacentIndex = this.getAdjacentIndex(currentIndex);
        adjacentEntry = this.heapList.get(adjacentIndex);
        switch (this.compare(adjacentEntry, newEntry)) {
          case EQUAL:
          case SMALLER:
            return newEntry;
          case BIGGER:
            switch (this.compare(parentEntry, newEntry)) {
              case EQUAL:
              case SMALLER:
                return newEntry;
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
    Entry<K, V> bottomEntry = this.getEntry(this.heapList.size() - 1);
    this.heapList.remove(bottomEntry);
    // When isEmpty, List.set() causes Exception
    if (this.isEmpty()) {
      return resultEntry;
    } else {
      this.heapList.set(0, bottomEntry);
    }

    int currentIndex;
    int leftChildIndex;
    int rightChildIndex;
    Entry<K, V> leftChildEntry;
    Entry<K, V> rightChildEntry;

    while (true) {
      currentIndex = this.getCurrentIndex(bottomEntry);
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
        if (this.compare(comparingChild, bottomEntry) == CompResult.SMALLER) {
          this.swap(comparingChild, bottomEntry);
        }
        break;
      }

      // Sort either left or right
      switch (this.compare(leftChildEntry, rightChildEntry)) {
        case BIGGER:
        case EQUAL:
          this.swap(rightChildEntry, bottomEntry);
          break;
        case SMALLER:
          this.swap(leftChildEntry, bottomEntry);
          break;
      }

    }

    return resultEntry;
  }

  @Override
  public BHPriorityQueue<K, V> merge(VCPriorityQueue<K, V> other) {
    while (!other.isEmpty()) {
      Entry<K, V> entry = other.dequeueMin();
      enqueue(entry.getKey(), entry.getValue());
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
    if (isLeft(this.heapList.get(currentIndex))) {
      return (currentIndex - 1) / 2;
    } else {
      return (currentIndex / 2) - 1;
    }
  }

  private int getAdjacentIndex(int currentIndex) {
    if (isLeft(this.heapList.get(currentIndex))) {
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

  public void print(String msg){
    System.out.println("===============" + msg);
    for (Entry<K, V> entry: this.heapList){
      System.out.println(entry.getKey());
    }
    System.out.println("===============" + msg);
  }
}
