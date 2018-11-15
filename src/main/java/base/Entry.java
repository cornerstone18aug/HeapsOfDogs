package base;

/**
 * Class for the base.Entry element of base.VCPriorityQueue
 *
 * @author Derrick Park
 */
public class Entry<K extends Comparable,V> {
    K key;
    V value;

    /**
     * Returns the key stored in this entry.
     * @return the entry's key
     */
    public K getKey(){
        return key;
    }

    /**
     * Returns the value stored in this entry.
     * @return the entry's value
     */
    public V getValue(){
        return value;
    }

    public Entry(K k, V v){
        key = k;
        value = v;
    }
}