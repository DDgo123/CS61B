package deque;

public class test {
    public static void main(String[] args){
        ArrayDeque<Integer> test1 = new ArrayDeque<>();
        for (int i = 0;i < 10000;i++){
            test1.addLast(i);
        }
        for (int i = 0;i < 9920;i++){
            test1.removeLast();
        }

        for (int i : test1){
            System.out.println(i);
        }


        LinkedListDeque<Integer> test2 = new LinkedListDeque<>(0);




        System.out.println(test2.removeLast());



    }
}
