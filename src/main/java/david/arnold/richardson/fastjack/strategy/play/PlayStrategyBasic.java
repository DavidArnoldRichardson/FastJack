package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;
import david.arnold.richardson.fastjack.Rules;

import static david.arnold.richardson.fastjack.PlayerDecision.n_a;

public class PlayStrategyBasic extends PlayStrategy {

    Matrix matrixForSurrender = null;
    Matrix matrixForSplit;
    Matrix matrixForSoftDouble = null;
    Matrix matrixForHardDouble;
    Matrix matrixForSoftHitStand;
    Matrix matrixForHardHitStand;

    public PlayStrategyBasic(Rules rules) {
        super(rules);

        MatrixHolder matrixHolder = rules.getMatrixHolder();
        int numDecks = rules.getNumDecks();
        boolean isH17 = rules.isH17();

        // choose a surrender matrix if needed
        if (rules.isLateSurrenderAvailable()) {
            if (numDecks < 3) {
                throw new RuntimeException("No support for num decks < 3 and surrender.");
            }

            if (isH17) {
                matrixForSurrender = matrixHolder.getMatrixSurrenderH17();
            } else {
                matrixForSurrender = matrixHolder.getMatrixSurrenderS17();
            }
        }

        // choose a split matrix
        if (rules.isCanDoubleAfterSplit()) {
            matrixForSplit = matrixHolder.getMatrixSplitDas();
        } else {
            switch (numDecks) {
                case 1:
                    matrixForSplit = matrixHolder.getMatrixSplitSingleDeckNoDas();
                    break;
                case 2:
                    matrixForSplit = matrixHolder.getMatrixSplitDoubleDeckNoDas();
                    break;
                default:
                    matrixForSplit = matrixHolder.getMatrixSplitManyDeckNoDas();
            }
        }

        // choose matrix for doubling on soft hands, if needed
        if (!rules.isCanDoubleOnTenOrElevenOnly()) {
            if (numDecks == 1) {
                matrixForSoftDouble = matrixHolder.getMatrixDoubleSoftSingleDeck();
            } else {
                if (isH17) {
                    if (numDecks == 2) {
                        matrixForSoftDouble = matrixHolder.getMatrixDoubleSoftDoubleDeckH17();
                    } else {
                        matrixForSoftDouble = matrixHolder.getMatrixDoubleSoftManyDeckH17();
                    }
                } else {
                    matrixForSoftDouble = matrixHolder.getMatrixDoubleSoftManyDeckS17();
                }
            }
        }

        // choose matrix for doubling on hard hands
        switch (numDecks) {
            case 1:
                matrixForHardDouble = matrixHolder.getMatrixDoubleHardSingleDeck();
                break;
            case 2:
                matrixForHardDouble = matrixHolder.getMatrixDoubleHardDoubleDeck();
                break;
            default:
                if (isH17) {
                    matrixForHardDouble = matrixHolder.getMatrixDoubleHardManyDeckH17();
                } else {
                    matrixForHardDouble = matrixHolder.getMatrixDoubleHardManyDeckS17();
                }
        }

        // assign matrix for hit or stand
        matrixForHardHitStand = matrixHolder.getMatrixHitStandHard();
        matrixForSoftHitStand = matrixHolder.getMatrixHitStandSoft();
    }

    @Override
    public PlayerDecision getPlay(
            HandForPlayer hand,
            int dealerUpcardValue) {
        int playerHandMinPointSum = hand.computeMinPointSum();
        PlayerDecision playerDecision;

        if (rules.isLateSurrenderAvailable()) {
            if (hand.hasExactlyTwoCards()) {
                playerDecision = matrixForSurrender.lookup(
                        hand,
                        playerHandMinPointSum,
                        dealerUpcardValue);
                if (playerDecision != n_a) {
                    return playerDecision;
                }
            }
        }

        if (hand.isSplittablePair()) {
            playerDecision = matrixForSplit.lookup(
                    hand,
                    playerHandMinPointSum,
                    dealerUpcardValue);
            if (playerDecision != n_a) {
                return playerDecision;
            }
        }

        boolean isSoft = hand.isSoft();
        if (hand.canDoubleDown(isSoft)) {
            if (isSoft) {
                playerDecision = matrixForSoftDouble.lookup(
                        hand,
                        playerHandMinPointSum,
                        dealerUpcardValue);
            } else {
                playerDecision = matrixForHardDouble.lookup(
                        hand,
                        playerHandMinPointSum,
                        dealerUpcardValue);
            }
            if (playerDecision != n_a) {
                return playerDecision;
            }
        }

        if (isSoft) {
            return matrixForSoftHitStand.lookup(
                    hand,
                    playerHandMinPointSum,
                    dealerUpcardValue);
        } else {
            return matrixForHardHitStand.lookup(
                    hand,
                    playerHandMinPointSum,
                    dealerUpcardValue);
        }
    }

    @Override
    public boolean shouldGetInsurance() {
        // never get insurance if not counting cards.
        return false;
    }
}
