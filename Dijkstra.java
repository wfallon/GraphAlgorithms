
import java.util.LinkedList;

/**
 * Provides access to Dijkstra's algorithm for a weighted, directed graph.
 * 
 */
public class Dijkstra {
    static int[] edgeTo;
    static double[] distTo;
    static BinaryMinHeapImpl<Double, Integer> heap;
    
    private Dijkstra() {}

    /**
     * Computes the shortest path between two nodes in a weighted, directed graph
     *
     * @param G the graph to compute the shortest path on
     * @param src the source node
     * @param tgt the target node
     * @return an Iterable containing the nodes in the path, including the start and end nodes. If
     *         the start and end nodes are the same, or if there is no path from the start node to
     *         the end node, it returns an empty Iterable.
     * @throws IllegalArgumentException if g is null
     * @throws IllegalArgumentException if src is not in g
     * @throws IllegalArgumentException if tgt is not in g
     */
    public static Iterable<Integer> getShortestPath(WDGraph g, int src, int tgt) {
        if (g == null) {
            throw new IllegalArgumentException();
        }
        if (g.getSize() <= src) {
            throw new IllegalArgumentException();
        }
        if (g.getSize() <= tgt) {
            throw new IllegalArgumentException();
        }
        
        edgeTo = new int[g.getSize()]; 
        for (int i = 0; i < g.getSize(); i++) {
            edgeTo[i] = -1;
        }
        distTo = new double[g.getSize()];
        heap = new BinaryMinHeapImpl<Double, Integer>();
        
        for (int i = 0; i < g.getSize(); i++) {
            distTo[i] = Double.MAX_VALUE;
        }
        distTo[src] = 0.0;
        heap.add(0.0, src);
        while (heap.size() > 0) {
            int v = heap.extractMin();
            for (int i: g.outNeighbors(v)) {
                relax(v, i, g.getWeight(v, i));
            }
        }
        
        LinkedList<Integer> path = new LinkedList<Integer>();
        int curr = tgt;
        if (edgeTo[tgt] == -1) {
            return path;
        } else {
            path.add(0, curr);
            while (curr != src) {
                curr = edgeTo[curr];
                path.add(0, curr);
            }
        }
        return path;
    }
    
    private static void relax(int src, int tgt, double weight) {
        if (distTo[tgt] > distTo[src] + weight) {
            distTo[tgt] = distTo[src] + weight;
            edgeTo[tgt] = src;
            if (heap.containsValue(tgt)) {
                heap.decreaseKey(tgt, distTo[tgt]);
            } else {
                heap.add(distTo[tgt], tgt);
            }
        }
    }

}
