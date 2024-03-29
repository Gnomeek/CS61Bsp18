import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    StudentArrayDeque<Integer> stu = new StudentArrayDeque<>();
    ArrayDequeSolution<Integer> ans = new ArrayDequeSolution<>();

    @Test
    public void testAddFirst() {

        for (int i = 0; i <= 10; i += 1) {
            int randomNum = StdRandom.uniform(0, 100);
            stu.addFirst(randomNum);
            ans.addFirst(randomNum);
        }

        for (int i = 0; i <= 10; i += 1) {
            assertEquals(ans.getRecursive(i), stu.get(i));
        }
    }

    @Test
    public void testAddLast() {
        for (int i = 0; i <= 10; i += 1) {
            int randomNum = StdRandom.uniform(0, 100);
            stu.addLast(randomNum);
            ans.addLast(randomNum);
        }

        for (int i = 0; i <= 10; i += 1) {
            assertEquals(ans.getRecursive(i), stu.get(i));
        }
    }

    @Test
    public void testRemoveFirst() {
        for (int i = 0; i <= 10; i += 1) {
            int randomNum = StdRandom.uniform(0, 100);
            stu.addFirst(randomNum);
            ans.addFirst(randomNum);
        }

        for (int i = 0; i <= 10; i += 1) {
            assertEquals(ans.removeFirst(), stu.removeFirst());
        }


    }

    @Test
    public void testRemoveLast1() {
        String errorString = "";
        for (int i = 0; i <= 10; i += 1) {
            int randomNum = StdRandom.uniform(0, 100);
            stu.addFirst(randomNum);
            ans.addFirst(randomNum);
            assertEquals(errorString + "removeLast()\n", ans.removeLast(), stu.removeLast());
        }
    }

    // ERROR OCCUR
    @Test
    public void testRemoveLast2() {
        String errorString = "";
        for (int i = 0; i <= 10; i += 1) {
            int randomNum = StdRandom.uniform(0, 100);
            stu.addFirst(randomNum);
            ans.addFirst(randomNum);
            errorString += ("addFirst(" + randomNum + ")\n");
        }

        for (int i = 0; i <= 10; i += 1) {
            Integer expect = ans.removeLast();
            Integer actual = stu.removeLast();
            errorString += "removeLast()\n";
            assertEquals(errorString, expect, actual);
        }
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(TestArrayDequeGold.class);
    }
}
