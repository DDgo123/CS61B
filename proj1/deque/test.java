package deque;


public class test {
    public static void main(String[] args){

        ArrayDeque<Integer> test1 = new ArrayDeque<>();
        for (int i = 0;i < 64;i++){
            test1.addLast(i);
        }
        for (int i = 0;i < 63;i++){
            test1.removeFirst();
        }

            System.out.println(test1.size());



        LinkedListDeque<Integer> test2 = new LinkedListDeque<>();




        System.out.println(test2.removeLast());



    }
}
