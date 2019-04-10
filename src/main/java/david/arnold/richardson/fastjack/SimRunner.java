package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.bet.BetStrategyAlwaysMin;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategyAlwaysStand;

public class SimRunner {
    public void run() {
        Outputter outputter = new OutputterVerboseToScreen();
        Rules rules = Rules.getWendover6D();
        Table table = new Table(outputter, rules);

        Player player1 = new Player(
                "Abe",
                200000L,
                new PlayStrategyAlwaysStand(),
                new BetStrategyAlwaysMin(),
                rules,
                table);
        table.addPlayer(player1);
        table.playRounds(1);
        outputter.showMessage(player1.toString());
    }
}
