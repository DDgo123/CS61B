package deque;
import java.util.Comparator;

public class test {
    public static void main(String[] args){
        Comparator<Integer> comparator = (o1, o2) -> 0;
        MaxArrayDeque<Integer> test1 = new MaxArrayDeque<>(comparator);
        for (int i = 0;i < 10000;i++){
            test1.addLast(i);
        }

            System.out.println(test1.max());



        LinkedListDeque<Integer> test2 = new LinkedListDeque<>();




        System.out.println(test2.removeLast());



    }
}
