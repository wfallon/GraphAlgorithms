import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 * @param <V> {@inheritDoc}
 * @param <Key> {@inheritDoc}
 *
 */
public class BinaryMinHeapImpl<Key extends Comparable<Key>, V> implements BinaryMinHeap<Key, V> {

    private ArrayList<Entry <Key, V>> heap;
    private HashMap<V, Integer> values;
    
    public BinaryMinHeapImpl() {
        heap = new ArrayList<Entry<Key, V>>();
        values = new HashMap<V, Integer>(); 
        heap.add(null); 
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return heap.size() - 1; 
    }

    @Override
    public boolean isEmpty() {
        return heap.size() == 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(V value) {
        return values.containsKey(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Key key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        } else if (this.containsValue(value)) {
            throw new IllegalArgumentException();
        }
        heap.add(new Entry<Key, V>(key, value));
        int childIndex = heap.size() - 1;
        values.put(value, childIndex);
        
        for (int i = childIndex; i > 1; i = Math.floorDiv(i, 2)) {
            int parentIndex = Math.floorDiv(i, 2);
            if (heap.get(i).getKey().compareTo(heap.get(parentIndex).getKey()) >= 0) {
                values.put(value, i);
                break;
            } else {
                Entry<Key, V> temp = heap.get(i);
                heap.set(i, heap.get(parentIndex));
                values.put(heap.get(parentIndex).getValue(), i);
                heap.set(parentIndex, temp);
                values.put(value, parentIndex);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return heap.get(1).getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V extractMin() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        V min = heap.get(1).getValue();
        values.remove(heap.get(1).getValue());
        
        heap.set(1, heap.get(heap.size() - 1));
        values.put(heap.get(1).getValue(), 1);
        
        heap.remove(heap.size() - 1);
        
        int index = 1;
        while (index < heap.size()) {
            if (2 * index < heap.size() 
                    && heap.get(index).getKey().compareTo(heap.get(2 * index).getKey()) > 0) {
                if ((2 * index) + 1 < heap.size() 
                        && heap.get(2 * index + 1).getKey().compareTo(heap.get(2 * index).getKey())
                        < 0) { 
                    Entry<Key, V> temp = heap.get(index); 
                    heap.set(index, heap.get((2 * index) + 1));
                    values.put(heap.get(index).getValue(), index);
                    heap.set((2 * index) + 1, temp);
                    values.put(heap.get((2 * index) + 1).getValue(), (2 * index) + 1);
                    index = (2 * index) + 1;
                } else { 
                    Entry<Key, V> temp = heap.get(index); 
                    heap.set(index, heap.get((2 * index)));
                    values.put(heap.get(index).getValue(), index);
                    heap.set((2 * index), temp);
                    values.put(heap.get((2 * index)).getValue(), (2 * index));
                    index = (2 * index);
                }
            } else if (2 * index + 1 < heap.size() 
                    && heap.get(index).getKey().compareTo(heap.get(2 * index + 1).getKey()) > 0) {
                Entry<Key, V> temp = heap.get(index); 
                heap.set(index, heap.get((2 * index) + 1));
                values.put(heap.get(index).getValue(), index);
                heap.set((2 * index) + 1, temp);
                values.put(heap.get((2 * index) + 1).getValue(), (2 * index) + 1);
                index = (2 * index) + 1;
            } else {
                break;
            }
        }
        return min;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseKey(V value, Key newKey) {
        if (!this.containsValue(value)) {
            throw new NoSuchElementException();
        } else if (newKey == null || 
                newKey.compareTo(heap.get(values.get(value)).getKey()) > 0) {
            throw new IllegalArgumentException();
        }
        int index = values.get(value);
        Entry<Key, V> tempEntry = heap.get(index);
        tempEntry.setKey(newKey);
        heap.set(index, tempEntry);
        
        for (int i = index; i > 1; i = Math.floorDiv(i, 2)) {
            int parentIndex = Math.floorDiv(i, 2);
            if (heap.get(i).getKey().compareTo(heap.get(parentIndex).getKey()) >= 0) {
                values.put(value, i);
                break;
            } else {
                Entry<Key, V> temp = heap.get(i);
                heap.set(i, heap.get(parentIndex));
                values.put(heap.get(parentIndex).getValue(), i);
                heap.set(parentIndex, temp);
                values.put(value, parentIndex);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<V> values() {
        Set<V> set = new HashSet<V>();
        for (int i = 1; i < heap.size(); i++) {
            set.add(heap.get(i).getValue());
        }
        return set;
    }
    
    /**
     * Helper entry class for maintaining value-key pairs.
     * The underlying indexed list for your heap will contain
     * these entries.
     *
     * You are not required to use this, but we recommend it.
     */
    class Entry<A, B> {

        private A key;
        private B value;

        public Entry(A key, B value) {
            this.value = value;
            this.key = key;
        }

        /**
         * @return  the value stored in the entry
         */
        B getValue() {
            return this.value;
        }
 
        /**
         * @return  the key stored in the entry
         */
        A getKey() {
            return this.key;
        }

        /**
         * Changes the key of the entry.
         *
         * @param key  the new key
         * @return  the old key
         */
        A setKey(A key) {
            A oldKey = this.key;
            this.key = key;
            return oldKey;
        }

    }

}