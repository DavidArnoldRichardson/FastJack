package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HelperForTests;
import david.arnold.richardson.fastjack.PlayerDecision;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayStrategyBasicSplitHandsTest extends HelperForTests {

    @Test
    public void testPairOfTwosOrThrees() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                case Five:
                case Six:
                case Seven:
                    assertEquals(PlayerDecision.SPL, compute(c2, c2, upcardValue));
                    assertEquals(PlayerDecision.SPL, compute(c3, c3, upcardValue));
                    break;
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(c2, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c3, c3, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testPairOfFours() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                    assertEquals(PlayerDecision.HIT, compute(c4, c4, upcardValue));
                    break;
                case Five:
                case Six:
                    assertEquals(PlayerDecision.SPL, compute(c4, c4, upcardValue));
                    break;
                case Seven:
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(c4, c4, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testPairOfFives() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                case Five:
                case Six:
                case Seven:
                case Eight:
                case Nine:
                    assertEquals(PlayerDecision.DBL, compute(c5, c5, upcardValue));
                    break;
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(c5, c5, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testPairOfSixes() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                case Five:
                case Six:
                    assertEquals(PlayerDecision.SPL, compute(c6, c6, upcardValue));
                    break;
                case Seven:
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(c6, c6, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testPairOfSevens() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                case Five:
                case Six:
                case Seven:
                    assertEquals(PlayerDecision.SPL, compute(c7, c7, upcardValue));
                    break;
                case Eight:
                case Nine:
                    assertEquals(PlayerDecision.HIT, compute(c7, c7, upcardValue));
                    break;
                case Ten:
                    assertEquals(PlayerDecision.SUR, compute(rulesCanSurrender, c7, c7, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(rulesCannotSurrender, c7, c7, upcardValue));
                    break;
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(c7, c7, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testPairOfEights() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            assertEquals(PlayerDecision.SPL, compute(c8, c8, upcardValue));
        }
    }

    @Test
    public void testPairOfNines() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                case Five:
                case Six:
                    assertEquals(PlayerDecision.SPL, compute(c9, c9, upcardValue));
                    break;
                case Seven:
                    assertEquals(PlayerDecision.STD, compute(c9, c9, upcardValue));
                    break;
                case Eight:
                case Nine:
                    assertEquals(PlayerDecision.SPL, compute(c9, c9, upcardValue));
                    break;
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.STD, compute(c9, c9, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testPairOfTens() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            assertEquals(PlayerDecision.STD, compute(cT, cT, upcardValue));
        }
    }

    @Test
    public void testPairOfAces() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                case Five:
                case Six:
                case Seven:
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.SPL, compute(cA, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, computeCannotSplit(cA, cA, upcardValue));
                    break;
            }
        }
    }
}
