import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    StudentArrayDeque<Integer> stu = new StudentArrayDeque<>();
    ArrayDequeSolution<Integer> ans = new ArrayDequeSolution<>();

    @Test
    public void testAddFirst() {

        for (int i = 0; i <= 10; i += 1){
            int randomNum = StdRandom.uniform(0, 100);
            stu.addFirst(randomNum);
            ans.addFirst(randomNum);
        }

        for (int i = 0; i <= 10; i += 1){
            Integer expect = ans.getRecursive(i);
            Integer actual = stu.get(i);
            assertEquals("AddFirst(" + actual + ")\n", expect, actual);
        }
    }

    @Test
    public void testAddLast() {
        for (int i = 0; i <= 10; i += 1){
            int randomNum = StdRandom.uniform(0, 100);
            stu.addLast(randomNum);
            ans.addLast(randomNum);
        }

        for (int i = 0; i <= 10; i += 1){
            Integer expect = ans.getRecursive(i);
            Integer actual = stu.get(i);
            assertEquals("AddLast(" + actual + ")\n", expect, actual);
        }
    }

    @Test
    public void testRemoveFirst() {
        for (int i = 0; i <= 10; i += 1){
            int randomNum = StdRandom.uniform(0, 100);
            stu.addFirst(randomNum);
            ans.addFirst(randomNum);
        }

        for (int i = 0; i <= 10; i += 1){
            Integer expect = ans.removeFirst();
            Integer actual = stu.removeFirst();
            assertEquals("RemoveFirst()" + actual, expect, actual);
        }


    }

    // ERROR OCCUR
    @Test
    public void testRemoveLast() {
        String errorString = "";
        for (int i = 0; i <= 10; i += 1){
            int randomNum = StdRandom.uniform(0, 100);
            stu.addFirst(randomNum);
            ans.addFirst(randomNum);
            errorString += ("addFirst(" + randomNum + ")\n");
        }

        for (int i = 0; i <= 9; i += 1){
            Integer expect = ans.removeLast();
            Integer actual = stu.removeLast();
            assertEquals(errorString + "removeLast()\n", expect, actual);
        }
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(TestArrayDequeGold.class);
    }
}
