package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.bet.BetStrategy;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategy;

public class Player {
    private String playerName;
    private long initialBankroll;
    private long bankroll;
    private PlayStrategy playStrategy;
    private BetStrategy betStrategy;
    private Table table;

    public Player(
            String playerName,
            long bankroll,
            Table table) {
        this.playerName = playerName;
        this.initialBankroll = bankroll;
        this.bankroll = bankroll;
        this.table = table;
    }

    public void setStrategies(
            PlayStrategy playStrategy,
            BetStrategy betStrategy) {
        this.playStrategy = playStrategy;
        this.betStrategy = betStrategy;
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
                + " ended with " + MoneyHelper.formatForDisplay(bankroll) + ".";
    }
}
