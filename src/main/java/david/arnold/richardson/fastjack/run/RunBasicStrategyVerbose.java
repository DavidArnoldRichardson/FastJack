package david.arnold.richardson.fastjack.run;

import david.arnold.richardson.fastjack.*;
import david.arnold.richardson.fastjack.strategy.bet.BetStrategy;
import david.arnold.richardson.fastjack.strategy.bet.BetStrategyAlwaysMin;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategy;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategyBasic;

public class RunBasicStrategyVerbose extends SimRunner {

    @Override
    public void runHelper() {
        Outputter outputterVerbose = new OutputterVerboseToScreen();
        Rules rules = Rules.getWendover6D();
        System.out.println("FastJack Seed: " + rules.getRandomness().getSeed());
        Table table = new Table(outputterVerbose, rules);

        Player player = new Player(
                "Abe",
                100000L,
                table);
        PlayStrategy playStrategy = new PlayStrategyBasic(rules);
        BetStrategy betStrategy = new BetStrategyAlwaysMin(player, rules);
        player.setStrategies(playStrategy, betStrategy);
        table.addPlayer(player);

        int numRoundsPlayed = table.playRounds(10);
        outputterVerbose.showMessage("\nPlayed " + numRoundsPlayed + " rounds.");
        outputterVerbose.showMessage(player.showResult());

        long playersDelta = player.getBankroll() - player.getInitialBankroll();
        long tableBankrollDelta = table.getTableBankrollDelta();
        if (tableBankrollDelta < 0L) {
            outputterVerbose.showMessage("Casino paid the players a total of " + MoneyHelper.formatForDisplay(-tableBankrollDelta) + ".");
        } else if (tableBankrollDelta > 0L) {
            outputterVerbose.showMessage("Casino took " + MoneyHelper.formatForDisplay(tableBankrollDelta) + " from the players.");
        } else {
            outputterVerbose.showMessage("Casino was a wash.");
        }

        if (playersDelta != -tableBankrollDelta) {
            System.out.println("WHAT! Accounting error. Players delta: " + playersDelta + " but table delta: " + tableBankrollDelta);
        }
    }
}
