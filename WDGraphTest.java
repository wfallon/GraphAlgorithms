import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;

public class WDGraphTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeVertices() {
        WDGraph g = new WDGraph(-1);
    }
    
    @Test
    public void testSize() {
        WDGraph g = new WDGraph(5);
        assertEquals(5, g.getSize());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeIllegalArgument() {
        WDGraph g = new WDGraph(5);
        g.addEdge(1, 1, 2.0);
    }
    
    @Test
    public void testAddEdge() {
        WDGraph g = new WDGraph(5);
        assertTrue(g.addEdge(1, 2, 2.0));
        assertFalse(g.addEdge(1, 2, 2.0));
        assertTrue(g.addEdge(1, 2, 3.0));
        assertEquals(3.0, g.getWeight(1, 2), .001);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetWeightIllegalArgument() {
        WDGraph g = new WDGraph(5);
        g.getWeight(1, 7);
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testGetWeightNoEdge() {
        WDGraph g = new WDGraph(5);
        g.getWeight(1, 3);
    }
    
    @Test
    public void testGetWeight() {
        WDGraph g = new WDGraph(5);
        g.addEdge(1, 2, 3.0); 
        assertEquals(3.0, g.getWeight(1, 2), .001);
    }
     
    @Test
    public void testOutNeighbors() {
        WDGraph g = new WDGraph(5);
        g.addEdge(1, 2, 3.0);
        g.addEdge(1, 3, 4.0);
        Set<Integer> set = g.outNeighbors(1);
        assertTrue(set.contains(2));
        assertTrue(set.contains(3));
        assertFalse(set.contains(4));
    }
    
    @Test
    public void testInNeighbors() {
        WDGraph g = new WDGraph(5);
        g.addEdge(1, 2, 3.0);
        g.addEdge(1, 3, 4.0);
        Set<Integer> set = g.inNeighbors(2);
        assertTrue(set.contains(1));
        assertFalse(set.contains(4));
    }

}
