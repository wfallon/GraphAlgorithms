import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class ConnectedComponentsTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void testConnectedComponentsNullInput() {
        ConnectedComponents.stronglyConnectedComponents(null);
    } 

    @Test
    public void testConnectedComponentsSimple() {
        WDGraph g = new WDGraph(5);
        g.addEdge(0, 1, 0);
        g.addEdge(1, 2, 0); 
        g.addEdge(2, 0, 0);
        g.addEdge(3, 4, 0);
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(0);
        set.add(1);
        set.add(2);
        HashSet<Integer> set2 = new HashSet<Integer>();
        set2.add(3);
        HashSet<Integer> set3 = new HashSet<Integer>();
        set3.add(4);
        HashSet<HashSet<Integer>> all = new HashSet<HashSet<Integer>>();
        all.add(set);
        all.add(set2);
        all.add(set3);
        assertEquals(all, ConnectedComponents.stronglyConnectedComponents(g));
    }
    
    
     
    @Test
    public void testConnectedComponentsNoEdges() {
        WDGraph g = new WDGraph(3);
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(0);
        HashSet<Integer> set2 = new HashSet<Integer>();
        set2.add(1);
        HashSet<Integer> set3 = new HashSet<Integer>();
        set3.add(2);
        HashSet<HashSet<Integer>> all = new HashSet<HashSet<Integer>>();
        all.add(set);
        all.add(set2);
        all.add(set3);
        assertEquals(all, ConnectedComponents.stronglyConnectedComponents(g));
    }
    
    

}
