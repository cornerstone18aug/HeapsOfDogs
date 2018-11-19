import java.util.ArrayList;

public class BHPPriorityQueue<K extends Comparable, V> implements VCPriorityQueue<K, V> {

    public ArrayList<Entry<K, V>> getQueue() {
        return queue;
    }

    ArrayList<Entry<K,V>> queue;

    public BHPPriorityQueue() {
        queue = new ArrayList<>();
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
        boolean bubble = true;
        int currentIndex = queue.size();
        boolean added = false;
        Entry<K, V> newEntry = new Entry<>(key, value);

        while(bubble)
        {
        if(queue.isEmpty())
        {
            queue.add(newEntry);
            bubble = false;
            added = true;
        }


        if (!added)
        {
            queue.add(newEntry);
            added = true;
        }
        if(bubble) {
            if ((currentIndex + 1) % 2 == 0) {
                currentIndex = queue.size() - 1;
                int parentindex = ((currentIndex + 1) / 2) - 1;
                switch (queue.get(currentIndex).getKey().compareTo(queue.get(parentindex).getKey())) {
                    case 0:
                    case 1:
                        bubble = false;
                        break;
                    case -1:
                        Entry<K, V> temp = queue.get(currentIndex);
                        queue.set(currentIndex, queue.get(parentindex));
                        queue.set(parentindex, temp);
                        currentIndex = parentindex - 1;
                }
            } else if ((currentIndex + 1) % 2 != 0) {
                currentIndex = queue.size() - 1;
                int parentIndex = ((currentIndex) / 2) - 1;
                int previndex = currentIndex - 1;
                switch (queue.get(currentIndex).getKey().compareTo(queue.get(previndex).getKey())) {
                    case 0:
                    case 1:
                        bubble = false;
                        break;
                    case -1:
                        switch (queue.get(currentIndex).getKey().compareTo(queue.get(parentIndex).getKey())) {
                            case 0:
                            case 1:
                                bubble = false;
                                break;
                            case -1:
                                Entry<K, V> temp = queue.get(currentIndex);
                                queue.set(currentIndex, queue.get(parentIndex));
                                queue.set(parentIndex, temp);
                                currentIndex = parentIndex - 1;
                        }
                }


            }
        }
        }
       return newEntry;
    }

    @Override
    public Entry<K, V> peek() {
        return queue.get(0);
    }

    @Override
    public Entry<K, V> dequeueMin() {
        Entry<K, V> newEntry = peek();
        boolean bubble = true;
        queue.remove(0);
        int lastindex = queue.size() -1;
        Entry<K, V> lastEntry = queue.get(lastindex);
        queue.remove(lastindex);
        queue.add(0,lastEntry);
        int currentIndex = 0;

        while(bubble) {
            int childLeft = (currentIndex + 1) * 2 - 1;
            int childRight = childLeft + 1;

            if (childLeft < queue.size() && childRight < queue.size()) {
                switch (queue.get(childLeft).getKey().compareTo(queue.get(childRight).getKey())) {
                    case 0:
                    case -1:
                        switch (queue.get(currentIndex).getKey().compareTo(queue.get(childLeft).getKey())) {
                            case -1:
                            case 0:
                                bubble = false;
                                break;
                            case 1:
                                Entry<K, V> temp = queue.get(currentIndex);
                                queue.set(currentIndex, queue.get(childLeft));
                                queue.set(childLeft, temp);
                                currentIndex = childLeft;
                                break;
                        }
                        break;
                    case 1:
                        switch (queue.get(currentIndex).getKey().compareTo(queue.get(childRight).getKey())) {
                            case -1:
                            case 0:
                                bubble = false;
                                break;
                            case 1:
                                Entry<K, V> temp = queue.get(currentIndex);
                                queue.set(currentIndex, queue.get(childRight));
                                queue.set(childRight, temp);
                                currentIndex = childRight;
                        }
                }
            }
            else if (childLeft >= queue.size()) {
                bubble = false;
                break;
            }
            else if (childRight >= queue.size()) {
                switch (queue.get(currentIndex).getKey().compareTo(queue.get(childLeft).getKey())) {
                    case -1:
                    case 0:
                        bubble = false;
                        break;
                    case 1:
                        Entry<K, V> temp = queue.get(currentIndex);
                        queue.set(currentIndex, queue.get(childLeft));
                        queue.set(childLeft, temp);
                        currentIndex = childLeft;
                        break;
                }
            }
        }
        return newEntry;
    }

    @Override
    public VCPriorityQueue<K, V> merge(VCPriorityQueue<K, V> other) {
        ArrayList<Entry<K,V>> newqueue = ((BHPPriorityQueue)other).getQueue();
        for (Entry<K,V> entry: newqueue)
        {
            this.enqueue(entry.getKey(),entry.getValue());
        }
        return this;
    }
}
