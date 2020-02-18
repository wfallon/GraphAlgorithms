import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Contains methods to run DFS on a {@link WDGraph}.
 *
 * Iterative implementations of DFS MUST use your ResizingDeque as the DFS stack. 
 *
 */
public final class DFS {
    private DFS() {}

    /**
     * Runs depth-first search on the input graph {@code g} and returns the list of nodes explored
     * in reverse order of node finishing time. For the purposes of testing, we also ask you to
     * adhere to the following constraints:
     * <ul>
     * <li>When visiting a node's neighbors, the neighbors should be visited in increasing order.
     * You may use {@link java.util.Collections#sort(List)} for this.</li>
     * <li>If DFS finishes on the source/root node but not the entire graph has been explored, DFS
     * should start again on the smallest node that hasn't yet been visited.</li>
     * </ul>
     * Do NOT modify this method header.
     *
     * @param g the graph
     * @param src the vertex from which to begin search
     * @return a list containing all vertices of the graph in reverse order of finish time
     * @throws IllegalArgumentException if {@code src} is not in the graph
     * @throws IllegalArgumentException if the specified graph is null
     */
    public static List<Integer> dfsReverseFinishingTime(WDGraph g, int src) {
        if (g == null) {
            throw new IllegalArgumentException();
        } 
        if (g.getSize() <= src) {
            throw new IllegalArgumentException();
        }
        
        int curr = src;
        List<Integer> path = new LinkedList<Integer>();
        boolean[] visited = new boolean[g.getSize()];
        while (path.size() < g.getSize()) {
            dfsReverseHelper(g, curr, visited, path);
            if (path.size() < g.getSize()) {
                curr = getLowestNotVisited(visited);
            }
        }
        return path;
        
    }
    
    static int getLowestNotVisited(boolean[] visited) {
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                return i;
            }
        }
        return -1;
    }
    
    static List<Integer> dfsReverseHelper(WDGraph g, int curr, boolean[] visited, 
            List<Integer> path) {
        visited[curr] = true;
        for (Integer i: g.outNeighbors(curr)) {
            if (!visited[i]) {
                path = dfsReverseHelper(g, i, visited, path);
            }
        }
        path.add(0, curr);
        return path;
    }

    /**
     * Runs depth-first search on the input graph {@code g} and returns the set of nodes reachable
     * from {@code src}.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param g the graph
     * @param src the vertex from which to begin search
     * @return a set containing all vertices reachable from {@code src}, including {@code src}
     * @throws IllegalArgumentException if {@code src} is not in the graph
     * @throws IllegalArgumentException if the specified graph is null
     */
    public static Set<Integer> dfsExploreComponent(WDGraph g, int src) {
        if (g == null) {
            throw new IllegalArgumentException();
        }
        if (g.getSize() <= src) {
            throw new IllegalArgumentException();
        } 
        Set<Integer> nodes = new HashSet<Integer>();
        boolean[] visited = new boolean[g.getSize()];
        return dfsExploreHelper(g, src, visited, nodes);
    }
    
    static Set<Integer> dfsExploreHelper(WDGraph g, int curr, boolean[] visited, 
            Set<Integer> nodes) {
        nodes.add(curr);
        visited[curr] = true;
        for (Integer i: g.outNeighbors(curr)) {
            if (!nodes.contains(i)) {
                nodes = dfsExploreHelper(g, i, visited, nodes);
            }
        }
        return nodes;
    }
}
