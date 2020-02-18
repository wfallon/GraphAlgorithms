import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class MazeGraphTest {

    @Test
    public void test() {
        int[][] arr = {
                {0,0,1},
                {1,0,1},
                {1,0,0}
        };
        MazeGraph m = new MazeGraph(arr);
        List<Coordinate> list = m.getShortestPath(new Coordinate(0,0), new Coordinate (2,2));
        for (Coordinate c: list) {
            System.out.println("x: " + c.getX() + ", y: " + c.getY());
        }
    }

}
