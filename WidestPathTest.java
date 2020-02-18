import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class WidestPathTest {
 
    
    @Test(expected = IllegalArgumentException.class)
    public void testWidestPathNull() {
        WidestPath.getWidestPath(null, 0, 7);
    } 
    
    @Test(expected = IllegalArgumentException.class)
    public void testWidestPathNull2() {
        Graph g = new Graph(8);
        WidestPath.getWidestPath(g, 9, 0);
    } 
    
    @Test(expected = IllegalArgumentException.class)
    public void testWidestPathNull3() {
        Graph g = new Graph(8);
        WidestPath.getWidestPath(g, 0, 9);
    } 
    
    @Test
    public void testWidestPath() {
        Graph g = new Graph(8);
        g.addEdge(0, 1, 100);
        g.addEdge(0, 2, 10);
        g.addEdge(0, 3, 5); 
        g.addEdge(1, 4, 1);
        g.addEdge(2, 5, 8);
        g.addEdge(3, 6, 5);
        g.addEdge(4, 7, 100);
        g.addEdge(5, 7, 6);
        g.addEdge(0, 7, 5);
        LinkedList<Integer> path = new LinkedList<Integer>();
        path.add(0);
        path.add(2);
        path.add(5);
        path.add(7);
        assertEquals(path, WidestPath.getWidestPath(g, 0, 7));
    }
    
    
    @Test
    public void testWidestPath2() { 
        Graph g = new Graph(6);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 4);
        g.addEdge(1, 3, 10);
        g.addEdge(2, 4, 3);
        g.addEdge(3, 4, 4);
        g.addEdge(3, 5, 11);
        LinkedList<Integer> path = new LinkedList<Integer>();
        path.add(0);
        path.add(2);
        path.add(4);
        path.add(3);
        path.add(5);
        assertEquals(path, WidestPath.getWidestPath(g, 0, 5));
    }
    
    @Test
    public void testWidestPathNoPath() { 
        Graph g = new Graph(6);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 4);
        LinkedList<Integer> path = new LinkedList<Integer>();
        assertEquals(path, WidestPath.getWidestPath(g, 0, 5));
    }
    
    @Test
    public void testWidestPath3() { 
        Graph g = new Graph(7);
        g.addEdge(0, 1, 15);
        g.addEdge(0, 2, 53);
        g.addEdge(1, 2, 40);
        g.addEdge(1, 3, 46);
        g.addEdge(2, 4, 31);
        g.addEdge(2, 5, 17);
        g.addEdge(3, 4, 3);
        g.addEdge(3, 6, 11);
        g.addEdge(4, 5, 29);
        g.addEdge(4, 6, 8);
        g.addEdge(5, 6, 40);
        LinkedList<Integer> path = new LinkedList<Integer>();
        path.add(6);
        path.add(5);
        path.add(4);
        path.add(2);
        path.add(1);
        path.add(3);
        assertEquals(path, WidestPath.getWidestPath(g, 6, 3));
    }
    
}
