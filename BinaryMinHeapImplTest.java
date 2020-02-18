import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.Test;

public class BinaryMinHeapImplTest {
    
    @Test
    public void testIsEmptyNoElements() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        assertTrue(heap.isEmpty());
    }
    
    @Test
    public void testIsEmptyElements() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.add(1, 1);
        assertFalse(heap.isEmpty());
    } 
    
    @Test 
    public void testSize() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.add(1, 1);
        assertEquals(1, heap.size());
        heap.add(2, 2);
        assertEquals(2, heap.size());
    }
    
    @Test
    public void testSizeEmptyHeap() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        assertEquals(0, heap.size());
    }
    
    @Test
    public void testContainsValueEmptyHeap() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        assertFalse(heap.containsValue(1));
    }
    
    @Test
    public void testContainsValueInHeap() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.add(4, 4);
        heap.add(2, 2);
        heap.add(1, 1);
        assertTrue(heap.containsValue(1));
        assertTrue(heap.containsValue(4));
    }
    
    @Test
    public void testContainsValueNotInHeap() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.add(4, 4);
        heap.add(2, 2);
        heap.add(1, 1);
        assertFalse(heap.containsValue(5));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddNullKey() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.add(null, 5);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddValueAlreadyInHeap() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.add(5, 5);
        heap.add(4, 5);
    }
    
    @Test
    public void testAdd() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        assertFalse(heap.containsValue(1));
        heap.add(1, 1);
        assertTrue(heap.containsValue(1));
    }
    
    @Test
    public void testAddProperlyUpdatesStructure() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.add(5, 5);
        heap.add(1, 1);
        assertEquals(Integer.valueOf(1), heap.extractMin());
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testPeekIsEmpty() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.peek();
    }
    
    @Test
    public void testPeek() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.add(5, 5);
        heap.add(4, 4);
        heap.add(1, 1);
        assertEquals(Integer.valueOf(1), heap.peek());
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testExtractMinIsEmpty() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.extractMin();
    }
    
    @Test
    public void testExtractMinSimple() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.add(5, 5);
        heap.add(4, 4);
        heap.add(1, 1);
        assertEquals(Integer.valueOf(1), heap.extractMin());
        assertEquals(Integer.valueOf(4), heap.extractMin());
        assertEquals(Integer.valueOf(5), heap.extractMin());
    }
    
    @Test
    public void testExtractMinComplex() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.add(5, 5);
        heap.add(4, 4);
        heap.add(1, 1);
        heap.add(50, 50);
        heap.add(51, 51);
        heap.add(52, 52);
        heap.add(53, 53);
        heap.add(54, 54);
        heap.add(55, 55);
        heap.decreaseKey(55, 40);
        assertEquals(Integer.valueOf(1), heap.extractMin());
        assertEquals(Integer.valueOf(4), heap.extractMin());
        assertEquals(Integer.valueOf(5), heap.extractMin());
        assertEquals(Integer.valueOf(55), heap.extractMin());
        assertEquals(Integer.valueOf(50), heap.extractMin());
        assertEquals(Integer.valueOf(51), heap.extractMin());
        assertEquals(Integer.valueOf(52), heap.extractMin());
        assertEquals(Integer.valueOf(53), heap.extractMin());
        assertEquals(Integer.valueOf(54), heap.extractMin());
    }
    
    @Test
    public void testValues() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.add(5, 5);
        heap.add(4, 4);
        heap.add(1, 1);
        Set<Integer> vals = heap.values();
        assertTrue(vals.contains(Integer.valueOf(1)));
        assertTrue(vals.contains(Integer.valueOf(4)));
        assertTrue(vals.contains(Integer.valueOf(5)));
        assertFalse(vals.contains(Integer.valueOf(3)));
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testDecreaseNoValue() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.add(5, 5);
        heap.decreaseKey(6, 6);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDecreaseKeyNullKey() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.add(5, 5);
        heap.decreaseKey(5, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDecreaseKeyNotLessThan() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.add(5, 5);
        heap.decreaseKey(5, 6); 
    } 
    
    @Test
    public void testDecreaseKey() {
        BinaryMinHeapImpl<Integer, Integer> heap = new BinaryMinHeapImpl<Integer, Integer>();
        heap.add(5, 5);
        heap.add(4, 4);
        heap.decreaseKey(5, 3);
        assertEquals(Integer.valueOf(5), heap.extractMin());
    }
}
