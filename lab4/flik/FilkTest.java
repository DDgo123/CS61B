package flik;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FilkTest {

    @Test
    public void EqualTest() {
        int i = 0;
        int j = 0;
        while (i <= 500) {
            assertTrue("i is " + i + " j is " + j, Flik.isSameNumber(i, j));
            i += 1;
            j += 1;
        }
    }
}

