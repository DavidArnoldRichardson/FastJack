package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.bet.BetStrategy;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategy;

import java.text.DecimalFormat;

public class Player {
    private String playerName;
    private long initialBankroll;
    private long bankroll;
    private PlayStrategy playStrategy;
    private BetStrategy betStrategy;

    public Player(
            String playerName,
            long bankroll) {
        this.playerName = playerName;
        this.initialBankroll = bankroll;
        this.bankroll = bankroll;
    }

    public void setStrategies(
            PlayStrategy playStrategy,
            BetStrategy betStrategy) {
        this.playStrategy = playStrategy;
        this.betStrategy = betStrategy;
    }

    // used for testing
    public void refreshStrategiesDueToRulesChange() {
        playStrategy.setupLogic();
        betStrategy.setupLogic();
    }

    public PlayStrategy getPlayStrategy() {
        return playStrategy;
    }

    public BetStrategy getBetStrategy() {
        return betStrategy;
    }

    public void payPlayer(long money) {
        bankroll += money;
    }

    public String getPlayerName() {
        return playerName;
    }

    public long getBankroll() {
        return bankroll;
    }

    public long getInitialBankroll() {
        return initialBankroll;
    }

    public void removeFromBankroll(long money) {
        bankroll -= money;
    }

    public void addToBankroll(long money) {
        bankroll += money;
    }

    public String showResult() {
        return playerName
                + " started with " + MoneyHelper.formatForDisplay(initialBankroll)
                + " ended with " + MoneyHelper.formatForDisplay(bankroll)
                + ". Player edge: " + computePlayerEdge(initialBankroll, bankroll);
    }

    static String computePlayerEdge(
            long initialBankroll,
            long finalBankroll) {
        double ib = (double) initialBankroll;
        double fb = (double) finalBankroll;
        double ratio = (1.0D - (fb / ib)) * -100.0D;
        DecimalFormat df = new DecimalFormat("#0.000");
        return df.format(ratio) + "%";
    }
}
