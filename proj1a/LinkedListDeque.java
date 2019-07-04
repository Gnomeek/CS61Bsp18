public class LinkedListDeque<Gnome> {

    /** Constructor */
    public class DLLnode {
        public Gnome item;
        public DLLnode prev;
        public DLLnode next;
        public DLLnode(Gnome i, DLLnode pre, DLLnode nex) {
            item = i;
            prev = pre;
            next = nex;
        }
    }
    private int size;
    private DLLnode sentinel;

    /** make an empty DLList */
    public LinkedListDeque() {
        sentinel = new DLLnode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /** add the item to the first of the DLList */
    public void addFirst(Gnome i){
        DLLnode newItem = new DLLnode(i, sentinel, sentinel.next);
        sentinel.next.prev = newItem;
        sentinel.next = newItem;
        size += 1;
    }

    /** add the item to the last of the DLList */
    public void addLast(Gnome i) {
        DLLnode newItem = new DLLnode(i, sentinel.prev, sentinel);
        sentinel.prev.next = newItem;
        sentinel.prev = newItem;
        size += 1;
    }


    /** Returns true if DlList is empty, false otherwise */
    public boolean isEmpty(){
        if (size == 0) {
            return true;
        }
        return false;
    }

    /** Returns the number of items in the DLList. */
    public int size() {
        return this.size;
    }

    /** Prints the items in the DLList from first to last, separated by a space */
    public void printDeque() {
        DLLnode ptr = this.sentinel;
        while(ptr.next != this.sentinel) {
            System.out.print(ptr.next.item + " ");
            ptr = ptr.next;
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the DLList. If no such item exists, returns null */
    public DLLnode removeFirst() {
        if(this.sentinel.next == this.sentinel) {
            return null;
        }
        DLLnode ptr = this.sentinel.next;
        this.sentinel.next = this.sentinel.next.next;
        this.sentinel.next.prev = this.sentinel;
        return ptr;
    }

    /** Removes and returns the item at the back of the DDList. If no such item exists, returns null */
    public DLLnode removeLast() {
        if(this.sentinel.prev == this.sentinel) {
            return null;
        }
        DLLnode ptr = this.sentinel.prev;
        this.sentinel.prev = this.sentinel.prev.prev;
        this.sentinel.prev.next = this.sentinel;
        return ptr;
    }

    /** Get the item at the given index, If no such item exists, returns null. not to alter the DLList */
    public DLLnode get(int index) {
        DLLnode ptr = this.sentinel.next;
        for(int i = 0; i < index; i += 1){
            ptr = ptr.next;
        }
        return ptr;
    }

    /** Get the item at the given index recursively */
    public DLLnode getRecursive(int index) {
        DLLnode ptr = this.sentinel.next;
        return getRecursiveHelper(index, ptr);
    }

    private DLLnode getRecursiveHelper(int i, DLLnode d) {
        if(i == 0){
            return d;
        }
        return getRecursiveHelper(i - 1, d.next);
    }
}
