import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Facade for computing an unweighted shortest path between two vertices in a graph. We represent
 * paths as ordered lists of integers corresponding to vertices.
 *
 */
public final class BFS {
    private BFS() {}

    /**
     * Returns a shortest path from {@code src} to {@code tgt} by executing a breadth-first search.
     * If there are multiple shortest paths, this method may return any one of them. Please note, 
     * you MUST use your ResizingDeque implementation as the BFS queue for this method. 
     * <p/>
     * Do NOT modify this method header.
     *
     * @param g the graph
     * @param src the vertex from which to search
     * @param tgt the vertex to find via {@code src}
     * @return an ordered list of vertices on a shortest path from {@code src} to {@code tgt}, or an
     *         empty list if there is no path from {@code src} to {@code tgt}. The first element
     *         should be {@code src} and the last element should be {@code tgt}. If
     *         {@code src == tgt}, a list containing just that element is returned.
     * @throws IllegalArgumentException if {@code src} or {@code tgt} is not in the graph
     * @throws IllegalArgumentException if the specified graph is null
     */
    public static List<Integer> getShortestPath(Graph g, int src, int tgt) {
        if (g == null) {  
            throw new IllegalArgumentException();
        }
        if (src < 0 || src >= g.getSize() || tgt < 0 || tgt >= g.getSize()) {
            throw new IllegalArgumentException();
        }
        //this is what we will return (can't return a resizing deque as is not correct type)
        LinkedList<Integer> path = new LinkedList<Integer>();
        if (src == tgt) {
            path.add(src);
            return path;
        } else {
            //queue used for BFS
            ResizingDequeImpl<Integer> queue = new ResizingDequeImpl<Integer>();
            queue.addLast(src);
            
            //this will store the order in which we see nodes
            //values represent node we encountered previous (in depth) to key
            HashMap<Integer, Integer> previousNodes = new HashMap<Integer, Integer>();
            
            //iterate through adding neighbors we have not seen before to queue and stoppping
            //once we hit the target
            while (queue.size() >= 1) {
                int curr = queue.pollFirst();
                Set<Integer> neighbors = g.getNeighbors(curr);
                for (Integer i: neighbors) {
                    if (!previousNodes.containsKey(i)) {
                        previousNodes.put(i, curr);
                        queue.addLast(i);
                    }
                    if (i == tgt) {
                        break;
                    }
                }
            }
            
            //if we found the target we will add the path using the previousNodes map
            if (previousNodes.containsKey(tgt)) {
                path.addFirst(tgt); 
                int curr = tgt;
                while (curr != src) {
                    path.addFirst(previousNodes.get(curr));
                    curr = previousNodes.get(curr);
                }
            }
            return path;
        }
    }
}
