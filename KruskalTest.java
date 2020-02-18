import static org.junit.Assert.*;

import org.junit.Test;

public class KruskalTest {

    @Test(expected = IllegalArgumentException.class)
    public void testKruskalIllegalArgument() {
        Graph g = Kruskal.getMST(null);
    } 
    
    @Test
    public void testKruskal() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 4); 
        g.addEdge(0, 3, 3);
        g.addEdge(1, 3, 2);
        g.addEdge(2, 3, 5);
        Graph g2 = Kruskal.getMST(g);
        assertTrue(g2.hasEdge(0, 1));
        assertTrue(g2.hasEdge(0, 2));
        assertTrue(g2.hasEdge(1, 3));
        assertFalse(g2.hasEdge(2, 3));
        assertFalse(g2.hasEdge(0, 3));
    }

}
