import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class DijkstraTest {

    
    @Test(expected = IllegalArgumentException.class)
    public void testDijkstraNullInput() {
        Dijkstra.getShortestPath(null, 0, 1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDijkstraIllegalSource() {
        WDGraph g = new WDGraph(8);
        Dijkstra.getShortestPath(g, 9, 1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDijkstraIllegalTarget() {
        WDGraph g = new WDGraph(8);
        Dijkstra.getShortestPath(g, 1, 9);
    }
    
    @Test
    public void testDijkstraNoPath() {
        WDGraph g = new WDGraph(8);
        g.addEdge(1, 0, .5);
        Iterable<Integer> iter = Dijkstra.getShortestPath(g, 0, 1);
        Iterator<Integer> iter1 = iter.iterator();
        assertFalse(iter1.hasNext());
    }
    
    @Test
    public void testDijkstraComplex() {
        WDGraph g = new WDGraph(8); 
        g.addEdge(0, 1, 5.0);
        g.addEdge(0, 4, 9.0);
        g.addEdge(0, 7, 8.0);
        g.addEdge(1, 2, 12.0);
        g.addEdge(1, 3, 15.0);
        g.addEdge(1, 7, 4.0);
        g.addEdge(2, 3, 3.0);
        g.addEdge(2, 6, 11.0);
        g.addEdge(3, 6, 9.0);
        g.addEdge(4, 5, 4.0);
        g.addEdge(4, 6, 20.0);
        g.addEdge(4, 7, 5.0);
        g.addEdge(5, 2, 1.0);
        g.addEdge(5, 6, 13.0);
        g.addEdge(7, 5, 6.0);
        g.addEdge(7, 2, 7.0);
        Iterable<Integer> iter = Dijkstra.getShortestPath(g, 0, 2);
        Iterator<Integer> iter1 = iter.iterator();
        assertEquals(Integer.valueOf(0), iter1.next());
        assertEquals(Integer.valueOf(4), iter1.next());
        assertEquals(Integer.valueOf(5), iter1.next());
        assertEquals(Integer.valueOf(2), iter1.next());
    }

}
