package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;
public class TestArrayDequeEC {
    @Test
    public void test1() {
        ArrayDequeSolution<Integer> ADS = new ArrayDequeSolution();
        StudentArrayDeque<Integer> SAD = new StudentArrayDeque<>();
        String errString = new String();
        for (int i = 0; i < 10; i++) {
            double randomValue = StdRandom.uniform();
            if (randomValue < 0.5) {
                ADS.addLast(i);
                SAD.addLast(i);
                errString += ("addLast(" + i + ")\n");
            } else {
                ADS.addFirst(i);
                SAD.addFirst(i);
                errString += ("addFirst(" + i + ")\n");
            }


        }
        errString += ("size()\n");
        assertEquals(errString, ADS.size(), SAD.size());

        for (int i = 0; i < 10; i++) {
            double randomValue = StdRandom.uniform();
            if (randomValue < 0.5) {
                Integer a = ADS.removeLast();
                Integer b = SAD.removeLast();
                errString += "removeLast()\n";
                assertEquals(errString, a, b);

            } else {
                Integer a = ADS.removeFirst();
                Integer b = SAD.removeFirst();
                errString += "removeFirst()\n";
                assertEquals(errString, a, b);
            }
        }
        errString += ("size()\n");
        assertEquals(errString, ADS.size(), SAD.size());

    }
}