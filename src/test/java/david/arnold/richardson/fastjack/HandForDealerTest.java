package david.arnold.richardson.fastjack;

import org.junit.Test;

public class HandForDealerTest extends HelperForTests {
    @Test
    public void testShouldHitH17() {
        Rules rules = new Rules(
                1,
                5,
                5,
                1,
                true);
        Shoe shoe = new Shoe(rules);
        HandForDealer hand = new HandForDealer(shoe);

    }
}
