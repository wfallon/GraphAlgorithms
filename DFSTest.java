import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

public class DFSTest {

    @Test(expected = IllegalArgumentException.class)
    public void testReverseFinishingTimeNullInput() {
        DFS.dfsReverseFinishingTime(null, 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testReverseFinishingTimeIllegalInput() {
        WDGraph g = new WDGraph(5);
        DFS.dfsReverseFinishingTime(g, 5);
    }
    
    @Test
    public void testReverseFinishingTimeNoEdges() {
        WDGraph g = new WDGraph(5);
        List<Integer> path = new ArrayList<Integer>();
        path.add(4);
        path.add(3);
        path.add(1);
        path.add(0);
        path.add(2);
        assertEquals(path, DFS.dfsReverseFinishingTime(g, 2));
    }
    
    @Test
    public void testReverseFinishingTimeSimple() {
        WDGraph g = new WDGraph(5);
        g.addEdge(0, 1, 0);
        g.addEdge(1, 2, 0);
        g.addEdge(3, 4, 0);
        g.addEdge(0, 3, 0);
        List<Integer> path = new ArrayList<Integer>();
        path.add(0);
        path.add(3);
        path.add(4);
        path.add(1);
        path.add(2);
        assertEquals(path, DFS.dfsReverseFinishingTime(g, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExploreComponentNullInput() {
        DFS.dfsExploreComponent(null, 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testExploreComponentIllegalInput() {
        WDGraph g = new WDGraph(5);
        DFS.dfsExploreComponent(g, 5);
    }
    
    @Test
    public void testExploreComponentNoEdges() {
        WDGraph g = new WDGraph(5);
        HashSet<Integer> nodes = new HashSet<Integer>();
        nodes.add(2);
        assertEquals(nodes, DFS.dfsExploreComponent(g, 2));
    } 
    
    @Test
    public void testExploreComponentSimple() {
        WDGraph g = new WDGraph(5);
        g.addEdge(2, 1, 0);
        g.addEdge(3, 2, 0);
        g.addEdge(1, 0, 0);
        g.addEdge(2, 4, 0);
        HashSet<Integer> nodes = new HashSet<Integer>();
        nodes.add(2);
        nodes.add(1);
        nodes.add(0);
        nodes.add(4);
        assertEquals(nodes, DFS.dfsExploreComponent(g, 2));
    }
}
