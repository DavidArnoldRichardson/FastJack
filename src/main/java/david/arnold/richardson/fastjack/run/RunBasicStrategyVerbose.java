package david.arnold.richardson.fastjack.run;

import david.arnold.richardson.fastjack.Player;
import david.arnold.richardson.fastjack.Rules;
import david.arnold.richardson.fastjack.Table;
import david.arnold.richardson.fastjack.strategy.bet.BetStrategy;
import david.arnold.richardson.fastjack.strategy.bet.BetStrategyAlwaysMin;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategy;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategyBasic;

public class RunBasicStrategyVerbose extends SimRunner {

    public static void main(String... args) {
        new RunBasicStrategyVerbose().run();
    }

    @Override
    protected Rules getRules() {
        this.playIsVerbose = true;
        return Rules.getWendover6D();
    }

    @Override
    public int runHelper(Rules rules) {
        for (int i = 0; i < Table.NUM_SEATS; i++) {
            Player player = new Player(
                    playerNames[i],
                    100000L,
                    table);
            PlayStrategy playStrategy = new PlayStrategyBasic(rules);
            BetStrategy betStrategy = new BetStrategyAlwaysMin(player, rules);
            player.setStrategies(playStrategy, betStrategy);
            table.addPlayer(player);
        }

        return table.playRounds(10);
    }
}
