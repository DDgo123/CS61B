package deque;


import java.util.Iterator;

/** An SLList is a list of integers, which hides the terrible truth
 * of the nakedness within.
 * 不变量：
 * sentinel.next指向第一个项目；
 * sentinel.prev指向结尾；
 * size永远是项目的大小*/
public class LinkedListDeque<T> implements Deque<T> ,Iterable<T> {
    private class LinkList {
        private LinkList prev;
        private final T item;
        private LinkList next;

        public LinkList(LinkList p, T i, LinkList n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    /* The first item (if it exists) is at sentinel.next. */
    private final LinkList sentinel;
    private int size;

    /** Creates an empty timingtest.SLList. */
    public LinkedListDeque() {
        sentinel = new LinkList(null,null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public LinkedListDeque(T x) {
        sentinel = new LinkList(null,null, null);
        sentinel.next = new LinkList(sentinel,x, null);
        sentinel.prev = sentinel.next;
        size = 1;
    }


    public void addFirst(T x) {
        LinkList oldFirst = sentinel.next;

        sentinel.next = new LinkList(sentinel,x, sentinel.next);
        oldFirst.prev = sentinel.next;

        size = size + 1;

    }

    public void addLast(T x) {
        size = size + 1;

        LinkList prevLast = sentinel.prev;
        prevLast.next = new LinkList(prevLast,x, sentinel);
        sentinel.prev = prevLast.next;
    }

    public T removeFirst(){
        if (sentinel.next == sentinel){
            return  null;
        }
        size -= 1;
        LinkList First = sentinel.next;
        First.next.prev = sentinel;
        sentinel.next = First.next;
        return First.item;

    }

    public T removeLast(){
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





    public T get(int i) {
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
    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(LinkList node, int index) {
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

        if (!(o instanceof Deque)) {
            return false;
        }

        Deque<?> other = (Deque<?>) o;

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

    @Override
    public Iterator<T> iterator()  {
        return new LinkedListDequeIterator();
    }
    private class LinkedListDequeIterator implements Iterator<T>{
        private LinkList current;
        public LinkedListDequeIterator(){
            current = sentinel;

        }

        @Override
        public boolean hasNext() {
            return !(current.next == sentinel);
        }

        @Override
        public T next() {
            current = current.next;
            return current.item;

        }
    }

}

