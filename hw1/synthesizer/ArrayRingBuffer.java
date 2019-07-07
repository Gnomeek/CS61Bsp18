package synthesizer;
import java.util.Iterator;

//Make sure to make this class and all of its methods public
//Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    public int first;
    /* Index for the next enqueue. */
    public int last;
    /* Array for storing the buffer data. */
    public T[] rb;


    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // Note that the local variable here shadows the field
        // we inherit from AbstractBoundedQueue, so you'll
        // need to use this.capacity to set the capacity.
        first = 0;
        last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
        rb = (T[]) new Object[this.capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        //Enqueue the item. Don't forget to increase fillCount and update last.
        if (! iterator().hasNext()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;

        updateIndex(1);
    }


    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        //Dequeue the first item. Don't forget to decrease fillCount and update
        if (iterator().next() == null) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T temp = rb[first];
        rb[first] = null;

        updateIndex(0);

        return temp;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        //Return the first item. None of your instance variables should change.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    /** 'first' stores the index of the least recently inserted item
     *  'last' stores the index one beyond the most recently inserted item
     */

    private void updateIndex(int signal) {
        if (signal == 1) {
            this.fillCount += 1;
            last += 1;
        } else {
            this.fillCount -= 1;
            first += 1;
        }

        if (first == this.capacity) {
            first = 0;
        }
        if (last == this.capacity) {
            last = 0;
        }
    }
    //When you get to part 5, implement the needed code to support iteration.
    public Iterator<T> iterator() {
        return new ArrayRingIterator<>();
    }

    private class ArrayRingIterator<T> implements Iterator<T> {
        private int ptr;

        public ArrayRingIterator() {
            ptr = first;
        }

        @Override
        public boolean hasNext() {
            if (fillCount < capacity) {
                return true;
            }
            return false;
        }

        public T next() {
            T nextNode = (T)rb[ptr];
            if (ptr == capacity - 1) {
                ptr = 0;
            }
            ptr += 1;
            return nextNode;
        }
    }
}
