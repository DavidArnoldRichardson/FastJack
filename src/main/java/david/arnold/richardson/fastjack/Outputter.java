package david.arnold.richardson.fastjack;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// Note: Why all these methods? Why not just use showMessage with a string?
// To save CPU. Don't allocate memory and do operations unless needed.
public abstract class Outputter {

    private BufferedWriter bufferedWriterPlaySummary;
    private boolean isLogging;

    public Outputter() {
        isLogging = false;
    }

    private void openLogs() {
        isLogging = true;
        Path pathPlaySummary = Paths.get("play_summary.csv");
        try {
            bufferedWriterPlaySummary = Files.newBufferedWriter(pathPlaySummary);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeLogs() {
        if (isLogging) {
            try {
                bufferedWriterPlaySummary.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeToPlaySummaryLog(String line) {
        if (isLogging) {
            writeLineToFile(bufferedWriterPlaySummary, line);
        }
    }

    private void writeLineToFile(
            BufferedWriter writer,
            String line) {
        try {
            writer.write(line + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startCapturingSummaryLogs() {
        openLogs();
    }

    public abstract boolean isDisplaying();

    public boolean isLogging() {
        return isLogging;
    }

    public abstract boolean usingCarefulAccounting();

    public abstract void showMessage(String message);

    public abstract void startRound(int roundNumber);

    public abstract void cutCardWasDrawn();

    public abstract void shuffle(int numDecks);

    public abstract void placeCutCard(Shoe shoe);

    public abstract void showBurnCards(Shoe shoe);

    public abstract void showDealerUpcard(HandForDealer handForDealer);

    public abstract void roundAborted();

    public abstract void placeBet(Seat seat, int handIndex, long desiredBetAmount);

    public abstract void sitPlayer(Seat seat);

    public abstract void showDealtHand(Seat seat);

    public abstract void dealerUpcardIsAce();

    public abstract void insuranceBetMade(Seat seat);

    public abstract void revealDealerHand(HandForDealer handForDealer);

    public abstract long playerWinsInsuranceBet(Seat seat);

    public abstract void pushOnDealerBlackjack(Seat seat);

    public abstract long loseOnDealerBlackjack(Seat seat);

    public abstract long playerBlackjackAndWins(Seat seat, HandForPlayer hand);

    public abstract void playerStand(Seat seat, HandForPlayer hand);

    public abstract long playerHitAndBust(Seat seat, HandForPlayer hand);

    public abstract void playerHitAndGot21(Seat seat, HandForPlayer hand);

    public abstract void playerHit(Seat seat, HandForPlayer hand);

    public abstract long playerDoubledAndBust(Seat seat, HandForPlayer hand);

    public abstract void playerDoubledAndGot21(Seat seat, HandForPlayer hand);

    public abstract void playerDoubled(Seat seat, HandForPlayer hand);

    public abstract long playerSurrendered(Seat seat, HandForPlayer hand);

    public abstract void playerSplits(Seat seat, HandForPlayer hand);

    public abstract void gotSecondCardOnSplit(Seat seat, HandForPlayer hand);

    public abstract void gotSecondCardOnSplitAndGot21(Seat seat, HandForPlayer hand);

    public abstract void gotSecondCardOnSplitAndCannotContinue(Seat seat, HandForPlayer hand);

    public abstract void showDealerHandResult(HandForDealer handForDealer, boolean dealerBusted);

    public abstract long playerWins(Seat seat, HandForPlayer hand);

    public abstract long playerLoses(Seat seat, HandForPlayer hand);

    public abstract void playerPushes(Seat seat, HandForPlayer hand);

    public abstract void showRules(Rules rules);

    public abstract void playerDeclinesToBet(Seat seat);
}
