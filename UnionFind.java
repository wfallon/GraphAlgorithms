import java.util.NoSuchElementException;

/**
 * A data structure for tracking a set of elements partitioned into disjoint subsets. We can
 * <em>union</em> these subsets together and then <em>find</em> which set an element belongs to. In
 * particular, we can determine when two elements belong to the same set.
 *
 */
public class UnionFind {
    private int size; 
    private int[] id;
    private int[] sz;
    
    /**
     * Initializes a new union-find structure for the specified number of elements.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param n the number of singleton sets with which to start
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public UnionFind(int n) { 
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        id = new int[n];
        sz = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    } 

    /**
     * Joins two sets.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param u a vertex in the first set
     * @param v a vertex in the second set
     * @throws NoSuchElementException if {@code u < 0} or {@code u >= n} or {@code v < 0} o {@code v
     * >= n}
     */
    public void union(int u, int v) {
        if (u < 0 || u >= size) { 
            throw new NoSuchElementException();
        }
        if (v < 0 || v >= size) {
            throw new NoSuchElementException();
        }
        int i = find(u);
        int j = find(v);
        
        if (i == j) {
            return;
        } 
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
        
        
    }

    /**
     * Finds which set a vertex belongs to. We represent a vertex's set by the index of its tree's
     * root node, as discussed in the writeup. If for some {@code v}, {@code find(u) == find(v)},
     * then {@code u} and {@code v} are in the same set.
     * <p/>
     * Do NOT modify this method header.
     *
     * @param u the vertex
     * @return the root of the set to which the input vertex belongs
     * @throws NoSuchElementException if {@code u < 0} or {@code u >= n}
     */
    public int find(int u) {
        if (u < 0 || u >= size) {
            throw new NoSuchElementException();
        }
        while (u != id[u]) {
            id[u] = id[id[u]];
            u = id[u];
        }
        return u;
    }
}
