package deque;


import java.util.Comparator;

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

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        this.comparator = c;
    }

    public T max() {
        if (isEmpty()) {
            return null;
        }

        T max = get(0);
        for (T item : this) {
            if (comparator.compare(item, max) > 0) {
                max = item;
            }
        }

        return max;
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }

        T max = get(0);
        for (T item : this) {
            if (c.compare(item, max) > 0) {
                max = item;
            }
        }

        return max;
    }
}




