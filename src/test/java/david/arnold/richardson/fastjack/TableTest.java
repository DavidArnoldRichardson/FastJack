package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.bet.BetStrategyAlwaysMin;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategyBasic;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TableTest {

    Rules rules;
    Table table;
    Player player;
    long startBankroll = 10000L;
    long minBetAmount;

    @Before
    public void setup() {
        rules = Rules.getDefaultSixDecks();
        rules.setRandomness(new Randomness(1L));
        Outputter outputter = new OutputterVerboseToScreen();
        table = new Table(outputter, rules);
        player = new Player("test", startBankroll);
        player.setStrategies(new PlayStrategyBasic(rules), new BetStrategyAlwaysMin(player, rules));
        table.addPlayer(player);
        minBetAmount = rules.getMinBetAmount();
    }

    void setCardsAndPlay(int... cardValues) {
        table.tweakShoe(cardValues);
        table.playRound(0);
    }

    //////////////
    //////////////  simple
    //////////////

    @Test
    public void testSimplePlayerLosesDueToTotalLessThanDealer() {
        setCardsAndPlay(10, 10, 7, 10, 5, 6);
        assertEquals(startBankroll - minBetAmount, player.getBankroll());
        assertEquals(minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSimplePlayerLosesDueToTotalLessThanDealerWithHits() {
        setCardsAndPlay(3, 10, 4, 10, 5, 6);
        assertEquals(startBankroll - minBetAmount, player.getBankroll());
        assertEquals(minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSimplePlayerLosesDueToBust() {
        setCardsAndPlay(3, 10, 4, 10, 5, 10);
        assertEquals(startBankroll - minBetAmount, player.getBankroll());
        assertEquals(minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSimplePlayerWins() {
        setCardsAndPlay(3, 10, 4, 7, 5, 6);
        assertEquals(startBankroll + minBetAmount, player.getBankroll());
        assertEquals(-minBetAmount, table.getTableBankrollDelta());
    }

    //////////////
    //////////////  blackjacks
    //////////////

    @Test
    public void testBlackjackPlayerLoses() {
        setCardsAndPlay(10, 1, 10, 10);
        assertEquals(startBankroll - minBetAmount, player.getBankroll());
        assertEquals(minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testBlackjackPlayerWins() {
        setCardsAndPlay(1, 10, 10, 7);
        assertEquals(startBankroll + minBetAmount + (minBetAmount >> 1), player.getBankroll());
        assertEquals(-(minBetAmount + (minBetAmount >> 1)), table.getTableBankrollDelta());
    }

    //////////////
    //////////////  doubles
    //////////////

    @Test
    public void testDoublePlayerLoses() {
        setCardsAndPlay(6, 8, 5, 9, 2);
        assertEquals(startBankroll - minBetAmount - minBetAmount, player.getBankroll());
        assertEquals(minBetAmount + minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testDoublePlayerLosesWithDealerHits() {
        setCardsAndPlay(6, 8, 5, 2, 2, 9);
        assertEquals(startBankroll - minBetAmount - minBetAmount, player.getBankroll());
        assertEquals(minBetAmount + minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testDoublePlayerWins() {
        setCardsAndPlay(6, 8, 5, 9, 9);
        assertEquals(startBankroll + minBetAmount + minBetAmount, player.getBankroll());
        assertEquals(-minBetAmount - minBetAmount, table.getTableBankrollDelta());
    }

    //////////////
    //////////////  split eights
    //////////////

    @Test
    public void testSplitEightsPlayerLosesBoth() {
        setCardsAndPlay(8, 10, 8, 10, 9, 9);
        assertEquals(startBankroll - minBetAmount - minBetAmount, player.getBankroll());
        assertEquals(minBetAmount + minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitEightsPlayerWinsBoth() {
        setCardsAndPlay(8, 10, 8, 7, 10, 10);
        assertEquals(startBankroll + minBetAmount + minBetAmount, player.getBankroll());
        assertEquals(-minBetAmount - minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitEightsPlayerWinsAndLoses() {
        setCardsAndPlay(8, 10, 8, 7, 10, 4, 10);
        assertEquals(startBankroll, player.getBankroll());
        assertEquals(0L, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitEightsPlayerPushesBoth() {
        setCardsAndPlay(8, 10, 8, 8, 10, 10);
        assertEquals(startBankroll, player.getBankroll());
        assertEquals(0L, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitEightsWithResplitWinsAll() {
        setCardsAndPlay(8, 10, 8, 7, 8, 10, 10, 10);
        assertEquals(startBankroll + (minBetAmount * 3), player.getBankroll());
        assertEquals(-3 * minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitEightsWithResplitLosesAll() {
        setCardsAndPlay(8, 10, 8, 10, 8, 10, 10, 10);
        assertEquals(startBankroll - (minBetAmount * 3), player.getBankroll());
        assertEquals(3 * minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitEightsWithResplitLosesTwoWinsOne() {
        setCardsAndPlay(8, 10, 8, 10, 8, 4, 10, 3, 10, 10);
        assertEquals(startBankroll - minBetAmount, player.getBankroll());
        assertEquals(minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitEightsWithResplitLosesOneWinsTwo() {
        setCardsAndPlay(8, 10, 8, 7, 8, 10, 4, 10, 10);
        assertEquals(startBankroll + minBetAmount, player.getBankroll());
        assertEquals(-minBetAmount, table.getTableBankrollDelta());
    }

    //////////////
    //////////////  split aces
    //////////////

    @Test
    public void testSplitAcesPlayerWinsBoth() {
        setCardsAndPlay(1, 10, 1, 7, 9, 9);
        assertEquals(startBankroll + minBetAmount + minBetAmount, player.getBankroll());
        assertEquals(-minBetAmount - minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitAcesPlayerLosesBoth() {
        setCardsAndPlay(1, 10, 1, 7, 2, 10, 10, 2, 10, 10);
        assertEquals(startBankroll - minBetAmount - minBetAmount, player.getBankroll());
        assertEquals(minBetAmount + minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitAcesPlayerLosesAndWins() {
        setCardsAndPlay(1, 10, 1, 7, 2, 10, 10, 9);
        assertEquals(startBankroll, player.getBankroll());
        assertEquals(0L, table.getTableBankrollDelta());
    }
}
