package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
    }

    @Test
    public void testEnqueue() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        arb.enqueue(33.1);
        arb.enqueue(44.8);
        arb.enqueue(62.3);
        arb.enqueue(-3.4);
        arb.enqueue(-3.4);
        arb.enqueue(-3.4);
        arb.enqueue(-3.4);
        arb.enqueue(-3.4);
        arb.enqueue(-3.4);
        arb.enqueue(-3.4);
        //arb.enqueue(-3.4);
    }

    @Test
    public void testDequeue() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        arb.enqueue(33.1);
        arb.enqueue(44.8);
        arb.enqueue(62.3);
        arb.enqueue(-3.4);
        assertEquals(33.1, arb.dequeue());
        assertEquals(44.8, arb.dequeue());
        assertEquals(62.3, arb.dequeue());
        assertEquals(-3.4, arb.dequeue());
        //arb.dequeue();
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
