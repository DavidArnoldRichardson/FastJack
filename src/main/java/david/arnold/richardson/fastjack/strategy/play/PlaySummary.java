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
        return "isSplit,handsAlreadyAtSeat,hand,handPointsMin,handPointsMax,dealerUpcard,playerDecision";
    }

    public void writeLogEntry(
            Outputter outputter,
            int dealerUpcardValue,
            HandForPlayer hand,
            PlayerDecision playerDecision) {
        if (outputter.isLogging()) {
            builder.setLength(0);
            if (hand.isHandIsResultOfSplit()) {
                builder.append("y,");
            } else {
                builder.append("n,");
            }
            builder.append(hand.getNumberOfHandsAlreadyAtSeat()).append(",");
            hand.showSummary(builder);
            builder.append(",");
            builder.append(Rules.CARD_SYMBOLS.charAt(dealerUpcardValue)).append(",");
            builder.append(playerDecision);

            outputter.writeToPlaySummaryLog(builder.toString());
        }
    }
}
