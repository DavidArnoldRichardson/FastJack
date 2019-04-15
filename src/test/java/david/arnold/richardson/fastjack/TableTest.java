package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.bet.BetStrategyAlwaysMin;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategyBasic;
import org.junit.Test;

public class TableTest {
    @Test
    public void testSimplePlay() {
        Rules rules = Rules.getDefaultSixDecks();
        rules.setRandomness(new Randomness(1L));
        Outputter outputter = new OutputterVerboseToScreen();
        Table table = new Table(outputter, rules);
        Player player = new Player("test", 100000L, table);
        player.setStrategies(new PlayStrategyBasic(rules), new BetStrategyAlwaysMin(player, rules));
        table.addPlayer(player);

        table.tweakShoe(3, 10, 4, 10, 5, 6);

        table.playRounds(1);
    }
}
