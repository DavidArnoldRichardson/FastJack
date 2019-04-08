package david.arnold.richardson.fastjack;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShoeTest {
    @Test
    public void testOneDeckShuffle() {
        Shoe shoe = new Shoe(new Rules(1));
        String output = shoe.toString();
        assertEquals(52 * 3, output.length());
    }

    @Test
    public void testSixDeckShuffle() {
        Shoe shoe = new Shoe(new Rules(6));
        String output = shoe.toString();
        assertEquals(52 * 3 * 6, output.length());
    }
}
