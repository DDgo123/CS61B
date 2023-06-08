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
    private Comparator<T> c;

    public MaxArrayDeque(Comparator<T> c) {
        this.c = c;
    }

    public T max() {
        return max(c);
    }

    public T max(Comparator<T> comparator) {
        if (isEmpty()) {
            return null;
        }

        T maxValue = get(0);
        for (int i = 0; i < size(); i++) {
            if (comparator.compare(get(i), maxValue) > 0) {
                maxValue = get(i);
            }
        }

        return maxValue;
    }
}





