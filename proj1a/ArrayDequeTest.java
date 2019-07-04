import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void testAddGet() {
        ArrayDeque<String> ad1 = new ArrayDeque<String>();
        ad1.addLast("a");
        ad1.addLast("b");
        ad1.addFirst("c");

        assertEquals("a", ad1.get(5));
        assertEquals("b", ad1.get(6));
        assertEquals("c", ad1.get(4));
    }

    @Test
    public void testPrintDeque() {
        ArrayDeque<String> ad2 = new ArrayDeque<String>();
        ad2.addLast("a");
        ad2.addLast("b");
        ad2.addFirst("c");
        ad2.addLast("d");
        ad2.addLast("e");
        ad2.addFirst("f");

        ad2.printDeque();
    }

    @Test
    public void testRemove() {
        ArrayDeque<String> ad3 = new ArrayDeque<String>();
        ad3.addLast("a");
        ad3.addLast("b");
        ad3.addFirst("c");

        ArrayDeque<String> ad4 = new ArrayDeque<String>();

        assertEquals(null, ad4.removeFirst());
        assertEquals(null, ad4.removeLast());
        assertEquals("c", ad3.removeFirst());
        assertEquals("b", ad3.removeLast());
    }


}
