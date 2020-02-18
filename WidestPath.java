import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Returns a widest path between two vertices in a graph. A widest path between two vertices
 * maximizes the weight of the minimum-weight edge in the path.
 * <p/>
 * There are multiple ways to solve this problem, but consider how you can do it with what you've
 * already built---BFS and Kruskal's.
 *
 */
public final class WidestPath {
    private WidestPath() {}

    /**
     * Computes a widest path from {@code src} to {@code tgt} for a specified graph. If there are
     * multiple widest paths, this method may return any one of them.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param g the graph
     * @param src the vertex from which to start the search
     * @param tgt the vertex to find via {@code src}
     * @return an ordered list of vertices on a widest path from {@code src} to {@code tgt}, or an
     *         empty list if there is no such path. The first element is {@code src} and the last
     *         element is {@code tgt}. If {@code src == tgt}, a list containing just that element is
     *         returned.
     * @throws IllegalArgumentException if {@code src} or {@code tgt} is not in the graph
     * @throws IllegalArgumentException if the specified graph is null
     */
    public static List<Integer> getWidestPath(Graph g, int src, int tgt) {
        if (g == null) {
            throw new IllegalArgumentException();
        }
        if (src < 0 || src >= g.getSize()) { 
            throw new IllegalArgumentException();
        }
        if (tgt < 0 || tgt >= g.getSize()) {
            throw new IllegalArgumentException();
        }
        
        Graph negativeWeights = new Graph(g.getSize());
        
        for (int i = 0; i < g.getSize(); i++) {
            Set<Integer> neighbors = g.getNeighbors(i);
            for (Integer j: neighbors) {
                if (!negativeWeights.hasEdge(i, j)) {
                    negativeWeights.addEdge(i, j, g.getWeight(i,j) * -1);
                }
            }
        }
        Graph maxTree = Kruskal.getMST(negativeWeights);
        return BFS.getShortestPath(maxTree, src, tgt);
    }
}
