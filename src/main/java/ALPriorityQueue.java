import java.util.ArrayList;

public class ALPriorityQueue<K extends Comparable,V> implements VCPriorityQueue<K, V> {

    public ArrayList<Entry<K, V>> getQueue() {
        return queue;
    }

    public ALPriorityQueue() {
        queue = new ArrayList<>();
    }

    ArrayList<Entry<K, V>> queue;


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
        Entry<K, V> newEntry = new Entry<>(key, value);
        queue.add(newEntry);
        return null;
    }

    @Override
    public Entry<K, V> peek() {
        Entry<K, V> min = queue.get(0);
        for (Entry<K, V> entry : queue)
        {
            if(entry.getKey().compareTo(min.getKey()) < 0)
            {
                min = entry;
            }
        }

        return min;
    }

    @Override
    public Entry<K, V> dequeueMin() {
        Entry<K, V> min = peek();
        queue.remove(min);
        return min;
    }

    @Override
    public VCPriorityQueue<K, V> merge(VCPriorityQueue<K, V> other) {
        ArrayList<Entry<K, V>> otherqueue = ((ALPriorityQueue)other).getQueue();

        for (Entry<K,V> entry: otherqueue) {
            queue.add(entry);
        }

        return this;
    }
}
