package deque;


/** An SLList is a list of integers, which hides the terrible truth
 * of the nakedness within.
 * 不变量：
 * sentinel.next指向第一个项目；
 * sentinel.prev指向结尾；
 * size永远是项目的大小*/
public class LinkedListDeque<Item>  {
    private class LinkList {
        public LinkList prev;
        public Item item;
        public LinkList next;

        public LinkList(LinkList p,Item i, LinkList n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    /* The first item (if it exists) is at sentinel.next. */
    private LinkList sentinel;
    private int size;

    /** Creates an empty timingtest.SLList. */
    public LinkedListDeque() {
        sentinel = new LinkList(null,null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public LinkedListDeque(Item x) {
        sentinel = new LinkList(null,null, null);
        sentinel.next = new LinkList(sentinel,x, null);
        sentinel.prev = sentinel.next;
        size = 1;
    }


    public void addFirst(Item x) {
        size = size + 1;
        sentinel.next = new LinkList(sentinel,x, sentinel.next);

    }

    public void addLast(Item x) {
        size = size + 1;

        LinkList prevLast = sentinel.prev;
        prevLast.next = new LinkList(prevLast,x, sentinel);
        sentinel.prev = prevLast.next;
    }

    public Item removeFirst(){
        if (sentinel.next == sentinel){
            return  null;
        }
        size -= 1;
        LinkList First = sentinel.next;
        First.next.prev = sentinel;
        sentinel.next = First.next;
        return First.item;

    }

    public Item removeLast(){
        if (sentinel.prev == sentinel){
            return  null;
        }
        size -= 1;
        LinkList Last = sentinel.prev;
        Last.prev.next = sentinel;
        sentinel.prev = Last.prev;
        return Last.item;

    }
    /** Returns the first item in the list. */

    public void printDeque(){
        int s = size;
        LinkList current = sentinel.next;
        while (s != 0){
            System.out.print(current.item + " ");
            current = current.next;
            s --;
        }
        System.out.println();
    }

    public boolean isEmpty(){
        if (size == 0){
            return true;
        }
        return false;
    }

    /** Adds x to the end of the list. */


    /** returns last item in the list */

    public Item get(int i) {
        if (i <= size){
            LinkList current = sentinel.next;
            while (i > 0 ){
                current = current.next;
                i --;
            }
            return current.item;
        }
        return null;

    }
    public Item getRecursive(int index) {
        return getRecursiveHelper(sentinel.next, index);
    }

    private Item getRecursiveHelper(LinkList node, int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(node.next, index - 1);
    }



    /** Returns the size of the list. */

    public int size() {
        return size;
    }
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (!(o instanceof MaxArrayDeque)) {
            return false;
        }

        MaxArrayDeque<?> other = (MaxArrayDeque<?>) o;

        if (this.size() != other.size()) {
            return false;
        }

        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        /* Creates a list of one integer, namely 10 */
        LinkedListDeque L = new LinkedListDeque();
        L.addLast(20);
        System.out.println(L.size());
    }

}

