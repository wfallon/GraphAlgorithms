import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class BFSTest {

    
    @Test(expected = IllegalArgumentException.class)
    public void testNullGraph() {
        BFS.getShortestPath(null, 0, 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArguments() { 
        Graph g = new Graph(8);
        BFS.getShortestPath(g, 0, 8);
    }
    
    @Test
    public void testSameIndex() {
        Graph g = new Graph(8);
        LinkedList<Integer> path = new LinkedList<Integer>();
        path.add(0);
        assertEquals(path, BFS.getShortestPath(g, 0, 0));
    }
    
    @Test
    public void testSimplePath() {
        Graph g = new Graph(8);
        g.addEdge(0, 1, 0);
        g.addEdge(1, 2, 0);
        g.addEdge(0, 3, 0);
        g.addEdge(3, 4, 0);
        g.addEdge(3, 7, 0);
        g.addEdge(4, 7, 0);
        g.addEdge(6, 7, 0);
        g.addEdge(4, 6, 0);
        g.addEdge(4, 5, 0);
        g.addEdge(5, 6, 0);
        LinkedList<Integer> path = new LinkedList<Integer>();
        path.add(0);
        path.add(3);
        path.add(7);
        assertEquals(path, BFS.getShortestPath(g, 0, 7));
    }
    
    @Test
    public void testComplex2Paths() {
        Graph g = new Graph(8);
        g.addEdge(0, 1, 0);
        g.addEdge(0, 2, 0); 
        g.addEdge(0, 3, 0);
        g.addEdge(1, 4, 0);
        g.addEdge(1, 5, 0);
        g.addEdge(2, 4, 0); 
        g.addEdge(2, 5, 0);
        g.addEdge(2, 6, 0);
        g.addEdge(3, 6, 0);
        g.addEdge(4, 7, 0);
        g.addEdge(5, 7, 0);
        g.addEdge(6, 7, 0);
        LinkedList<Integer> path = new LinkedList<Integer>();
        path.add(0);
        path.add(3);
        path.add(6);
        path.add(7);
        LinkedList<Integer> path2 = new LinkedList<Integer>();
        path2.add(0);
        path2.add(1);
        path2.add(4);
        path2.add(7);
        LinkedList<LinkedList<Integer>> paths = new LinkedList<LinkedList<Integer>>();
        paths.add(path);
        paths.add(path2);
        assertTrue(paths.contains(BFS.getShortestPath(g, 0, 7)));
    }
    
    @Test
    public void testBFS2() {
        Graph g = new Graph(8);
        g.addEdge(0, 1, 0);
        g.addEdge(0, 2, 0);
        g.addEdge(0, 3, 0);
        g.addEdge(2, 5, 0);
        g.addEdge(3, 6, 0);
        g.addEdge(4, 7, 0);
        g.addEdge(5, 7, 0);
        LinkedList<Integer> path = new LinkedList<Integer>();
        path.add(0);
        path.add(2);
        path.add(5);
        path.add(7);
        assertEquals(path, BFS.getShortestPath(g, 0, 7));
    }
    
    @Test
    public void testNoSolution() {
        Graph g = new Graph(8);
        g.addEdge(0, 1, 0);
        g.addEdge(0, 3, 0);
        g.addEdge(3, 4, 0);
        g.addEdge(3, 7, 0);
        g.addEdge(4, 7, 0);
        g.addEdge(6, 7, 0);
        g.addEdge(4, 6, 0);
        g.addEdge(4, 5, 0);
        g.addEdge(5, 6, 0);
        LinkedList<Integer> path = new LinkedList<Integer>();
        assertEquals(path, BFS.getShortestPath(g, 7, 2));
    }
}
