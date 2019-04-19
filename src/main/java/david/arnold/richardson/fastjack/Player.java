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

    public String showResult() {
        return playerName
                + " started with " + MoneyPile.show(initialBankrollAmount)
                + " ended with " + moneyPile.formatForDisplay()
                + ". Player edge: " + computePlayerEdge(initialBankrollAmount, moneyPile.getAmount());
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
