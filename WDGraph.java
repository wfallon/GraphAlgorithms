import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;


/**
 * Type for a simple, weighted directed graph. By convention, the n vertices will be labeled
 * 0,1,...,n-1. The edge weights can be any double value. Self loops and parallel edges are not
 * allowed. Your implementation should use O(m + n) space.
 *
 * Also note that the runtimes given are worst-case runtimes. As a result, you shouldn't be 
 * implementing your graph using a HashMap as the primary data structure for the adjacency list
 * (you may use HashMaps/HashSets in other places as long as you meet the provided runtimes). 
 *
 */
public class WDGraph {

    private int vertices;
    
    private ArrayList<ArrayList<Edge>> inAdjacenyList;
    private ArrayList<ArrayList<Edge>> outAdjacenyList;
    
    public WDGraph() {} // Do NOT delete/modify this constructor! 
    
    /**
     * Initializes a graph of size {@code n}. All valid vertices in this graph thus have integer
     * indices in the half-open range {@code [0, n)}.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param n the number of vertices in the graph
     * @throws IllegalArgumentException if {@code n} is negative
     * @implSpec This method should run in O(n) time
     */
    public WDGraph(int n) {
        if (n < 0) { 
            throw new IllegalArgumentException();
        }
        vertices = n;
        inAdjacenyList = new ArrayList<ArrayList<Edge>>();
        outAdjacenyList = new ArrayList<ArrayList<Edge>>();
        for (int i = 0; i < n; i++) {
            inAdjacenyList.add(new ArrayList<Edge>());
            outAdjacenyList.add(new ArrayList<Edge>());
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
     * Creates an edge from {@code u} to {@code v} if it does not already exist. A call to this
     * method should <em>not</em> modify the edge weight if the {@code u-v} edge already exists.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param u the source vertex to connect
     * @param v the target vertex to connect
     * @param weight the edge weight
     * @return {@code true} if the graph changed as a result of this call, false otherwise (i.e. if
     *         the edge is already present)
     * @throws IllegalArgumentException if a specified vertex does not exist or if u == v
     * @implSpec This method should run in O(deg(u)) time
     */
    public boolean addEdge(int u, int v, double weight) {
        if (u < 0 || u >= vertices || v < 0 || v >= vertices || u == v) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < outAdjacenyList.get(u).size(); i++) {
            if (outAdjacenyList.get(u).get(i).getDestination() == v) {
                if (outAdjacenyList.get(u).get(i).getWeight() == weight) {
                    return false;
                } else {
                    outAdjacenyList.get(u).get(i).setWeight(weight);
                    for (int j = 0; j < inAdjacenyList.get(v).size(); j++) {
                        if (inAdjacenyList.get(v).get(j).getDestination() == u) {
                            inAdjacenyList.get(v).get(j).setWeight(weight);
                            return true;
                        }
                    }
                }
            }
        }
        outAdjacenyList.get(u).add(new Edge(v, weight));
        inAdjacenyList.get(v).add(new Edge(u, weight));
        return true;
    }

    /**
     * Returns the weight of an edge.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param u source vertex
     * @param v target vertex
     * @return the edge weight of {@code u-v}
     * @throws NoSuchElementException if the {@code u-v} edge does not exist
     * @throws IllegalArgumentException if a specified vertex does not exist
     * @implSpec This method should run in O(deg(u)) time.
     */
    public double getWeight(int u, int v) {
        if (u < 0 || u >= vertices || v < 0 || v >= vertices) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < outAdjacenyList.get(u).size(); i++) {
            if (outAdjacenyList.get(u).get(i).getDestination() == v) {
                return outAdjacenyList.get(u).get(i).getWeight();
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * Returns the out-neighbors of the specified vertex.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param v the vertex
     * @return all out neighbors of the specified vertex or an empty set if there are no out
     *         neighbors
     * @throws IllegalArgumentException if the specified vertex does not exist
     * @implSpec This method should run in O(outdeg(v)) time.
     */
    public Set<Integer> outNeighbors(int v) {
        if (v < 0 || v >= vertices) {
            throw new IllegalArgumentException();
        }
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < outAdjacenyList.get(v).size(); i++) {
            set.add(outAdjacenyList.get(v).get(i).getDestination());
        }
        return set;
    }

    /**
     * Returns the in-neighbors of the specified vertex.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param v the vertex
     * @return all in neighbors of the specified vertex or an empty set if there are no in neighbors
     * @throws IllegalArgumentException if the specified vertex does not exist
     * @implSpec This method should run in O(indeg(v)) time.
     */
    public Set<Integer> inNeighbors(int v) {
        if (v < 0 || v >= vertices) {
            throw new IllegalArgumentException(); 
        }
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < inAdjacenyList.get(v).size(); i++) {
            set.add(inAdjacenyList.get(v).get(i).getDestination());
        }
        return set;
    }
    
    private class Edge {
        private int destination;
        private double weight;
        
        public Edge(int d, double w) {
            destination = d;
            weight = w;
        }
        
        public int getDestination() {
            return destination;
        } 
        
        public double getWeight() {
            return weight;
        }
        
        public void setWeight(double w) {
            weight = w;
        }
        
    }

}
