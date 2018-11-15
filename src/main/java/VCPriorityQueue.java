/**
 * Interface for the priority queue ADT(Abstract Data Type).
 *
 * @author Derrick Park
 */
public interface VCPriorityQueue<K extends Comparable, V> {
    /**
     * Returns the number of items in the priority queue.
     * @return number of items
     */
    int size();

    /**
     * Tests whether the priority queue is empty.
     * @return true if the priority queue is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Inserts a key-value pair and returns the entry created.
     * @param key     the key of the new entry
     * @param value   the associated value of the new entry
     * @return the entry storing the new key-value pair
     * @throws IllegalArgumentException if the key is unacceptable for this queue
     */
    Entry<K,V> enqueue(K key, V value) throws IllegalArgumentException;

    /**
     * Returns (but does not remove) an entry with minimal key.
     * @return entry having a minimal key (or null if empty)
     */
    Entry<K,V> peek();

    /**
     * Removes and returns an entry with minimal key.
     * @return the removed entry (or null if empty)
     */
    Entry<K,V> dequeueMin();


    /**
     * Merges another priority queue of the same type.
     * @return the merged priority queue
     */
    VCPriorityQueue<K, V> merge(VCPriorityQueue<K, V> other);
}
