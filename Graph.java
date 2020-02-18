import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;

/**
 * Contains the API necessary for an undirected, (optionally) weighted graph. We use ints to
 * identify vertices (i.e. the vertices are labeled 0 through n-1).
 * <p/>
 * We call the graph "optionally weighted" because it can be used by algorithms that use weights
 * (like Kruskal's) and by algorithms that do not (like BFS). An algorithm like BFS would simply
 * ignore any weights present.
 *
 * This Graph can use at most O(m + n) space. Please DO NOT use adjacency matrices!
 * 
 * Also note that the runtimes given are worst-case runtimes. As a result, you shouldn't be 
 * implementing your graph using a HashMap as the primary data structure for the adjacency list
 * (you may use HashMaps/HashSets in other places as long as you meet the provided runtimes). 
 *
 */
public class Graph {
    
    private int vertices;
    
    private ArrayList<ArrayList<Edge>> adjacenyList;
    
    
    /**
     * Initializes a graph of size {@code n}. All valid vertices in this graph thus have integer
     * indices in the half-open range {@code [0, n)}.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param n the number of vertices in the graph
     * @throws IllegalArgumentException if {@code n} is negative
     * @implSpec This constructor should run in O(n) time.
     */ 
    public Graph(int n) {
        if (n < 0) { 
            throw new IllegalArgumentException();
        }
        vertices = n;
        adjacenyList = new ArrayList<ArrayList<Edge>>(); 
        for (int i = 0; i < n; i++) {
            adjacenyList.add(new ArrayList<Edge>());
        }
    }

    /**
     * Returns the number of vertices in the graph.
     * <p/>
     * Do NOT modify this method header.
     *
     * @return the number of vertices in the graph
     * @implSpec This method should run in O(1) time.
     */
    public int getSize() {
        return vertices;
    }

    /**
     * Determines if an edge exists between two vertices.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param u a vertex
     * @param v a vertex
     * @return {@code true} if the {@code u-v} edge is in this graph
     * @throws IllegalArgumentException if a specified vertex does not exist
     * @implSpec This method should run in O(max(deg(u), deg(v))) time.
     */
    public boolean hasEdge(int u, int v) {
        if (u < 0 || u >= vertices || v < 0 || v >= vertices) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < adjacenyList.get(u).size(); i++) {
            if (adjacenyList.get(u).get(i).getDestination() == v) {
                return true;
            } 
        }
        return false;
    }

    /**
     * Creates an edge between {@code u} and {@code v} if it does not already exist. A call to this
     * method should <em>not</em> modify the edge weight if the {@code u-v} edge already exists.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param u one vertex to connect
     * @param v the other vertex to connect
     * @param weight the edge weight
     * @return {@code true} if the graph changed as a result of this call, false otherwise (i.e. if
     *         the edge is already present)
     * @throws IllegalArgumentException if a specified vertex does not exist
     * @implSpec This method should run in O(max(deg(u), deg(v))) time.
     */
    public boolean addEdge(int u, int v, int weight) {
        if (u < 0 || u >= vertices || v < 0 || v >= vertices) {
            throw new IllegalArgumentException();
        } 
        if (!hasEdge(u,v)) {
            adjacenyList.get(u).add(new Edge(v, weight));
            adjacenyList.get(v).add(new Edge(u, weight));
            return true;
        } else if (getWeight(u,v) != weight) {
            for (int i = 0; i < adjacenyList.get(u).size(); i++) {
                if (adjacenyList.get(u).get(i).getDestination() == v) {
                    adjacenyList.get(u).get(i).setWeight(weight);
                    break;
                }
            }
            if (u != v) {
                for (int i = 0; i < adjacenyList.get(v).size(); i++) {
                    if (adjacenyList.get(v).get(i).getDestination() == u) {
                        adjacenyList.get(v).get(i).setWeight(weight);
                        break;
                    }
                }
            }
            return true;
        }
        return false; 
        
    }

    /**
     * Returns the weight of an edge.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param u a vertex
     * @param v a vertex
     * @return the edge weight of {@code u-v}
     * @throws NoSuchElementException if the {@code u-v} edge does not exist
     * @throws IllegalArgumentException if a specified vertex does not exist
     * @implSpec This method should run in O(max(deg(u), deg(v))) time.
     */
    public int getWeight(int u, int v) {
        if (u < 0 || u >= vertices || v < 0 || v >= vertices) {
            throw new IllegalArgumentException();
        } 
        if (!hasEdge(u,v)) {
            throw new NoSuchElementException();
        }
        int weight = 0;
        for (int i = 0; i < adjacenyList.get(u).size(); i++) {
            if (adjacenyList.get(u).get(i).getDestination() == v) {
                weight = adjacenyList.get(u).get(i).getWeight();
            }
        } 
        return weight;
    }

    /**
     * Returns the neighbors of the specified vertex.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param v the vertex
     * @return all neighbors of the specified vertex or an empty set if there are no neighbors. If
     *         there is a self-loop on v, include v in the neighbor set.
     * @throws IllegalArgumentException if the specified vertex does not exist
     * @implSpec This method should run in expected O(deg(v)) time.
     */
    public Set<Integer> getNeighbors(int v) {
        if (v < 0 || v >= vertices) {
            throw new IllegalArgumentException();
        }
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < adjacenyList.get(v).size(); i++) {
            set.add(adjacenyList.get(v).get(i).getDestination());
        }
        return set;
    }
    
    private class Edge {
        private int destination;
        private int weight;
        
        public Edge(int d, int w) {
            destination = d;
            weight = w;
        } 
        
        public int getDestination() {
            return destination;
        } 
        
        public int getWeight() {
            return weight;
        }
        
        public void setWeight(int w) {
            weight = w;
        }
        
    }
}
