import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class DLPriorityQueue<K extends Comparable, V> implements VCPriorityQueue<K, V> {

    LinkedList<Entry<K,V>> queue;

    public LinkedList<Entry<K, V>> getQueue() {
        return queue;
    }

    public DLPriorityQueue() {
        queue = new LinkedList<>();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public Entry<K, V> enqueue(K key, V value) throws IllegalArgumentException {
        Entry<K,V> newEntry = new Entry<>(key, value);
        queue.add(newEntry);
        queue.sort(Comparator.comparing(Entry::getKey));
        return newEntry;
    }

    @Override
    public Entry<K, V> peek() {
        return queue.getFirst();
    }

    @Override
    public Entry<K, V> dequeueMin()
    {
        Entry<K,V> min = queue.getFirst();
        queue.remove(min);
        return min;
    }

    @Override
    public VCPriorityQueue<K, V> merge(VCPriorityQueue<K, V> other) {
        LinkedList<Entry<K, V>> otherqueue = ((DLPriorityQueue)other).getQueue();

        for (Entry<K,V> entry: otherqueue) {
            queue.add(entry);
        }

        queue.sort(Comparator.comparing(Entry::getKey));

        return this;
    }
}
