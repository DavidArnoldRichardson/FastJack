package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.bet.BetStrategy;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategy;

public class Player {
    private String playerName;
    private long initialBankroll;
    private long bankroll;
    private PlayStrategy playStrategy;
    private BetStrategy betStrategy;
    private Rules rules;
    private Table table;

    private HandForPlayer[] hands;
    private int numHandsInUse;

    public Player(
            String playerName,
            long bankroll,
            Rules rules,
            Table table) {
        this.playerName = playerName;
        this.initialBankroll = bankroll;
        this.bankroll = bankroll;
        this.rules = rules;
        this.table = table;

        int maxNumHands = rules.getMaxNumSplits() + 1;
        hands = new HandForPlayer[maxNumHands];
        for (int i = 0; i < maxNumHands; i++) {
            hands[i] = new HandForPlayer(table.getShoe());
        }
        numHandsInUse = 0;
    }

    public void setStrategies(
            PlayStrategy playStrategy,
            BetStrategy betStrategy) {
        this.playStrategy = playStrategy;
        this.betStrategy = betStrategy;
    }

    public void createNewHandWithBet() {
        HandForPlayer hand = hands[numHandsInUse++];
        hand.reset();
        hand.setBetAmount(betStrategy.getBetAmount());
    }

    public int getNumHandsInUse() {
        return numHandsInUse;
    }

    public HandForPlayer getHand(int handIndex) {
        return hands[handIndex];
    }

    public String getPlayerName() {
        return playerName;
    }

    public String showResult() {
        return playerName
                + " started with " + MoneyHelper.formatForDisplay(initialBankroll)
                + " ended with " + MoneyHelper.formatForDisplay(bankroll) + ".";
    }

    public void resetHands() {
        numHandsInUse = 0;
    }
}
