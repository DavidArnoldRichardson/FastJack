package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.bet.BetStrategy;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategy;

import java.text.DecimalFormat;

public class Player {
    private String playerName;
    private long initialBankrollAmount;
    private MoneyPile moneyPile;
    private PlayStrategy playStrategy;
    private BetStrategy betStrategy;

    public Player(
            String playerName,
            long initialBankrollAmount) {
        this.playerName = playerName;
        this.initialBankrollAmount = initialBankrollAmount;
        this.moneyPile = MoneyPile.createPlayerMoneyPile(initialBankrollAmount);
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

    public String getPlayerName() {
        return playerName;
    }

    public PlayStrategy getPlayStrategy() {
        return playStrategy;
    }

    public BetStrategy getBetStrategy() {
        return betStrategy;
    }

    public MoneyPile getMoneyPile() {
        return this.moneyPile;
    }

    public boolean canAfford(long amount) {
        return moneyPile.canAfford(amount);
    }

    public long getAvailableFunds() {
        return moneyPile.getAmount();
    }

    public long getInitialBankrollAmount() {
        return initialBankrollAmount;
    }

    public double getEdge() {
        return getEdge(
                initialBankrollAmount,
                moneyPile.getAmount());
    }

    public static double getEdge(
            double initialBankroll,
            double finalBankroll) {
        return (1.0D - (finalBankroll / initialBankroll)) * -100.0D;
    }

    public static String formatEdge(double edge) {
        DecimalFormat df = new DecimalFormat("#0.000");
        return df.format(edge) + "%";
    }

    public String showResult() {
        double edge = getEdge(
                initialBankrollAmount,
                moneyPile.getAmount());
        return playerName
                + " started with " + MoneyPile.show(initialBankrollAmount)
                + " ended with " + moneyPile.formatForDisplay()
                + ". Player edge: " + formatEdge(edge);
    }
}
