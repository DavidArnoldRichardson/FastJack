package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;
import david.arnold.richardson.fastjack.Rules;

public class PlaySummary {
    private boolean isHandResultOfSplit;
    private int numHandsAlreadyAtSeat;
    private int dealerUpcardValue;
    private HandForPlayer hand;
    private PlayerDecision playerDecision;

    private String rulesSummary;
    private StringBuilder builder = new StringBuilder();

    public PlaySummary(Rules rules) {
        this.rulesSummary = rules.showSummary() + " ";
    }

    public void setConditions(
            int dealerUpcardValue,
            HandForPlayer hand) {
        this.isHandResultOfSplit = hand.isHandIsResultOfSplit();
        this.numHandsAlreadyAtSeat = hand.getNumberOfHandsAlreadyAtSeat();
        this.dealerUpcardValue = dealerUpcardValue;
        this.hand = hand;
        this.playerDecision = null;
    }

    public void setPlayerDecision(PlayerDecision playerDecision) {
        this.playerDecision = playerDecision;
    }

    public String show() {
        if (playerDecision == null) {
            throw new IllegalStateException("You forgot to set the player decision.");
        }

        builder.setLength(0);
        builder.append(rulesSummary);
        if (isHandResultOfSplit) {
            builder.append("[").append(numHandsAlreadyAtSeat).append(" hands] ");
        }
        hand.showSummary(builder);
        builder.append(" vs ");
        builder.append(Rules.CARD_SYMBOLS.charAt(dealerUpcardValue));
        builder.append(" : ").append(playerDecision);

        return builder.toString();
    }
}
