package deque;


import java.util.Iterator;

/** Array based list.
 *  @author Josh Hug
 */

//         0 1  2 3 4 5 6 7
// items: [6 9 -1 2 0 0 0 0 ...]
// size: 5



public class ArrayDeque<T>  implements Deque<T> , Iterable<T>{
    private T[] Array;

    private int size;
    private int nextFirst;
    private int nextLast;

    /**
     * Creates an empty list.
     */
    public ArrayDeque() {
        Array = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /**
     * Resizes the underlying array to the target capacity.
     */
    private void resize(int capacity) {
        int current = (nextFirst + 1 + Array.length) % Array.length;
        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            a[i] = Array[current];
            current = (current + 1) % Array.length;
        }
        Array = a;
        nextFirst = a.length - 1;
        nextLast = size;
    }

    public void addFirst(T x) {
        if (size == Array.length) {
            resize(size * 2);
        }

        Array[nextFirst] = x;
        nextFirst = (nextFirst - 1 + Array.length) % Array.length;
        size = size + 1;

    }


    public void addLast(T x) {
        if (size == Array.length) {
            resize(size * 2);
        }

        Array[nextLast] = x;
        nextLast = (nextLast + 1) % Array.length;
        size = size + 1;

    }
    private void sizeCheck(){
        double used = (double) size / Array.length;
        if (used <0.25 && Array.length > 16){
            resize(size * 2);
        }
    }
    public T removeLast() {
        sizeCheck();
        int last = (nextLast - 1 + Array.length) % Array.length;
        T x = Array[last];
        if (x != null) {
            nextLast = last;
            Array[last] = null;
            size--;
        }

        return x;
    }

    public T removeFirst() {
        sizeCheck();
        int first = (nextFirst + 1) % Array.length;
        T x = Array[first];
        if (x != null) {
            nextFirst = first;
            Array[first] = null;
            size--;
        }
        return x;
    }



    public T get(int i) {
        if (i <= size && i >= 0){
            int position = (nextFirst + i + 1) % Array.length;
            return Array[position];
            }
        return null;

    }



    public int size() {
        return size;
    }



    public void printDeque() {
        int current = (nextFirst + 1 + Array.length) % Array.length;
        for (int i = 0; i < size; i++) {
            System.out.print(Array[current] + " ");
            current = (current + 1) % Array.length;
        }
        System.out.println();
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
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    private class ArrayDequeIterator implements Iterator<T>{
        private int current;
        public ArrayDequeIterator(){
            current = 0;

        }

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public T next() {
            T returnItem = get(current);
            current += 1;
            return returnItem;

        }
    }
}




