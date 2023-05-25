package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> a = new AListNoResizing<>();
        BuggyAList b = new BuggyAList<>();
        a.addLast(1);
        a.addLast(2);
        a.addLast(3);
        b.addLast(1);
        b.addLast(2);
        b.addLast(3);
        assertEquals(a.removeLast(),b.removeLast());
        assertEquals(a.removeLast(),b.removeLast());
        assertEquals(a.removeLast(),b.removeLast());
    }
    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> b = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                b.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int bsize = b.size();
                System.out.println("L.size: " + size);
                System.out.println("B.size: " + bsize);
            } else if (operationNumber == 2 && L.size() > 0) {
                System.out.println("get L.last is "+L.getLast());
                System.out.println("get b.last is "+b.getLast());
                System.out.println("remove L.last is "+L.removeLast());
                System.out.println("remove b.last is "+b.removeLast());
            }
        }
    }

    }

