import static org.junit.Assert.*;

import org.junit.Test;

public class IntListTest {

    /**
     * Example test that verifies correctness of the IntList.of static
     * method. The main point of this is to convince you that
     * assertEquals knows how to handle IntLists just fine.
     */

    @Test
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.of(3, 2, 1);
        assertEquals(threeTwoOne, x);
    }

    @Test
    public void testdSquareList() {
        IntList L = IntList.of(1, 2, 3);
        IntList.dSquareList(L);
        assertEquals(IntList.of(1, 4, 9), L);
    }

    /**
     * Do not use the new keyword in your tests. You can create
     * lists using the handy IntList.of method.
     * <p>
     * Make sure to include test cases involving lists of various sizes
     * on both sides of the operation. That includes the empty list, which
     * can be instantiated, for example, with
     * IntList empty = IntList.of().
     * <p>
     * Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     * Anything can happen to A.
     */

    @Test
    public void testSquareListRecursive() {
        IntList L = IntList.of(1, 2, 3);
        IntList res = IntList.squareListRecursive(L);
        assertEquals(IntList.of(1, 2, 3), L);
        assertEquals(IntList.of(1, 4, 9), res);
    }

    @Test
    public void testDcatenate() {
        IntList A = IntList.of(1, 2, 3);
        IntList B = IntList.of(4, 5, 6);
        IntList exp = IntList.of(1, 2, 3, 4, 5, 6);
        assertEquals(exp, IntList.dcatenate(A, B));
        assertEquals(IntList.of(1, 2, 3, 4, 5, 6), A);
    }

    @Test
    public void testCatenate() {
        IntList A = IntList.of(1, 2, 3);
        IntList B = IntList.of(4, 5, 6);
        IntList exp = IntList.of(1, 2, 3, 4, 5, 6);
        assertEquals(exp, IntList.catenate(A, B));
        assertEquals(IntList.of(1, 2, 3), A);
    }

    @Test(timeout = 1000)
    public void testReverse() {
        IntList empty = IntList.of();
        IntList A = IntList.of(1, 4, 9);
        IntList B = IntList.of(9, 4, 1);
        IntList C = IntList.of(1, 4, 9, 16);
        IntList D = IntList.of(16, 9, 4, 1);
        IntList bBackup = IntList.of(9, 4, 1);

        assertEquals(B, IntList.reverse(A));
        assertEquals(D, IntList.reverse(C));
        assertNotEquals(bBackup, IntList.reverse(B));
        assertEquals(empty, IntList.reverse(empty));
    }

    @Test
    public void testGetCorrespondItemFirst() {
        IntList A = IntList.of(1, 4, 9);
        assertEquals(9, IntList.getCorrespondItemFirst(A, 0, 1));
    }

    /** If you're running this from the command line, you'll need
      * to add a main method. See ArithmeticTest.java for an
      * example. */

}
