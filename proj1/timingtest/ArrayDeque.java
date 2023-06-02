package timingtest;



/** Array based list.
 *  @author Josh Hug
 */

//         0 1  2 3 4 5 6 7
// items: [6 9 -1 2 0 0 0 0 ...]
// size: 5

/* Invariants:
 addLast: The next item we want to add, will go into position size
 getLast: The item we want to return is in position size - 1
 size: The number of items in the list should be size.
*/

public class ArrayDeque<Item> {
    private Item[] Array;

    private int size;
    private int nextFirst;
    private int nextLast;

    /** Creates an empty list. */
    public ArrayDeque() {
        Array = (Item[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize(int capacity) {
        int current = (nextFirst + 1 + Array.length) % Array.length;
        Item[] a = (Item[]) new Object[capacity];
        for (int i = 0 ; i < size; i++ ){
            a[i] = Array[current];
            current = (current + 1 ) % Array.length;
        }
        Array = a;
        nextFirst = a.length - 1;
        nextLast = size;
    }
    public void addFirst(Item x) {
        if (size == Array.length) {
            resize(size * 2);
        }

        Array[nextFirst] = x;
        nextFirst = (nextFirst - 1 + Array.length) % Array.length;
        size = size + 1;

    }
    /** Inserts X into the back of the list. */
    public void addLast(Item x) {
        if (size == Array.length) {
            resize(size * 2);
        }

        Array[nextLast] = x;
        nextLast = (nextLast + 1 ) % Array.length;
        size = size + 1;

    }
    public Item removeLast() {
        int last = (nextLast - 1 +Array.length) % Array.length;
        Item x = Array[last];
        if (x != null){
            nextLast = last;
            Array[last] = null;
            size --;
        }

        return x;
    }
    public Item removeFirst() {
        int first = (nextFirst + 1 ) % Array.length;
        Item x = Array[first];
        if (x != null){
            nextFirst = first;
            Array[first] = null;
            size --;
        }
        return x;
    }




    /** Returns the item from the back of the list. */
    /** Gets the ith item in the list (0 is the front). */
    public Item get(int i) {
        return Array[i];
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }
    public Item getLast() {
        int last = (nextLast - 1 +Array.length) % Array.length;
        return Array[last];
    }
    public void printDeque(){
        int current = (nextFirst + 1 + Array.length) % Array.length;
        for (int i = 0 ; i < size; i++ ){
            System.out.print(Array[current] + " ");
            current = (current + 1 ) % Array.length;
        }
        System.out.println();
    }
}


