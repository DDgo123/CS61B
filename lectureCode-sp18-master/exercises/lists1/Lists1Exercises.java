public class Lists1Exercises {
    /** Returns an IntList identical to L, but with
      * each element incremented by x. L is not allowed
      * to change. */
    public static IntList incrList(IntList L, int x) {
        IntList newlist = new IntList(L.first + x, null);
        IntList p = L.rest;
        IntList current = newlist;

        while (p != null) {
            current.rest = new IntList(p.first + x, null);
            p = p.rest;
            current = current.rest;
        }

        return newlist;
    }

    /** Returns an IntList identical to L, but with
      * each element incremented by x. Not allowed to use
      * the 'new' keyword. */
    public static IntList dincrList(IntList L, int x) {
        /* Your code here. */
        IntList current = L;
        while (current != null){
            current.first += x;
            current = current.rest;

        }
        return L;
    }

    public static void main(String[] args) {
        IntList L = new IntList(5, null);
        L.rest = new IntList(7, null);
        L.rest.rest = new IntList(9, null);

        System.out.println(L.size());
        System.out.println(L.iterativeSize());
        IntList incrList = incrList(L,1);
        System.out.println(incrList.get(1));

        // Test your answers by uncommenting. Or copy and paste the
        // code for incrList and dincrList into IntList.java and
        // run it in the visualizer.
        // System.out.println(L.get(1));
        // System.out.println(incrList(L, 3));
         dincrList(L,3);
        System.out.println(L.get(3));
    }
}