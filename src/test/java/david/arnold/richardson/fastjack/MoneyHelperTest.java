package david.arnold.richardson.fastjack;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoneyHelperTest {
    @Test
    public void testIt() {
        assertEquals(0L, MoneyHelper.computeAcceptableBet(10, 1, 20, 200));
        assertEquals(10L, MoneyHelper.computeAcceptableBet(10, 100, 5, 200));
        assertEquals(7L, MoneyHelper.computeAcceptableBet(10, 7, 5, 200));
        assertEquals(50L, MoneyHelper.computeAcceptableBet(10, 100, 50, 200));
        assertEquals(200L, MoneyHelper.computeAcceptableBet(1000, 10000, 50, 200));
        assertEquals(50L, MoneyHelper.computeAcceptableBet(10, 10000, 50, 200));
    }
}
