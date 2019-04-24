package david.arnold.richardson.fastjack.run;

import david.arnold.richardson.fastjack.*;

public class SimRunResult {
    int numRoundsPlayed;
    int numShoesPlayed;

    public SimRunResult(RoundRunStats roundRunStats) {
        this.numRoundsPlayed = roundRunStats.getRoundNumber();
        this.numShoesPlayed = roundRunStats.getNumShoesPlayed();
    }

    public void show(
            Outputter outputter,
            Table table,
            long numMillis) {
        numMillis = numMillis <= 0 ? 1 : numMillis;
        long handsPerMillisecond = ((long) numRoundsPlayed * (long) table.getPlayers().size()) / numMillis;
        outputter.showMessage("\nPlayed " + numRoundsPlayed
                + " rounds (" + numShoesPlayed + " shoes) in " + numMillis
                + " milliseconds (" + (handsPerMillisecond * 1000) + " hands per second).\n");

        double sumOfEdges = 0.0D;
        int numPlayers = 0;
        for (Player player : table.getPlayers()) {
            numPlayers++;
            sumOfEdges += player.getEdge();
            outputter.showMessage(player.showResult());
        }
        double averageEdge = sumOfEdges / (double) numPlayers;
        double expectedEdge = table.getRules().getPlayerEdge();
        double difference = expectedEdge - averageEdge;
        outputter.showMessage("Average player edge: "
                + Player.formatEdge(averageEdge)
                + ". Expected player edge: " + Player.formatEdge(expectedEdge)
                + ". Difference: " + Player.formatEdge(difference)
                + ". Rules: " + table.getRules().showSummary());
        System.out.println();

        long tableBankrollDelta = table.getTableBankrollDelta();
        if (tableBankrollDelta < 0L) {
            outputter.showMessage("Casino paid the players a total of "
                    + MoneyPile.show(-tableBankrollDelta) + ".");
        } else if (tableBankrollDelta > 0L) {
            outputter.showMessage("Casino took " + MoneyPile.show(tableBankrollDelta)
                    + " from the players.");
        } else {
            outputter.showMessage("Casino was a wash.");
        }

        long playersBankrollDelta = table.computePlayersBankrollDelta();
        if (playersBankrollDelta != -tableBankrollDelta) {
            String errorMessage = "WHAT! Accounting error. Players delta: "
                    + MoneyPile.show(playersBankrollDelta)
                    + " but table delta: " + MoneyPile.show(tableBankrollDelta)
                    + ". Seed=" + table.getRules().getRandomness().getSeed();
            outputter.showMessage(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }
}
