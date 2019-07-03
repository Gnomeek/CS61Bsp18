import static org.junit.Assert.*;

import org.junit.Test;

public class FlikTest {

    @Test
    public void testisSameNumber(){
        int a = 100;
        int b = 200;
        int c = 100;
        int d = 128;
        assertTrue(Flik.isSameNumber(a, c));
        assertFalse(Flik.isSameNumber(a, b));
        assertTrue(Flik.isSameNumber(d, d));
    }

}
