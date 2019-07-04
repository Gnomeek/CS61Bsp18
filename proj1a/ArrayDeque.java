public class ArrayDeque<Gnome> {

    private Gnome[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private double usage;

    /** make an empty ArrayDeque */
    public ArrayDeque() {
        items = (Gnome[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        usage = 0;
    }

    /** add the item to the first of the ArrayDeque */
    public void addFirst(Gnome i) {
        resize();
        this.items[nextFirst] = i;
        if(nextFirst == 0){
            nextFirst = items.length - 1;
        }
        else{
            nextFirst -= 1;
        }
        size += 1;
    }

    /** add the item to the last of the ArrayDeque */
    public void addLast(Gnome i) {
        resize();
        this.items[nextLast] = i;

        if(nextLast == items.length - 1){
            nextLast = 0;
        }
        else{
            nextLast += 1;
        }
        size += 1;
    }

    /** return true if the ArrayDeque is empty, false otherwise */
    public boolean isEmpty(){
        if(size == 0) {
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
        for (Gnome p : items) {
            System.out.print(p + " ");
        }
        System.out.print("\n");
    }

    /** remove the first item in the ArrayDeque and return it */
    public Gnome removeFirst() {
        Gnome temp = this.items[nextFirst + 1];
        this.items[nextFirst + 1] = null;
        if(size == 0){
            return null;
        }

        if(nextFirst == items.length - 1){
            temp = this.items[0];
            this.items[0] = null;
        }

        size -= 1;
        resize();
        return temp;
    }

    /** remove the last item in the ArrayDeque and return it */
    public Gnome removeLast(){
        Gnome temp = this.items[nextLast - 1];
        this.items[nextLast - 1] = null;
        if(size == 0){
            return null;
        }

        if(nextLast == 0){
            temp = this.items[items.length - 1];
            this.items[items.length - 1] = null;
        }

        size -= 1;
        resize();
        return temp;
    }

    /** get the item at the given index and return it */
    public Gnome get(int index) {
        return this.items[index];
    }

    public boolean checkUsage() {
        return (usage < 0.25 && items.length > 16);
    }

    public void resize(){
        if(checkUsage() || size == items.length) {
            int targetSize = items.length;

            if (checkUsage()) {
                targetSize /= 2;
            }
            else{
                targetSize *= 2;
            }

            Gnome[] newItems = (Gnome[]) new Object[targetSize];

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

            }
            else {
                maxIndex = items.length;
                startIndex = 0;
                System.arraycopy(items, startIndex, newItems, 1, maxIndex - startIndex);
                items = newItems;
                nextFirst = 0;
                nextLast = maxIndex - startIndex + 1;
            }
        }
    }
}
