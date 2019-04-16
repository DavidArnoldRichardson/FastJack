package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.bet.BetStrategyAlwaysMin;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategyBasic;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TableTest {
    @Test
    public void testSimplePlayerLoses() {
        long startBankroll = 10000L;
        Rules rules = Rules.getDefaultSixDecks();
        rules.setRandomness(new Randomness(1L));
        Outputter outputter = new OutputterVerboseToScreen();
        Table table = new Table(outputter, rules);
        Player player = new Player("test", startBankroll, table);
        player.setStrategies(new PlayStrategyBasic(rules), new BetStrategyAlwaysMin(player, rules));
        table.addPlayer(player);

        table.tweakShoe(3, 10, 4, 10, 5, 6);
        table.playRound(0);
        long minBetAmount = rules.getMinBetAmount();
        assertEquals(startBankroll - minBetAmount, player.getBankroll());
        assertEquals(minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSimplePlayerWins() {
        long startBankroll = 10000L;
        Rules rules = Rules.getDefaultSixDecks();
        rules.setRandomness(new Randomness(1L));
        Outputter outputter = new OutputterVerboseToScreen();
        Table table = new Table(outputter, rules);
        Player player = new Player("test", startBankroll, table);
        player.setStrategies(new PlayStrategyBasic(rules), new BetStrategyAlwaysMin(player, rules));
        table.addPlayer(player);

        table.tweakShoe(3, 10, 4, 7, 5, 6);
        table.playRound(0);
        long minBetAmount = rules.getMinBetAmount();
        assertEquals(startBankroll + minBetAmount, player.getBankroll());
        assertEquals(-minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testBlackjackPlayerLoses() {
        long startBankroll = 10000L;
        Rules rules = Rules.getDefaultSixDecks();
        rules.setRandomness(new Randomness(1L));
        Outputter outputter = new OutputterVerboseToScreen();
        Table table = new Table(outputter, rules);
        Player player = new Player("test", startBankroll, table);
        player.setStrategies(new PlayStrategyBasic(rules), new BetStrategyAlwaysMin(player, rules));
        table.addPlayer(player);

        table.tweakShoe(10, 1, 10, 10);
        table.playRound(0);
        long minBetAmount = rules.getMinBetAmount();
        assertEquals(startBankroll - minBetAmount, player.getBankroll());
        assertEquals(minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testBlackjackPlayerWins() {
        long startBankroll = 10000L;
        Rules rules = Rules.getDefaultSixDecks();
        rules.setRandomness(new Randomness(1L));
        Outputter outputter = new OutputterVerboseToScreen();
        Table table = new Table(outputter, rules);
        Player player = new Player("test", startBankroll, table);
        player.setStrategies(new PlayStrategyBasic(rules), new BetStrategyAlwaysMin(player, rules));
        table.addPlayer(player);

        table.tweakShoe(1, 10, 10, 7);
        table.playRound(0);
        long minBetAmount = rules.getMinBetAmount();
        assertEquals(startBankroll + minBetAmount + (minBetAmount >> 1), player.getBankroll());
        assertEquals(-(minBetAmount + (minBetAmount >> 1)), table.getTableBankrollDelta());
    }

    @Test
    public void testDoublePlayerLoses() {
        long startBankroll = 10000L;
        Rules rules = Rules.getDefaultSixDecks();
        rules.setRandomness(new Randomness(1L));
        Outputter outputter = new OutputterVerboseToScreen();
        Table table = new Table(outputter, rules);
        Player player = new Player("test", startBankroll, table);
        player.setStrategies(new PlayStrategyBasic(rules), new BetStrategyAlwaysMin(player, rules));
        table.addPlayer(player);

        table.tweakShoe(6, 8, 5, 9, 2);
        table.playRound(0);
        long minBetAmount = rules.getMinBetAmount();
        assertEquals(startBankroll - minBetAmount - minBetAmount, player.getBankroll());
        assertEquals(minBetAmount + minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testDoublePlayerWins() {
        long startBankroll = 10000L;
        Rules rules = Rules.getDefaultSixDecks();
        rules.setRandomness(new Randomness(1L));
        Outputter outputter = new OutputterVerboseToScreen();
        Table table = new Table(outputter, rules);
        Player player = new Player("test", startBankroll, table);
        player.setStrategies(new PlayStrategyBasic(rules), new BetStrategyAlwaysMin(player, rules));
        table.addPlayer(player);

        table.tweakShoe(6, 8, 5, 9, 9);
        table.playRound(0);
        long minBetAmount = rules.getMinBetAmount();
        assertEquals(startBankroll + minBetAmount + minBetAmount, player.getBankroll());
        assertEquals(-minBetAmount - minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitEightsPlayerLosesBoth() {
        long startBankroll = 10000L;
        Rules rules = Rules.getDefaultSixDecks();
        rules.setRandomness(new Randomness(1L));
        Outputter outputter = new OutputterVerboseToScreen();
        Table table = new Table(outputter, rules);
        Player player = new Player("test", startBankroll, table);
        player.setStrategies(new PlayStrategyBasic(rules), new BetStrategyAlwaysMin(player, rules));
        table.addPlayer(player);

        table.tweakShoe(8, 10, 8, 10, 9, 9);
        table.playRound(0);
        long minBetAmount = rules.getMinBetAmount();
        assertEquals(startBankroll - minBetAmount - minBetAmount, player.getBankroll());
        assertEquals(minBetAmount + minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitEightsPlayerWinsBoth() {
        long startBankroll = 10000L;
        Rules rules = Rules.getDefaultSixDecks();
        rules.setRandomness(new Randomness(1L));
        Outputter outputter = new OutputterVerboseToScreen();
        Table table = new Table(outputter, rules);
        Player player = new Player("test", startBankroll, table);
        player.setStrategies(new PlayStrategyBasic(rules), new BetStrategyAlwaysMin(player, rules));
        table.addPlayer(player);

        table.tweakShoe(8, 10, 8, 7, 10, 10);
        table.playRound(0);
        long minBetAmount = rules.getMinBetAmount();
        assertEquals(startBankroll + minBetAmount + minBetAmount, player.getBankroll());
        assertEquals(-minBetAmount - minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitEightsPlayerWinsAndLoses() {
        long startBankroll = 10000L;
        Rules rules = Rules.getDefaultSixDecks();
        rules.setRandomness(new Randomness(1L));
        Outputter outputter = new OutputterVerboseToScreen();
        Table table = new Table(outputter, rules);
        Player player = new Player("test", startBankroll, table);
        player.setStrategies(new PlayStrategyBasic(rules), new BetStrategyAlwaysMin(player, rules));
        table.addPlayer(player);

        table.tweakShoe(8, 10, 8, 7, 10, 4, 10);
        table.playRound(0);
        assertEquals(startBankroll, player.getBankroll());
        assertEquals(0L, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitEightsPlayerPushesBoth() {
        long startBankroll = 10000L;
        Rules rules = Rules.getDefaultSixDecks();
        rules.setRandomness(new Randomness(1L));
        Outputter outputter = new OutputterVerboseToScreen();
        Table table = new Table(outputter, rules);
        Player player = new Player("test", startBankroll, table);
        player.setStrategies(new PlayStrategyBasic(rules), new BetStrategyAlwaysMin(player, rules));
        table.addPlayer(player);

        table.tweakShoe(8, 10, 8, 8, 10, 10);
        table.playRound(0);
        assertEquals(startBankroll, player.getBankroll());
        assertEquals(0L, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitAcesPlayerWinsBoth() {
        long startBankroll = 10000L;
        Rules rules = Rules.getDefaultSixDecks();
        rules.setRandomness(new Randomness(1L));
        Outputter outputter = new OutputterVerboseToScreen();
        Table table = new Table(outputter, rules);
        Player player = new Player("test", startBankroll, table);
        player.setStrategies(new PlayStrategyBasic(rules), new BetStrategyAlwaysMin(player, rules));
        table.addPlayer(player);

        table.tweakShoe(1, 10, 1, 7, 9, 9);
        table.playRound(0);
        long minBetAmount = rules.getMinBetAmount();
        assertEquals(startBankroll + minBetAmount + minBetAmount, player.getBankroll());
        assertEquals(-minBetAmount - minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitAcesPlayerLosesBoth() {
        long startBankroll = 10000L;
        Rules rules = Rules.getDefaultSixDecks();
        rules.setRandomness(new Randomness(1L));
        Outputter outputter = new OutputterVerboseToScreen();
        Table table = new Table(outputter, rules);
        Player player = new Player("test", startBankroll, table);
        player.setStrategies(new PlayStrategyBasic(rules), new BetStrategyAlwaysMin(player, rules));
        table.addPlayer(player);

        table.tweakShoe(1, 10, 1, 7, 2, 10, 10, 2, 10, 10);
        table.playRound(0);
        long minBetAmount = rules.getMinBetAmount();
        assertEquals(startBankroll - minBetAmount - minBetAmount, player.getBankroll());
        assertEquals(minBetAmount + minBetAmount, table.getTableBankrollDelta());
    }

    @Test
    public void testSplitAcesPlayerLosesAndWins() {
        long startBankroll = 10000L;
        Rules rules = Rules.getDefaultSixDecks();
        rules.setRandomness(new Randomness(1L));
        Outputter outputter = new OutputterVerboseToScreen();
        Table table = new Table(outputter, rules);
        Player player = new Player("test", startBankroll, table);
        player.setStrategies(new PlayStrategyBasic(rules), new BetStrategyAlwaysMin(player, rules));
        table.addPlayer(player);

        table.tweakShoe(1, 10, 1, 7, 2, 10, 10, 9);
        table.playRound(0);
        assertEquals(startBankroll, player.getBankroll());
        assertEquals(0L, table.getTableBankrollDelta());
    }
}
