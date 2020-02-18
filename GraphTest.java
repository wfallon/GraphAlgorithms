import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;

public class GraphTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeVertices() {
        Graph g = new Graph(-1);
    }
    
    @Test
    public void testSize() {
        Graph g = new Graph(5);
        assertEquals(5, g.getSize());
    }
    
    @Test
    public void testHasEdge() {
        Graph g = new Graph(5);
        g.addEdge(1, 2, 0); 
        g.addEdge(1, 1, 2);
        assertTrue(g.hasEdge(1, 2));
        assertTrue(g.hasEdge(2, 1));
        assertTrue(g.hasEdge(1, 1));
        assertFalse(g.hasEdge(0, 1)); 
        assertFalse(g.hasEdge(4, 3));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testHasEdgeIllegalArgument() {
        Graph g = new Graph(5);
        assertTrue(g.hasEdge(1, 7));
    }
    
    @Test
    public void testAddEdge() {
        Graph g = new Graph(5);
        assertFalse(g.hasEdge(1, 1));
        assertTrue(g.addEdge(1, 1, 2));
        assertTrue(g.hasEdge(1, 1));
        assertFalse(g.addEdge(1, 1, 2));
        assertTrue(g.addEdge(1, 1, 3));
        g.addEdge(1, 2, 3);
        assertTrue(g.hasEdge(1, 2));
        assertTrue(g.hasEdge(2, 1));
        g.addEdge(1, 2, 4);
        assertTrue(g.hasEdge(2, 1));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeIllegalArgument() {
        Graph g = new Graph(5);
        g.addEdge(1, 7, 2);
    }
    
    @Test
    public void testGetWeight() {
        Graph g = new Graph(5);
        assertTrue(g.addEdge(1, 1, 2));
        assertEquals(2, g.getWeight(1,1));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetWeightIllegalArgument() {
        Graph g = new Graph(5);
        g.getWeight(1,7);
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testGetWeightNoEdge() {
        Graph g = new Graph(5);
        g.getWeight(1, 1);
    }
    
    @Test
    public void testGetNeighbors() {
        Graph g = new Graph(5);
        g.addEdge(1, 2, 0);
        g.addEdge(1, 3, 4);
        Set<Integer> neigh = g.getNeighbors(1);
        assertTrue(neigh.contains(2));
        assertTrue(neigh.contains(3));
        assertFalse(neigh.contains(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNeighborsIllegalArgument() {
        Graph g = new Graph(5);
        g.getNeighbors(7);
    }
}
