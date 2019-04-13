package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.bet.BetStrategy;
import david.arnold.richardson.fastjack.strategy.bet.BetStrategyAlwaysMin;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategy;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategyBasic;

public class SimRunner {
    public void run() {
        Outputter outputterVerbose = new OutputterVerboseToScreen();
        Outputter outputterSilent = new OutputterSilentAndFast();
        Rules rules = Rules.getWendover6D();
        System.out.println("FastJack Seed: " + rules.getRandomness().getSeed());
        Table table = new Table(outputterSilent, rules);

        Player player1 = new Player(
                "Abe",
                200000000L,
                table);
        PlayStrategy playStrategy = new PlayStrategyBasic(rules);
        BetStrategy betStrategy = new BetStrategyAlwaysMin(player1, rules);
        player1.setStrategies(playStrategy, betStrategy);
        table.addPlayer(player1);

        int numRoundsPlayed = table.playRounds(10000000);
        outputterVerbose.showMessage("\nPlayed " + numRoundsPlayed + " rounds.");
        outputterVerbose.showMessage(player1.showResult());
        long tableBankrollDelta = table.getTableBankrollDelta();
        if (tableBankrollDelta < 0L) {
            outputterVerbose.showMessage("Casino paid the players a total of " + MoneyHelper.formatForDisplay(-tableBankrollDelta) + ".");
        } else if (tableBankrollDelta > 0L) {
            outputterVerbose.showMessage("Casino took " + MoneyHelper.formatForDisplay(tableBankrollDelta) + " from the players.");
        } else {
            outputterVerbose.showMessage("Casino was a wash.");
        }
    }
}
