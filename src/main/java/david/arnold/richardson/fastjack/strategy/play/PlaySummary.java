package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.Outputter;
import david.arnold.richardson.fastjack.PlayerDecision;
import david.arnold.richardson.fastjack.Rules;

public class PlaySummary {
    private StringBuilder builder;

    public PlaySummary() {
        builder = new StringBuilder();
    }

    public static String getHeaderLine() {
        return "playerDecision,isSplit,handsAlreadyAtSeat,handMinPoints,hand,dealerUpcard";
    }

    public void writeLogEntry(
            Outputter outputter,
            int dealerUpcardValue,
            HandForPlayer hand,
            PlayerDecision playerDecision) {
        if (outputter.isLogging()) {
            builder.setLength(0);
            builder.append(playerDecision).append(",");
            if (hand.isHandIsResultOfSplit()) {
                builder.append("y,");
            } else {
                builder.append("n,");
            }
            builder.append(hand.getNumberOfHandsAlreadyAtSeat()).append(",");
            builder.append(hand.computeMinPointSum()).append(",");
            hand.showSummaryWithCards(builder);
            builder.append(",");
            builder.append(Rules.CARD_SYMBOLS.charAt(dealerUpcardValue - 1));
            outputter.writeToPlaySummaryLog(builder.toString());
        }
    }
}
