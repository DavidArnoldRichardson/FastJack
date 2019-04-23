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
        return "dealerUpcard,handMinPoints,isSplit,handsAlreadyAtSeat,hand,playerDecision,isSoft";
    }

    public void writeLogEntry(
            Outputter outputter,
            int dealerUpcardValue,
            HandForPlayer hand,
            PlayerDecision playerDecision) {
        if (outputter.isLogging()) {
            builder.setLength(0);
            int minPointSum = hand.computeMinPointSum();
            builder.append(Rules.CARD_SYMBOLS.charAt(dealerUpcardValue - 1)).append(",");
            builder.append(minPointSum).append(",");
            if (hand.isHandIsResultOfSplit()) {
                builder.append("y,");
            } else {
                builder.append("n,");
            }
            builder.append(hand.getNumberOfHandsAlreadyAtSeat()).append(",");
            hand.showSummaryWithCards(builder);
            builder.append(",");
            builder.append(playerDecision).append(",");
            builder.append(hand.isSoft() ? "soft" : "hard");
            outputter.writeToPlaySummaryLog(builder.toString());
        }
    }
}
