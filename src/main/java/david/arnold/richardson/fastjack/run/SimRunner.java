package david.arnold.richardson.fastjack.run;

import david.arnold.richardson.fastjack.*;

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

        int numRoundsPlayed = runHelper(rules);
        showResult(numRoundsPlayed);
    }

    public abstract int runHelper(Rules rules);

    protected abstract Rules getRules();

    private void showResult(int numRoundsPlayed) {
        outputterVerbose.showMessage("\nPlayed " + numRoundsPlayed + " rounds.\n");

        for (Player player : table.getPlayers()) {
            outputterVerbose.showMessage(player.showResult());
        }
        System.out.println();

        long tableBankrollDelta = table.getTableBankrollDelta();
        if (tableBankrollDelta < 0L) {
            outputterVerbose.showMessage("Casino paid the players a total of "
                    + MoneyHelper.formatForDisplay(-tableBankrollDelta) + ".");
        } else if (tableBankrollDelta > 0L) {
            outputterVerbose.showMessage("Casino took " + MoneyHelper.formatForDisplay(tableBankrollDelta)
                    + " from the players.");
        } else {
            outputterVerbose.showMessage("Casino was a wash.");
        }

        long playersBankrollDelta = computePlayersBankrollDelta();
        if (playersBankrollDelta != -tableBankrollDelta) {
            System.out.println("WHAT! Accounting error. Players delta: "
                    + playersBankrollDelta + " but table delta: " + tableBankrollDelta);
        }
    }

    private long computePlayersBankrollDelta() {
        long delta = 0L;
        for (Player player : table.getPlayers()) {
            delta += player.getBankroll() - player.getInitialBankroll();
        }
        return delta;
    }
}
