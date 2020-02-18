import java.util.LinkedList;
import java.util.List;


// CIS 121, HW 6 Extra Credit, MazeGraph
final public class MazeGraph {
    
    private Graph g;
    private int[][] m;
    /**
     * Initialize a MazeGraph with a 2D-array of size m×n.
     * <p/>
     * Input {@code maze} guaranteed to be a non-empty and valid matrix.
     * Do NOT modify this constructor header.
     *
     * @param maze the input maze, which is a 2D-array of size m×n
     * @implSpec This constructor should run in O(m×n) time.
     */
    
    public MazeGraph(int[][] maze) {
        m = maze;
        g = new Graph(maze.length * maze[0].length);
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (i > 0 && maze[i][j] == 0 && maze[i - 1][j] == 0) {
                    System.out.println("hi");
                    g.addEdge(j*maze[0].length + i, j*maze[0].length + i - 1, 0);
                }
                if (j > 0 && maze[i][j] == 0 && maze[i][j - 1] == 0) {
                    System.out.println("hi");
                    g.addEdge(j*maze[0].length + i, j*(maze[0].length - 1) + i, 0);
                }
                if (i < maze.length - 1 && maze[i][j] == 0 && maze[i + 1][j] == 0) {
                    System.out.println("hi");
                    g.addEdge(j*maze[0].length + i, j*maze[0].length + i + 1, 0);
                }
                if (j < maze[0].length - 1 && maze[i][j] == 0 && maze[i][j + 1] == 0) {
                    System.out.println(j*maze[0].length + i);
                    System.out.println(j*(maze[0].length + 1) + i);
                    g.addEdge(j*maze[0].length + i, j*(maze[0].length + 1) + i, 0);
                }
            }
        }
        System.out.println(g.hasEdge(6, 4));
    }

    /**
     * Returns a list of coordinates on the shortest path from {@code src} to {@code tgt}
     * by executing a breadth-first search.
     * Please note, you MUST use your ResizingDeque implementation as the BFS queue for this method.
     * <p/>
     * Input {@code src} and {@code tgt} are guaranteed to be valid, in-bounds, and not blocked.
     * Do NOT modify this constructor header.
     *
     * @param src The starting Coordinate of the path on the matrix
     * @param tgt The target Coordinate of the path on the matrix
     * @return an empty list if there is no path from {@code src} to {@code tgt}, otherwise an ordered list of
     * vertices in the shortest path from {@code src} to {@code tgt}, with the first element being
     * {@code src} and the last element being {@code tgt}.
     * @implSpec This constructor should run in expected worst-case O(m×n) time.
     */
    public List<Coordinate> getShortestPath(Coordinate src, Coordinate tgt) {
        List<Integer> path = BFS.getShortestPath(g, src.getX()*m[0].length + src.getY(), 
                tgt.getX()*m[0].length + tgt.getY());
        List<Coordinate> coordPath = new LinkedList<Coordinate>();
        for(Integer i: path) {
            coordPath.add(new Coordinate(i / m[0].length, i % m[0].length));
        }
        return coordPath;
    }
}
