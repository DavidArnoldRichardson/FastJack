package david.arnold.richardson.fastjack.run;

import david.arnold.richardson.fastjack.*;

import java.time.Duration;
import java.time.Instant;

public abstract class SimRunner {

    protected String[] playerNames = new String[]{"Alex", "Beth", "Carl", "Dora", "Eric", "Fran", "Gary"};
    protected Outputter outputterVerbose = new OutputterVerboseToScreen();
    protected Outputter outputterSilent = new OutputterSilentAndFast();
    protected Table table;
    protected boolean playIsVerbose;

    public void run() {
        Rules rules = getRules();

        table = new Table(
                playIsVerbose ? outputterVerbose : outputterSilent,
                rules);

        Instant startTime = Instant.now();
        int numRoundsPlayed = runHelper(table);
        Instant completionTime = Instant.now();
        long timeElapsedInMillis = Duration.between(startTime, completionTime).toMillis();
        showResult(numRoundsPlayed, timeElapsedInMillis);
    }

    public abstract int runHelper(Table table);

    protected abstract Rules getRules();

    protected abstract int getNumPlayers();

    private void showResult(
            int numRoundsPlayed,
            long numMillis) {
        numMillis = numMillis <= 0 ? 1 : numMillis;
        long handsPerMillisecond = ((long)numRoundsPlayed * (long) getNumPlayers()) / numMillis;
        outputterVerbose.showMessage("\nPlayed " + numRoundsPlayed
                + " rounds in " + numMillis
                + " milliseconds (" + (handsPerMillisecond * 1000) + " hands per second).\n");

        for (Player player : table.getPlayers()) {
            outputterVerbose.showMessage(player.showResult());
        }
        System.out.println();

        long tableBankrollDelta = table.getTableBankrollDelta();
        if (tableBankrollDelta < 0L) {
            outputterVerbose.showMessage("Casino paid the players a total of "
                    + MoneyPile.show(-tableBankrollDelta) + ".");
        } else if (tableBankrollDelta > 0L) {
            outputterVerbose.showMessage("Casino took " + MoneyPile.show(tableBankrollDelta)
                    + " from the players.");
        } else {
            outputterVerbose.showMessage("Casino was a wash.");
        }

        long playersBankrollDelta = computePlayersBankrollDelta();
        if (playersBankrollDelta != -tableBankrollDelta) {
            String errorMessage = "WHAT! Accounting error. Players delta: "
                    + MoneyPile.show(playersBankrollDelta)
                    + " but table delta: " + MoneyPile.show(tableBankrollDelta)
                    + ". Seed=" + getRules().getRandomness().getSeed();
            outputterVerbose.showMessage(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    private long computePlayersBankrollDelta() {
        long delta = 0L;
        for (Player player : table.getPlayers()) {
            delta += (player.getMoneyPile().getAmount() - player.getInitialBankrollAmount());
        }
        return delta;
    }
}
