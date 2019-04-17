package david.arnold.richardson.fastjack;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    @Test
    public void testComputePlayerEdge() {
        assertEquals("-0.500%", Player.computePlayerEdge(10000L, 9950L));
        assertEquals("0.500%", Player.computePlayerEdge(10000L, 10050L));
    }
}
