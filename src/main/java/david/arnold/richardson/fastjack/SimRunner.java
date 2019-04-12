package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.bet.BetStrategy;
import david.arnold.richardson.fastjack.strategy.bet.BetStrategyAlwaysMin;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategy;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategyBasic;

public class SimRunner {
    public void run() {
        Outputter outputter = new OutputterVerboseToScreen();
        Rules rules = Rules.getWendover6D();
//        rules.setSeed(1L);
        System.out.println("FastJack Seed: " + rules.getRandomness().getSeed());
        Table table = new Table(outputter, rules);

        Player player1 = new Player(
                "Abe",
                200000L,
                rules,
                table);
        PlayStrategy playStrategy = new PlayStrategyBasic(rules);
        BetStrategy betStrategy = new BetStrategyAlwaysMin(player1, rules);
        player1.setStrategies(playStrategy, betStrategy);
        table.addPlayer(player1);

        int numRoundsPlayed = table.playRounds(1);
        outputter.showMessage("Played " + numRoundsPlayed + " rounds.");
        outputter.showMessage(player1.showResult());
        long tableBankrollDelta = table.getTableBankrollDelta();
        if (tableBankrollDelta < 0L) {
            outputter.showMessage("Casino paid the players a total of " + MoneyHelper.formatForDisplay(-tableBankrollDelta) + ".");
        } else if (tableBankrollDelta > 0L) {
            outputter.showMessage("Casino took " + MoneyHelper.formatForDisplay(tableBankrollDelta) + " from the players.");
        } else {
            outputter.showMessage("Casino was a wash.");
        }
    }
}
