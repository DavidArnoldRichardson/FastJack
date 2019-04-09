package david.arnold.richardson.fastjack;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HandTest extends HelperForTests {

    @Test
    public void testComputeMinSum() {
        Hand hand = new HandForTesting(shoe, 20);
        assertEquals(0, hand.computeMinPointSum());

        resetHand(hand, cA);
        assertEquals(1, hand.computeMinPointSum());

        resetHand(hand, c4, cT);
        assertEquals(14, hand.computeMinPointSum());
    }

    @Test
    public void testHasAtLeastOneAce() {
        Hand hand = new HandForTesting(shoe, 20);
        assertFalse(hand.hasAtLeastOneAce());

        resetHand(hand, c2, c3, c4, c5, c6, c7, c8, c9, cT);
        assertFalse(hand.hasAtLeastOneAce());

        hand.addCard(cA);
        assertTrue(hand.hasAtLeastOneAce());

        hand.addCard(cA);
        assertTrue(hand.hasAtLeastOneAce());

        hand.addCard(cA);
        assertTrue(hand.hasAtLeastOneAce());
    }

    @Test
    public void testIsSoft() {
        Hand hand = new HandForTesting(shoe, 20);
        assertFalse(hand.isSoft());

        resetHand(hand, c2, c3);
        assertFalse(hand.isSoft());

        resetHand(hand, c2, c3);
        assertFalse(hand.isSoft());

        resetHand(hand, c2, cA);
        assertTrue(hand.isSoft());

        // blackjack, actually, but also soft.
        resetHand(hand, cT, cA);
        assertTrue(hand.isSoft());

        resetHand(hand, c9, cA);
        assertTrue(hand.isSoft());

        resetHand(hand, c9, cA, cA);
        assertTrue(hand.isSoft());

        resetHand(hand, c9, cA, cA, cA);
        assertFalse(hand.isSoft());

        resetHand(hand, c9, cA, c2);
        assertFalse(hand.isSoft());
    }

    @Test
    public void testIsBlackjack() {
        Hand hand = new HandForTesting(shoe, 20);
        assertFalse(hand.isBlackjack(false));
        assertFalse(hand.isBlackjack(true));

        resetHand(hand, c2, c4);
        assertFalse(hand.isBlackjack(false));
        assertFalse(hand.isBlackjack(true));

        resetHand(hand, cT, c6, c5);
        assertFalse(hand.isBlackjack(false));
        assertFalse(hand.isBlackjack(true));

        resetHand(hand, cT, cA);
        assertTrue(hand.isBlackjack(false));
        assertFalse(hand.isBlackjack(true));

        resetHand(hand, cA, cT);
        assertTrue(hand.isBlackjack(false));
        assertFalse(hand.isBlackjack(true));

        resetHand(hand, cA, cT, cA);
        assertFalse(hand.isBlackjack(false));
        assertFalse(hand.isBlackjack(true));

        resetHand(hand, cA, cT, c2);
        assertFalse(hand.isBlackjack(false));
        assertFalse(hand.isBlackjack(true));
    }

    @Test
    public void testIsBusted() {
        Hand hand = new HandForTesting(shoe, 20);
        assertFalse(hand.isBusted());

        resetHand(hand, c2);
        assertFalse(hand.isBusted());

        resetHand(hand, cT, cT);
        assertFalse(hand.isBusted());

        resetHand(hand, cT, cT, cA);
        assertFalse(hand.isBusted());

        resetHand(hand, cT, cT, cA, cA);
        assertTrue(hand.isBusted());

        resetHand(hand, cT, cT, cT);
        assertTrue(hand.isBusted());

        resetHand(hand, c5, c5, c5, c5);
        assertFalse(hand.isBusted());

        resetHand(hand, c5, c5, c5, c5, c5);
        assertTrue(hand.isBusted());
    }
}
