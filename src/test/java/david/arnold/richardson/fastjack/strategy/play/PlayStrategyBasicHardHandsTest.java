package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HelperForTests;
import david.arnold.richardson.fastjack.PlayerDecision;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayStrategyBasicHardHandsTest extends HelperForTests {

    @Test
    public void testSumsLessThanNine() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {

            // sum of 5
            assertEquals(PlayerDecision.HIT, compute(c2, c3, upcardValue));

            // sum of 6
            assertEquals(PlayerDecision.HIT, compute(c2, c4, upcardValue));

            // sum of 7
            assertEquals(PlayerDecision.HIT, compute(c2, c5, upcardValue));
            assertEquals(PlayerDecision.HIT, compute(c2, c3, c2, upcardValue));
            assertEquals(PlayerDecision.HIT, compute(c3, c4, upcardValue));

            // sum of 8
            assertEquals(PlayerDecision.HIT, compute(c2, c6, upcardValue));
            assertEquals(PlayerDecision.HIT, compute(c2, c3, c3, upcardValue));
            assertEquals(PlayerDecision.HIT, compute(c3, c5, upcardValue));
            assertEquals(PlayerDecision.HIT, compute(c3, c2, c3, upcardValue));
        }
    }

    @Test
    public void testSumsOfNine() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                    assertEquals(PlayerDecision.HIT, compute(c2, c7, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c2, c3, c4, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c2, c3, c2, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c3, c6, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c4, c5, upcardValue));
                    break;
                case Three:
                case Four:
                case Five:
                case Six:
                    assertEquals(PlayerDecision.DBL, compute(c2, c7, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(rulesDoubleDownLimited, c2, c7, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c2, c3, c4, upcardValue));
                    assertEquals(PlayerDecision.DBL, compute(c3, c6, upcardValue));
                    assertEquals(PlayerDecision.DBL, compute(c4, c5, upcardValue));
                    break;
                case Seven:
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(c2, c7, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c3, c6, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c4, c5, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testSumsOfTen() {
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
                    assertEquals(PlayerDecision.DBL, compute(c2, c8, upcardValue));
                    assertEquals(PlayerDecision.DBL, compute(c3, c7, upcardValue));
                    assertEquals(PlayerDecision.DBL, compute(c4, c6, upcardValue));
                    break;
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(c2, c8, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c3, c7, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c4, c6, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testSumsOfEleven() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            assertEquals(PlayerDecision.DBL, compute(c2, c9, upcardValue));
            assertEquals(PlayerDecision.DBL, compute(c3, c8, upcardValue));
            assertEquals(PlayerDecision.DBL, compute(c4, c7, upcardValue));
            assertEquals(PlayerDecision.DBL, compute(c5, c6, upcardValue));
        }
    }

    @Test
    public void testSumsOfTwelve() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                    assertEquals(PlayerDecision.HIT, compute(c2, cT, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c3, c9, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c4, c8, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c5, c7, upcardValue));
                    break;
                case Four:
                case Five:
                case Six:
                    assertEquals(PlayerDecision.STD, compute(c2, cT, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(c3, c9, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(c4, c8, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(c5, c7, upcardValue));
                    break;
                case Seven:
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(c2, cT, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c3, c9, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c4, c8, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c5, c7, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testSumsOfThirteenAndFourteen() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                case Five:
                case Six:
                    // sum of 13
                    assertEquals(PlayerDecision.STD, compute(c3, cT, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(c4, c9, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(c5, c8, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(c6, c7, upcardValue));

                    // sum of 14
                    assertEquals(PlayerDecision.STD, compute(c4, cT, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(c5, c9, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(c6, c8, upcardValue));
                    break;
                case Seven:
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    // sum of 13
                    assertEquals(PlayerDecision.HIT, compute(c3, cT, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c4, c9, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c5, c8, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c6, c7, upcardValue));

                    // sum of 14
                    assertEquals(PlayerDecision.HIT, compute(c4, cT, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c5, c9, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c6, c8, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testSumsOfFifteen() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                case Five:
                case Six:
                    assertEquals(PlayerDecision.STD, compute(c5, cT, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(c6, c9, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(c7, c8, upcardValue));
                    break;
                case Seven:
                case Eight:
                case Nine:
                    assertEquals(PlayerDecision.HIT, compute(c5, cT, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c6, c9, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c7, c8, upcardValue));
                    break;
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.SUR, compute(rulesCanSurrender, c5, cT, upcardValue));
                    assertEquals(PlayerDecision.SUR, compute(rulesCanSurrender, c6, c9, upcardValue));
                    assertEquals(PlayerDecision.SUR, compute(rulesCanSurrender, c7, c8, upcardValue));

                    // handle case where can't surrender due to more than 2 cards in hand
                    assertEquals(PlayerDecision.HIT, compute(rulesCanSurrender, c5, c4, c6, upcardValue));

                    // handle conditions where can't surrender due to table rules
                    assertEquals(PlayerDecision.HIT, compute(rulesCannotSurrender, c5, cT, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(rulesCannotSurrender, c6, c9, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(rulesCannotSurrender, c7, c8, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testSumsOfSixteen() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                case Five:
                case Six:
                    assertEquals(PlayerDecision.STD, compute(c6, cT, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(c7, c9, upcardValue));
                    break;
                case Seven:
                case Eight:
                    assertEquals(PlayerDecision.HIT, compute(c6, cT, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(c7, c9, upcardValue));
                    break;
                case Nine:
                    assertEquals(PlayerDecision.SUR, compute(rulesCanSurrender, c6, cT, upcardValue));
                    assertEquals(PlayerDecision.SUR, compute(rulesCanSurrender, c7, c9, upcardValue));

                    // handle case where can't surrender due to more than 2 cards in hand
                    assertEquals(PlayerDecision.HIT, compute(rulesCanSurrender, c3, c4, c9, upcardValue));

                    // handle conditions where can't surrender due to table rules
                    assertEquals(PlayerDecision.HIT, compute(rulesCannotSurrender, c6, cT, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(rulesCannotSurrender, c7, c9, upcardValue));
                    break;
                case Ten:
                    assertEquals(PlayerDecision.SUR, compute(rulesCanSurrender, c6, cT, upcardValue));
                    assertEquals(PlayerDecision.SUR, compute(rulesCanSurrender, c7, c9, upcardValue));

                    // handle case where can't surrender due to more than 2 cards in hand
                    assertEquals(PlayerDecision.HIT, compute(rulesCanSurrender, c2, c4, cT, upcardValue));

                    // handle conditions where can't surrender due to table rules
                    assertEquals(PlayerDecision.HIT, compute(rulesCannotSurrender, c6, cT, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(rulesCannotSurrender, c7, c9, upcardValue));
                    break;
                case Ace:
                    assertEquals(PlayerDecision.SUR, compute(rulesCanSurrender, c6, cT, upcardValue));
                    assertEquals(PlayerDecision.SUR, compute(rulesCanSurrender, c7, c9, upcardValue));

                    // handle case where can't surrender due to more than 2 cards in hand
                    assertEquals(PlayerDecision.HIT, compute(rulesCanSurrender, c2, c4, cT, upcardValue));

                    // handle conditions where can't surrender due to table rules
                    assertEquals(PlayerDecision.HIT, compute(rulesCannotSurrender, c6, cT, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(rulesCannotSurrender, c7, c9, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testSumsOfSeventeen() {
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
                    assertEquals(PlayerDecision.STD, compute(c7, cT, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(c8, c9, upcardValue));
                    break;
                case Ace:
                    assertEquals(PlayerDecision.SUR, compute(rulesCanSurrender, c7, cT, upcardValue));
                    assertEquals(PlayerDecision.SUR, compute(rulesCanSurrender, c8, c9, upcardValue));

                    // handle case where can't surrender due to more than 2 cards in hand
                    assertEquals(PlayerDecision.STD, compute(rulesCanSurrender, c2, c5, cT, upcardValue));

                    // handle conditions where can't surrender due to table rules
                    assertEquals(PlayerDecision.STD, compute(rulesCannotSurrender, c7, cT, upcardValue));
                    assertEquals(PlayerDecision.STD, compute(rulesCannotSurrender, c8, c9, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testSumsOfEighteenAndNineteen() {
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            assertEquals(PlayerDecision.STD, compute(c8, cT, upcardValue));
            assertEquals(PlayerDecision.STD, compute(c9, cT, upcardValue));
        }
    }
}
