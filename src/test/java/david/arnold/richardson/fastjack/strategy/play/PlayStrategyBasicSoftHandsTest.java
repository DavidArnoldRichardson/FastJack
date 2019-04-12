package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HelperForTests;
import david.arnold.richardson.fastjack.PlayerDecision;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayStrategyBasicSoftHandsTest extends HelperForTests {

    @Test
    public void testAceWithTwoOrThree() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                    assertEquals(PlayerDecision.HIT, compute(cA, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c3, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c2, cA, upcardValue));
                    break;
                case Five:
                case Six:
                    assertEquals(PlayerDecision.DBL, compute(cA, c2, upcardValue));
                    assertEquals(PlayerDecision.DBL, compute(cA, c3, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c2, cA, upcardValue));
                    break;
                case Seven:
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(cA, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c3, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c2, cA, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testAceWithFourOrFive() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                    assertEquals(PlayerDecision.HIT, compute(cA, c4, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c3, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c5, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c3, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c3, cA, cA, upcardValue));
                    break;
                case Four:
                case Five:
                case Six:
                    assertEquals(PlayerDecision.DBL, compute(cA, c4, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c3, cA, upcardValue));
                    assertEquals(PlayerDecision.DBL, compute(cA, c5, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c3, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c3, cA, cA, upcardValue));
                    break;
                case Seven:
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(cA, c4, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c3, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c5, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c3, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c3, cA, cA, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testAceWithSix() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                    assertEquals(PlayerDecision.HIT, compute(cA, c6, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c5, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c4, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c2, c2, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c2, c2, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c2, cA, cA, cA, cA, upcardValue));
                    break;
                case Three:
                case Four:
                case Five:
                case Six:
                    assertEquals(PlayerDecision.DBL, compute(cA, c6, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c5, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c4, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c2, c2, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c2, c2, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c2, cA, cA, cA, cA, upcardValue));
                    break;
                case Seven:
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(cA, c6, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c5, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c4, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c2, c2, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c2, c2, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c2, cA, cA, cA, cA, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testAceWithSeven() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                case Five:
                case Six:
                    assertEquals(PlayerDecision.DBL, compute(cA, c7, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(rulesDoubleDownLimited, cA, c7, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(cA, c6, cA, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(cA, c5, c2, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(cA, c3, c2, c2, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(cA, c3, c2, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(cA, c3, cA, cA, cA, cA, upcardValue));
                    break;
                case Seven:
                case Eight:
                    assertEquals(PlayerDecision.STD, compute(cA, c7, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(cA, c6, cA, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(cA, c5, c2, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(cA, c3, c2, c2, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(cA, c3, c2, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(cA, c3, cA, cA, cA, cA, upcardValue));
                    break;
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(cA, c7, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c6, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c5, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c3, c2, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c3, c2, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(cA, c3, cA, cA, cA, cA, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testAceWithEight() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                case Five:
                    assertEquals(PlayerDecision.STD, compute(cA, c8, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(cA, c6, c2, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(cA, c6, cA, cA, upcardValue));
                    break;
                case Six:
                    assertEquals(PlayerDecision.DBL, compute(cA, c8, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(rulesDoubleDownLimited, cA, c8, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(cA, c6, c2, upcardValue));
                    break;
                case Seven:
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.STD, compute(cA, c8, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(cA, c6, c2, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(cA, c6, cA, cA, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testAceWithNine() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            assertEquals(PlayerDecision.STD, compute(cA, c9, upcardValue));
            assertEquals(PlayerDecision.STD, compute(cA, c8, cA, upcardValue));
            assertEquals(PlayerDecision.STD, compute(cA, c7, cA, cA, upcardValue));
        }
    }

    @Test
    public void testSingle() {
        assertEquals(PlayerDecision.HIT, compute(cA, c2, Four));
    }
}
