package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;
import david.arnold.richardson.fastjack.Rules;

import static david.arnold.richardson.fastjack.PlayerDecision.n_a;

public class PlayStrategyBasic extends PlayStrategy {

    Matrix matrixForSurrender = null;

    public PlayStrategyBasic(Rules rules) {
        super(rules);

        if (rules.isLateSurrenderAvailable()) {

            if (rules.getNumDecks() < 3) {
                throw new RuntimeException("No support for num decks < 3 and surrender.");
            }

            if (rules.isH17()) {
                matrixForSurrender = rules.getMatrixHolder().getMatrixSurrenderH17();
            } else {
                matrixForSurrender = rules.getMatrixHolder().getMatrixSurrenderS17();
            }
        }
    }

    @Override
    public PlayerDecision getPlay(
            HandForPlayer hand,
            int dealerUpcardValue) {
        int playerHandMinPointSum = hand.computeMinPointSum();

        PlayerDecision playerDecision = n_a;
        if (rules.isLateSurrenderAvailable()) {
            if (hand.hasExactlyTwoCards()) {
                playerDecision = matrixForSurrender.lookup(
                        hand,
                        playerHandMinPointSum,
                        dealerUpcardValue);
            }
        }

        return playerDecision;
    }

    @Override
    public boolean shouldGetInsurance() {
        // never get insurance if not counting cards.
        return false;
    }
}
