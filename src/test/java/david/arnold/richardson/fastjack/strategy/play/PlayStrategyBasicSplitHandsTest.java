package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HelperForTests;
import david.arnold.richardson.fastjack.PlayerDecision;
import david.arnold.richardson.fastjack.Rules;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayStrategyBasicSplitHandsTest extends HelperForTests {

    @Test
    public void testPairOfTwosOrThreesDoubleAfterSplit() {
        Rules rules = Rules.getDefaultSixDecks();
        rules.setCanDoubleAfterSplit(true);
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                case Five:
                case Six:
                case Seven:
                    assertEquals(PlayerDecision.SPL, compute(rules, c2, c2, upcardValue));
                    assertEquals(PlayerDecision.SPL, compute(rules, c3, c3, upcardValue));
                    break;
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(rules, c2, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(rules, c3, c3, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testPairOfTwosOrThreesNoDoubleAfterSplit() {
        Rules rules = Rules.getDefaultSixDecks();
        rules.setCanDoubleAfterSplit(false);
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                    assertEquals(PlayerDecision.HIT, compute(rules, c2, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(rules, c3, c3, upcardValue));
                    break;
                case Four:
                case Five:
                case Six:
                case Seven:
                    assertEquals(PlayerDecision.SPL, compute(rules, c2, c2, upcardValue));
                    assertEquals(PlayerDecision.SPL, compute(rules, c3, c3, upcardValue));
                    break;
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(rules, c2, c2, upcardValue));
                    assertEquals(PlayerDecision.HIT, compute(rules, c3, c3, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testPairOfFoursDoubleAfterSplit() {
        Rules rules = Rules.getDefaultSixDecks();
        rules.setCanDoubleAfterSplit(true);
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                    assertEquals(PlayerDecision.HIT, compute(rules, c4, c4, upcardValue));
                    break;
                case Five:
                case Six:
                    assertEquals(PlayerDecision.SPL, compute(rules, c4, c4, upcardValue));
                    break;
                case Seven:
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(rules, c4, c4, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testPairOfFoursNoDoubleAfterSplit() {
        Rules rules = Rules.getDefaultSixDecks();
        rules.setCanDoubleAfterSplit(false);
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
                    assertEquals(PlayerDecision.HIT, compute(rules, c4, c4, upcardValue));
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
    public void testPairOfSixesDoubleAfterSplit() {
        Rules rules = Rules.getDefaultSixDecks();
        rules.setCanDoubleAfterSplit(true);
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                case Four:
                case Five:
                case Six:
                    assertEquals(PlayerDecision.SPL, compute(rules, c6, c6, upcardValue));
                    break;
                case Seven:
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(rules, c6, c6, upcardValue));
                    break;
            }
        }
    }

    @Test
    public void testPairOfSixesNoDoubleAfterSplit() {
        Rules rules = Rules.getDefaultSixDecks();
        rules.setCanDoubleAfterSplit(false);
        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                    assertEquals(PlayerDecision.HIT, compute(rules, c6, c6, upcardValue));
                    break;
                case Three:
                case Four:
                case Five:
                case Six:
                    assertEquals(PlayerDecision.SPL, compute(rules, c6, c6, upcardValue));
                    break;
                case Seven:
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.HIT, compute(rules, c6, c6, upcardValue));
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
                case Ten:
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
        Rules rules00 = Rules.getDefaultSixDecks();
        rules00.setCanHitSplitAces(false);
        rules00.setCanResplitAces(false);

        Rules rules01 = Rules.getDefaultSixDecks();
        rules01.setCanHitSplitAces(false);
        rules01.setCanResplitAces(true);

        Rules rules10 = Rules.getDefaultSixDecks();
        rules10.setCanHitSplitAces(true);
        rules10.setCanResplitAces(false);

        Rules rules11 = Rules.getDefaultSixDecks();
        rules11.setCanHitSplitAces(true);
        rules11.setCanResplitAces(true);

        for (int upcardValue = 1; upcardValue < 11; upcardValue++) {
            switch (upcardValue) {
                case Two:
                case Three:
                    assertEquals(PlayerDecision.SPL, compute(rules00, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.SPL, compute(rules01, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.SPL, compute(rules10, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.SPL, compute(rules11, cA, cA, upcardValue));

                    assertEquals(PlayerDecision.STD, computeButAlreadySplitOnce(rules00, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.SPL, computeButAlreadySplitOnce(rules01, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, computeButAlreadySplitOnce(rules10, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.SPL, computeButAlreadySplitOnce(rules11, cA, cA, upcardValue));

                    assertEquals(PlayerDecision.STD, computeButMaxSplitsReached(rules00, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.STD, computeButMaxSplitsReached(rules01, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, computeButMaxSplitsReached(rules10, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, computeButMaxSplitsReached(rules11, cA, cA, upcardValue));

                    break;
                case Four:
                case Five:
                case Six:
                    assertEquals(PlayerDecision.SPL, compute(rules00, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.SPL, compute(rules01, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.SPL, compute(rules10, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.SPL, compute(rules11, cA, cA, upcardValue));

                    assertEquals(PlayerDecision.STD, computeButAlreadySplitOnce(rules00, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.SPL, computeButAlreadySplitOnce(rules01, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.STD, computeButAlreadySplitOnce(rules10, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.SPL, computeButAlreadySplitOnce(rules11, cA, cA, upcardValue));

                    assertEquals(PlayerDecision.STD, computeButMaxSplitsReached(rules00, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.STD, computeButMaxSplitsReached(rules01, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.STD, computeButMaxSplitsReached(rules10, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.STD, computeButMaxSplitsReached(rules11, cA, cA, upcardValue));

                    break;
                case Seven:
                case Eight:
                case Nine:
                case Ten:
                case Ace:
                    assertEquals(PlayerDecision.SPL, compute(rules00, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.SPL, compute(rules01, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.SPL, compute(rules10, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.SPL, compute(rules11, cA, cA, upcardValue));

                    assertEquals(PlayerDecision.STD, computeButAlreadySplitOnce(rules00, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.SPL, computeButAlreadySplitOnce(rules01, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, computeButAlreadySplitOnce(rules10, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.SPL, computeButAlreadySplitOnce(rules11, cA, cA, upcardValue));

                    assertEquals(PlayerDecision.STD, computeButMaxSplitsReached(rules00, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.STD, computeButMaxSplitsReached(rules01, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, computeButMaxSplitsReached(rules10, cA, cA, upcardValue));
                    assertEquals(PlayerDecision.HIT, computeButMaxSplitsReached(rules11, cA, cA, upcardValue));

                    break;
            }
        }
    }
}
