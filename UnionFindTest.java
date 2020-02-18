import static org.junit.Assert.*;

import org.junit.Test;
import java.util.NoSuchElementException;

public class UnionFindTest {

    
    @Test(expected = IllegalArgumentException.class)
    public void testUnionIllegalInput() {
        UnionFind union = new UnionFind(-1);
    } 
    
    @Test(expected = NoSuchElementException.class)
    public void testUnionIllegalInput2() {
        UnionFind union = new UnionFind(5);
        union.union(6, 4);
    } 
    
    @Test(expected = NoSuchElementException.class)
    public void testUnionIllegalInput3() {
        UnionFind union = new UnionFind(5);
        union.union(4, 6);
    } 
    
    @Test(expected = NoSuchElementException.class)
    public void testFindIllegalInput() {
        UnionFind union = new UnionFind(5);
        union.find(7);
    } 
    
    @Test
    public void testUnion() {
        UnionFind union = new UnionFind(10);
        union.union(4, 3);
        assertEquals(4, union.find(3));
        assertEquals(4, union.find(4));
        union.union(3, 8);
        assertEquals(4, union.find(8));
    }  
    
    @Test
    public void testUnionMoreComplex() {
        UnionFind union = new UnionFind(10);
        union.union(4, 3);
        assertEquals(4, union.find(3));
        assertEquals(4, union.find(4));
        union.union(3, 8);
        assertEquals(4, union.find(8));
        union.union(6, 5); 
        union.union(9, 4);
        union.union(2, 1);
        union.union(5, 0);
        union.union(7, 2);
        union.union(6, 1);
        union.union(7, 3);
        assertEquals(6, union.find(7));
    }  
    
    @Test
    public void testUnionSameSet() {
        UnionFind union = new UnionFind(10);
        union.union(4, 3);
        assertEquals(4, union.find(3));
        assertEquals(4, union.find(4));
        union.union(3, 4);
        assertEquals(4, union.find(3));
        assertEquals(4, union.find(4));
    } 

}
