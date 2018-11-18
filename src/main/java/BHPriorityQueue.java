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

  List<Entry<K, V>> heapList = new ArrayList<>();

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

    // Create new Entry
    Entry<K, V> newEntry = new Entry<>(key, value);
    this.heapList.add(newEntry);

    if (this.heapList.size() == 1) {
      // First element
      return newEntry;
    }

    int currentIndex;
    int parentIndex;
    int adjacentIndex;
    Entry<K, V> parentEntry;
    Entry<K, V> adjacentEntry;

    while (!isRoot(newEntry)) {
      currentIndex = getCurrentIndex(newEntry);
      parentIndex = getParentIndex(currentIndex);
      parentEntry = heapList.get(parentIndex);

      if (isLeft(newEntry)) {
        // Leftmost element
        switch (compare(parentEntry, newEntry)) {
          case EQUAL:
          case BIGGER:
            // Swap with parent
            swap(parentEntry, newEntry);

            break;
          case SMALLER:
            return newEntry;
        }
      } else {
        // Rightmost element
        adjacentIndex = getAdjacentIndex(currentIndex);
        adjacentEntry = heapList.get(adjacentIndex);
        switch (compare(adjacentEntry, newEntry)) {
          // Adjacent was same or smaller
          case EQUAL:
          case SMALLER:
            return newEntry;
          // Adjacent was bigger, compare to parent
          case BIGGER:
            switch (compare(parentEntry, newEntry)) {
              case EQUAL:
              case SMALLER:
                return newEntry;
              case BIGGER:
                // Swap with parent
                swap(parentEntry, newEntry);
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

    // Create new Entry
    int lastIndex= heapList.size() - 1;
    Entry<K, V> dequeueEntry = getEntry(0);
    Entry<K, V> lastEntry = getEntry(lastIndex);
    this.heapList.remove(lastEntry);
    this.heapList.set(0 ,lastEntry);

    int currentIndex;
    int leftChildIndex;
    int rightChildIndex;
    Entry<K, V> leftChildEntry;
    Entry<K, V> rightChildEntry;

    int count = 0;
    while (!isLast(lastEntry) || count == heapList.size()) {
      currentIndex = getCurrentIndex(lastEntry);

      leftChildIndex = getLeftChildIndex(currentIndex);
      leftChildEntry = getEntry(leftChildIndex);
      
      rightChildIndex = getRightChildIndex(currentIndex);
      rightChildEntry = getEntry(rightChildIndex);

      // Either child was null
      if (leftChildEntry == null || rightChildEntry == null) {
        if (leftChildEntry != null) {
          if (compare(leftChildEntry, lastEntry) == CompResult.SMALLER) {
            swap(leftChildEntry, lastEntry);
          }
        } else if (rightChildEntry != null){
          if (compare(rightChildEntry, lastEntry) == CompResult.SMALLER) {
            swap(rightChildEntry, lastEntry);
          }
        }
        return dequeueEntry;
      }

      // Completely sort between 3(self and 2 children)
      switch (compare(leftChildEntry, rightChildEntry)) {
        case BIGGER:
        case EQUAL:
          swap(lastEntry, rightChildEntry);
          break;
        case SMALLER:
          swap(leftChildEntry, lastEntry);
          break;
      }

      ++count;
    }

    return dequeueEntry;
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
    return getCurrentIndex(entry) % 2 == 1;
  }

  private boolean isRight(Entry<K, V> entry) {
    return getCurrentIndex(entry) % 2 == 0;
  }

  private boolean isRoot(Entry<K, V> entry) {
    return getCurrentIndex(entry) == 0;
  }

  private boolean isLast(Entry<K, V> entry) {
    return getCurrentIndex(entry) == this.heapList.size() - 1;
  }

  private int getCurrentIndex(Entry<K, V> entry) {
    return this.heapList.indexOf(entry);
  }

  private int getParentIndex(Entry<K, V> entry) {
    return getParentIndex(getCurrentIndex(entry));
  }

  private int getAdjacentIndex(Entry<K, V> entry) {
    return getAdjacentIndex(getCurrentIndex(entry));
  }

  private int getLeftChildIndex(Entry<K, V> entry) {
    return getLeftChildIndex(getCurrentIndex(entry));
  }

  private int getRightChildIndex(Entry<K, V> entry) {
    return getRightChildIndex(getCurrentIndex(entry));
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
    int swapIndex = getCurrentIndex(e2);
    this.heapList.set(getCurrentIndex(e1), e2);
    this.heapList.set(swapIndex, e1);
  }

  private Entry<K, V> getEntry(int index){
    Entry<K, V> result = null;

    if (this.heapList.size() > index) {
      result = this.heapList.get(index);
    }

    return result;
  }
}
