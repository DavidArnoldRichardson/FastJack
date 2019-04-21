package david.arnold.richardson.fastjack;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HandForDealerTest extends HelperForTests {
    @Test
    public void testShouldHitHardLow() {
        HandForDealer hand = new HandForDealer(tableWithSixDecks.getShoe());
        resetHand(hand, c2, c2);
        assertTrue(hand.shouldDealerHit());
        resetHand(hand, c2, c3);
        assertTrue(hand.shouldDealerHit());
        resetHand(hand, c2, c4);
        assertTrue(hand.shouldDealerHit());
        resetHand(hand, c2, c4);
        assertTrue(hand.shouldDealerHit());
        resetHand(hand, c2, c5);
        assertTrue(hand.shouldDealerHit());
        resetHand(hand, c5, c5);
        assertTrue(hand.shouldDealerHit());
        resetHand(hand, cT, c5);
        assertTrue(hand.shouldDealerHit());
        resetHand(hand, cT, c6);
        assertTrue(hand.shouldDealerHit());
    }

    @Test
    public void testShouldHitHardHigh() {
        HandForDealer hand = new HandForDealer(tableWithSixDecks.getShoe());
        resetHand(hand, cT, c7);
        assertFalse(hand.shouldDealerHit());
        resetHand(hand, cT, c8);
        assertFalse(hand.shouldDealerHit());
        resetHand(hand, cT, c4, c4);
        assertFalse(hand.shouldDealerHit());
        resetHand(hand, cT, c4, c2, c2);
        assertFalse(hand.shouldDealerHit());
        resetHand(hand, cT, c9);
        assertFalse(hand.shouldDealerHit());
        resetHand(hand, cT, cT);
        assertFalse(hand.shouldDealerHit());
    }

    @Test
    public void testShouldHitSoftLow() {
        HandForDealer hand = new HandForDealer(tableWithSixDecks.getShoe());
        resetHand(hand, cA, cA);
        assertTrue(hand.shouldDealerHit());
        resetHand(hand, cA, c2);
        assertTrue(hand.shouldDealerHit());
        resetHand(hand, cA, c3);
        assertTrue(hand.shouldDealerHit());
        resetHand(hand, cA, c4);
        assertTrue(hand.shouldDealerHit());
        resetHand(hand, cA, c2, c2);
        assertTrue(hand.shouldDealerHit());
        resetHand(hand, cA, c2, cA, cA);
        assertTrue(hand.shouldDealerHit());
        resetHand(hand, cA, c5);
        assertTrue(hand.shouldDealerHit());
    }

    @Test
    public void testShouldHitSoftHigh() {
        HandForDealer hand = new HandForDealer(tableWithSixDecks.getShoe());
        resetHand(hand, cA, c7);
        assertFalse(hand.shouldDealerHit());
        resetHand(hand, cA, c8);
        assertFalse(hand.shouldDealerHit());
        resetHand(hand, cA, c9);
        assertFalse(hand.shouldDealerHit());
        resetHand(hand, cA, cT);
        assertFalse(hand.shouldDealerHit());
        resetHand(hand, cA, c5, c5);
        assertFalse(hand.shouldDealerHit());
        resetHand(hand, cA, c5, c3, c2);
        assertFalse(hand.shouldDealerHit());
    }

    @Test
    public void testForHorribleBug() {
        HandForDealer hand = new HandForDealer(tableWithSixDecks.getShoe());
        resetHand(hand, cT, c5, cA);
        assertTrue(hand.shouldDealerHit());
    }

    @Test
    public void testShouldHitSoft17_false() {
        HandForDealer hand = new HandForDealer(tableWithSixDecks.getShoe());
        tableWithSixDecks.getRules().setH17(false);
        resetHand(hand, cA, c6);
        assertFalse(hand.shouldDealerHit());
        resetHand(hand, cA, c3, c3);
        assertFalse(hand.shouldDealerHit());
        resetHand(hand, cA, cA, c5);
        assertFalse(hand.shouldDealerHit());
    }

    @Test
    public void testShouldHitSoft17_true() {
        HandForDealer hand = new HandForDealer(tableWithSixDecks.getShoe());
        tableWithSixDecks.getShoe().getRules().setH17(true);
        resetHand(hand, cA, c6);
        assertTrue(hand.shouldDealerHit());
        resetHand(hand, cA, c3, c3);
        assertTrue(hand.shouldDealerHit());
        resetHand(hand, cA, cA, c5);
        assertTrue(hand.shouldDealerHit());
    }
}
