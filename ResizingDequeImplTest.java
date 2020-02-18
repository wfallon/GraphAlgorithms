import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class ResizingDequeImplTest {

    @Test
    public void testAddFirst() {
        ResizingDequeImpl<Integer> deque = new ResizingDequeImpl<Integer>();
        deque.addFirst(1);
        deque.addFirst(2); 
        deque.addFirst(3);
        assertEquals(Integer.valueOf(3), deque.peekFirst());
        assertEquals(Integer.valueOf(1), deque.peekLast());
    }
    
    @Test
    public void testAddLast() {
        ResizingDequeImpl<Integer> deque = new ResizingDequeImpl<Integer>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertEquals(Integer.valueOf(1), deque.peekFirst());
        assertEquals(Integer.valueOf(3), deque.peekLast());
    }
    
    @Test
    public void testPollFirst() { 
        ResizingDequeImpl<Integer> deque = new ResizingDequeImpl<Integer>();
        
        deque.addLast(1);
        assertEquals(Integer.valueOf(1), deque.pollFirst());
        
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3); 
        deque.addLast(4);
        deque.addLast(5);
        assertEquals(5, deque.size());
        assertEquals(Integer.valueOf(1), deque.pollFirst());
        assertEquals(4, deque.size());
        assertEquals(Integer.valueOf(2), deque.pollFirst());
        assertEquals(3, deque.size());
        assertEquals(Integer.valueOf(3), deque.pollFirst());
        deque.addLast(6);
        assertEquals(Integer.valueOf(4), deque.pollFirst());
        assertEquals(Integer.valueOf(5), deque.pollFirst());
        assertEquals(Integer.valueOf(6), deque.pollFirst());
    }
    
    @Test
    public void testPollLast() {
        ResizingDequeImpl<Integer> deque = new ResizingDequeImpl<Integer>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.addLast(5);
        assertEquals(5, deque.size());
        assertEquals(Integer.valueOf(5), deque.pollLast());
        assertEquals(4, deque.size()); 
        assertEquals(Integer.valueOf(4), deque.pollLast());
        assertEquals(3, deque.size());
        assertEquals(Integer.valueOf(3), deque.pollLast());
        assertEquals(Integer.valueOf(2), deque.pollLast());
        assertEquals(Integer.valueOf(1), deque.pollLast());
    }
    
    @Test
    public void testSize() {
        ResizingDequeImpl<Integer> deque = new ResizingDequeImpl<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        assertEquals(3, deque.size());
    }
    
    @Test
    public void testSize2() { 
        ResizingDequeImpl<Integer> deque = new ResizingDequeImpl<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(4);
        deque.addLast(5);
        assertEquals(5, deque.size());
        deque.pollLast();
        assertEquals(4, deque.size());
        deque.pollFirst();
        assertEquals(3, deque.size());
    }
    
    @Test
    public void testIterator() {
        ResizingDequeImpl<Integer> deque = new ResizingDequeImpl<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(4);
        deque.addLast(5);
        Iterator<Integer> iterator = deque.iterator();
        assertEquals(Integer.valueOf(3), iterator.next());
        assertEquals(Integer.valueOf(2), iterator.next());
        assertEquals(Integer.valueOf(1), iterator.next());
        assertEquals(Integer.valueOf(4), iterator.next());
        assertEquals(Integer.valueOf(5), iterator.next());
        assertFalse(iterator.hasNext());
    }

}
