public class ArrayDeque<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private double usage;

    /** make an empty ArrayDeque */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        usage = 0;
    }

    private boolean checkUsage() {
        return (usage < 0.25 && items.length > 16);
    }

    private void resize() {
        if (checkUsage() || size == items.length) {
            int targetSize = items.length;

            if (checkUsage()) {
                targetSize /= 2;
            } else {
                targetSize *= 2;
            }

            T[] newItems = (T[]) new Object[targetSize];

            int maxIndex = 0;
            int startIndex = 0;

            if (checkUsage()) {
                for (int i = 0; i < items.length - 1; i++) {
                    if (items[i] == null && items[i + 1] != null) {
                        startIndex = i + 1;
                    }
                    if (items[i] != null && items[i + 1] == null) {
                        maxIndex = i;
                    }
                }

                System.arraycopy(items, startIndex, newItems, 1, maxIndex - startIndex + 1);
                items = newItems;
                nextFirst = 0;
                nextLast = maxIndex - startIndex + 2;

            } else {
                maxIndex = items.length;
                startIndex = 0;
                System.arraycopy(items, startIndex, newItems, 1, maxIndex - startIndex);
                items = newItems;
                nextFirst = 0;
                nextLast = maxIndex - startIndex + 1;
            }
        }
    }

    /** add the item to the first of the ArrayDeque */
    public void addFirst(T i) {
        resize();

        this.items[nextFirst] = i;
        size += 1;

        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }

        usage = (double)size / (double)items.length;
    }

    /** add the item to the last of the ArrayDeque */
    public void addLast(T i) {
        resize();

        this.items[nextLast] = i;
        size += 1;

        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }

        usage = (double)size / (double)items.length;
    }

    /** return true if the ArrayDeque is empty, false otherwise */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /** return the size of the ArrayDeque */
    public int size() {
        return size;
    }

    /** print the items in the ArrayDeque from the first to last, separated by a space */
    public void printDeque() {
        for (T p : items) {
            System.out.print(p + " ");
        }
        System.out.print("\n");
    }

    /** remove the first item in the ArrayDeque and return it */
    public T removeFirst() {
        T temp = this.items[nextFirst + 1];
        this.items[nextFirst + 1] = null;
        if (size == 0) {
            return null;
        }

        if (nextFirst == items.length - 1) {
            temp = this.items[0];
            this.items[0] = null;
        }

        size -= 1;
        usage = (double)size / (double)items.length;
        resize();
        return temp;
    }

    /** remove the last item in the ArrayDeque and return it */
    public T removeLast() {
        T temp = this.items[nextLast - 1];
        this.items[nextLast - 1] = null;
        if (size == 0) {
            return null;
        }

        if (nextLast == 0) {
            temp = this.items[items.length - 1];
            this.items[items.length - 1] = null;
        }

        size -= 1;
        usage = (double)size / (double)items.length;
        resize();
        return temp;
    }

    /** get the item at the given index and return it */
    public T get(int index) {
        if (index >= this.items.length) {
            return null;
        }
        return this.items[index];
    }
}
