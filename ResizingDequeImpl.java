import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingDequeImpl<E> implements ResizingDeque<E> {

    private E[] array;
    private int head;
    private int tail;
    
    private int size;
    
    @SuppressWarnings("unchecked") 
    public ResizingDequeImpl() {
        array = (E[]) new Object[2];
        head = 0; 
        tail = 0; 
        size = 0;
    }
    
    @SuppressWarnings("unchecked")
    public ResizingDequeImpl(E[] a, int h, int t, int s) {
        head = h;
        tail = t;
        size = s;
        array = (E[]) new Object[a.length];
        for (int i = 0; i < a.length; i++) {
            array[i] = a[i];
        }
    }
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public E[] getArray() {
        return array;
    }
     

    @SuppressWarnings("unchecked")
    @Override
    public void addFirst(E e) {
        if (size == 0) {
            //adding first element
            array[0] = e;
        } else if (size == 1) {
            //adding second element
            array[1] = e;
            head = 1;
        } else if (size == array.length) {
            //need to resize
            E[] newArray = (E[]) new Object[size * 2];
            int pointer = head;
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[pointer];
                pointer++;
                if (pointer >= array.length) {
                    pointer = 0;
                }
            }
            newArray[(size * 2) - 1] = e;
            head = (size * 2) - 1;
            tail = size - 1;
            array = newArray;
        } else {
            //size < array.length so we know that there is empty space 
            //at the index before the head
            if (head == 0) {
                array[array.length - 1] = e;
                head = array.length - 1;
            } else {
                array[head - 1] = e;
                head--;
            }
        }
        size++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addLast(E e) {
        if (size == 0) {
            //adding first element
            array[0] = e;
        } else if (size == 1) {
            //adding second element
            if (head == 1) {
                array[0] = e;
                tail = 0;
            } else {
                array[1] = e;
                tail = 1; 
            }
            
        } else if (size == array.length) {
            //need to resize
            E[] newArray = (E[]) new Object[size * 2];
            int pointer = head;
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[pointer];
                pointer++;
                if (pointer >= array.length) {
                    pointer = 0;
                }
            }
            newArray[size] = e;
            head = 0;
            tail = size;
            array = newArray;
        } else { 
            //size < array.length so we know that there is empty space 
            //at the index in front of tail
            if (tail == array.length - 1) {
                array[0] = e;
                tail = 0;
            } else {
                array[tail + 1] = e;
                tail++;
            }
        }
        size++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E pollFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        
        E value = array[head];
        if (size - 1 < (array.length / 4)) {
            E[] newArray = (E[]) new Object[array.length / 2];
            
            int pointer = head + 1;
            if (head == array.length) {
                pointer = 0;
            }
            for (int i = 0; i < size - 1; i++) {
                newArray[i] = array[pointer];
                if (pointer >= array.length) {
                    pointer = 0;
                }
            }
            head = 0; 
            if (size == 1) {
                tail = 0;
            } else {
                tail = size - 2;
            }
            
            array = newArray;
        } else {
            array[head] = null;
            if (head == array.length - 1) {
                head = 0; 
            } else if (size == 1) {
                head = tail = 0;
            } else if (size == 2) {
                head = tail;
            } else {
                head++;
            }
        }
        size--; 
        return value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E pollLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        E value = array[tail];
        if (size - 1 < (array.length / 4)) {
            E[] newArray = (E[]) new Object[array.length / 2];
            int pointer = head;
            for (int i = 0; i < size - 1; i++) {
                newArray[i] = array[pointer];
                if (pointer >= array.length) {
                    pointer = 0;
                }
            }
            head = 0;
            if (size == 1) {
                tail = 0;
            } else {
                tail = size - 2;
            }
            array = newArray;
        } else {
            array[tail] = null;
            if (tail == 0) {
                if (size == 1) {
                    tail = 0;
                } else if (size == 2) {
                    tail = 1;
                } else {
                    tail = array.length;
                }
            } else {
                tail--;
            }
        }
        size--;
        return value;
    }

    @Override
    public E peekFirst() {
        return array[head];
    }

    @Override
    public E peekLast() {
        return array[tail];
    }

    @Override
    public Iterator<E> iterator() {
        return new ResizingDequeIterator(this.getArray(), this.head, this.tail, this.size);
    }
    
    private class ResizingDequeIterator implements Iterator<E> {
        
        private ResizingDeque<E> iteratorDeque;

        public ResizingDequeIterator(E[] a, int h, int t, int s) {
            iteratorDeque = new ResizingDequeImpl<E>(a, h, t, s);
        }
        
        @Override
        public boolean hasNext() {
            return iteratorDeque.size() != 0;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return iteratorDeque.pollFirst();
        }
        
    }
    
}
