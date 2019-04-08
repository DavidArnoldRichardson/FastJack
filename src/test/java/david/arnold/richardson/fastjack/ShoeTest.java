package david.arnold.richardson.fastjack;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShoeTest extends HelperForTests {
    @Test
    public void testOneDeckShuffle() {
        Shoe shoe = new Shoe(Rules.getWendover1D());
        String output = shoe.toString();
        assertEquals(52 * 3, output.length());
    }

    @Test
    public void testSixDeckShuffle() {
        Shoe shoe = new Shoe(Rules.getWendover6D());
        String output = shoe.toString();
        assertEquals(52 * 3 * 6, output.length());
    }
}
