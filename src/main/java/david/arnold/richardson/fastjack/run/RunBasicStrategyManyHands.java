package david.arnold.richardson.fastjack.run;

import david.arnold.richardson.fastjack.Player;
import david.arnold.richardson.fastjack.PlayerEdge;
import david.arnold.richardson.fastjack.Rules;
import david.arnold.richardson.fastjack.Table;
import david.arnold.richardson.fastjack.strategy.bet.BetStrategy;
import david.arnold.richardson.fastjack.strategy.bet.BetStrategyAlwaysMin;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategy;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategyBasic;

public class RunBasicStrategyManyHands extends SimRunner {

    public static void main(String... args) {
        new RunBasicStrategyManyHands().run();
    }

    @Override
    protected Rules getRules() {
        this.playIsVerbose = false;
        this.captureSummaryLogs = false;
        return PlayerEdge.H17_DAS.getRules(6, 78, 78);
    }

    @Override
    protected int getNumPlayers() {
        return Table.NUM_SEATS;
    }

    @Override
    public SimRunResult runHelper(Table table) {
        if (captureSummaryLogs) {
            table.getOutputter().startCapturingSummaryLogs(table.getRules());
        }

        for (int i = 0; i < getNumPlayers(); i++) {
            Player player = new Player(
                    playerNames[i],
                    1000000000L);
            PlayStrategy playStrategy = new PlayStrategyBasic(table);
            BetStrategy betStrategy = new BetStrategyAlwaysMin(table);
            player.setStrategies(playStrategy, betStrategy);
            table.addPlayer(player);
        }

        return table.playRounds(44300000);
    }
}
