package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;
import david.arnold.richardson.fastjack.Rules;

import static david.arnold.richardson.fastjack.PlayerDecision.STD;
import static david.arnold.richardson.fastjack.PlayerDecision.n_a;

public class PlayStrategyBasic extends PlayStrategy {

    Matrix matrixForSurrenderIsPair = null;
    Matrix matrixForSurrenderIsNotPair = null;
    Matrix matrixForSplit;
    Matrix matrixForSoftDouble = null;
    Matrix matrixForHardDouble;
    Matrix matrixForSoftHitStand;
    Matrix matrixForHardHitStand;

    public PlayStrategyBasic(Rules rules) {
        super(rules);
    }

    @Override
    public void setupLogic() {
        LogicHolder logicHolder = rules.getLogicHolder();
        int numDecks = rules.getNumDecks();
        boolean isH17 = rules.isH17();

        // choose a surrender matrix if needed
        if (rules.isLateSurrenderAvailable()) {
            if (numDecks < 3) {
                throw new RuntimeException("No support for num decks < 3 and surrender.");
            }

            if (isH17) {
                matrixForSurrenderIsPair = logicHolder.getMatrixSurrenderIsPairH17();
                matrixForSurrenderIsNotPair = logicHolder.getMatrixSurrenderIsNotPairH17();
            } else {
                matrixForSurrenderIsPair = logicHolder.getMatrixSurrenderIsPairS17();
                matrixForSurrenderIsNotPair = logicHolder.getMatrixSurrenderIsNotPairS17();
            }
        }

        // choose a split matrix
        if (rules.isCanDoubleAfterSplit()) {
            matrixForSplit = logicHolder.getMatrixSplitDas();
        } else {
            switch (numDecks) {
                case 1:
                    matrixForSplit = logicHolder.getMatrixSplitSingleDeckNoDas();
                    break;
                case 2:
                    matrixForSplit = logicHolder.getMatrixSplitDoubleDeckNoDas();
                    break;
                default:
                    matrixForSplit = logicHolder.getMatrixSplitManyDeckNoDas();
            }
        }

        // choose matrix for doubling on soft hands, if needed
        if (!rules.isCanDoubleOnTenOrElevenOnly()) {
            if (numDecks == 1) {
                matrixForSoftDouble = logicHolder.getMatrixDoubleSoftSingleDeck();
            } else {
                if (isH17) {
                    if (numDecks == 2) {
                        matrixForSoftDouble = logicHolder.getMatrixDoubleSoftDoubleDeckH17();
                    } else {
                        matrixForSoftDouble = logicHolder.getMatrixDoubleSoftManyDeckH17();
                    }
                } else {
                    matrixForSoftDouble = logicHolder.getMatrixDoubleSoftManyDeckS17();
                }
            }
        }

        // choose matrix for doubling on hard hands
        switch (numDecks) {
            case 1:
                matrixForHardDouble = logicHolder.getMatrixDoubleHardSingleDeck();
                break;
            case 2:
                matrixForHardDouble = logicHolder.getMatrixDoubleHardDoubleDeck();
                break;
            default:
                if (isH17) {
                    matrixForHardDouble = logicHolder.getMatrixDoubleHardManyDeckH17();
                } else {
                    matrixForHardDouble = logicHolder.getMatrixDoubleHardManyDeckS17();
                }
        }

        // assign matrix for hit or stand
        matrixForHardHitStand = logicHolder.getMatrixHitStandHard();
        matrixForSoftHitStand = logicHolder.getMatrixHitStandSoft();
    }

    @Override
    public PlayerDecision getPlay(
            HandForPlayer hand,
            int dealerUpcardValue) {
        int playerHandMinPointSum = hand.computeMinPointSum();
        PlayerDecision playerDecision;

        if (rules.isLateSurrenderAvailable()) {
            if (hand.hasExactlyTwoCards()) {
                if (hand.isPair()) {
                    playerDecision = matrixForSurrenderIsPair.lookup(playerHandMinPointSum, dealerUpcardValue);
                } else {
                    playerDecision = matrixForSurrenderIsNotPair.lookup(playerHandMinPointSum, dealerUpcardValue);
                }
                if (playerDecision != n_a) {
                    return playerDecision;
                }
            }
        }

        if (hand.isSplittablePair()) {
            playerDecision = matrixForSplit.lookup(playerHandMinPointSum, dealerUpcardValue);
            if (playerDecision != n_a) {
                return playerDecision;
            }
        }

        boolean isSoft = hand.isSoft();
        if (hand.canDoubleDown(playerHandMinPointSum)) {
            if (isSoft) {
                playerDecision = matrixForSoftDouble.lookup(playerHandMinPointSum, dealerUpcardValue);
            } else {
                playerDecision = matrixForHardDouble.lookup(playerHandMinPointSum, dealerUpcardValue);
            }
            if (playerDecision != n_a) {
                return playerDecision;
            }
        }

        if (hand.isPairOfAces()) {
            if (hand.isHandIsResultOfSplit()) {
                if (!rules.isCanHitSplitAces()) {
                    return STD;
                }
            }
        }

        if (isSoft) {
            return matrixForSoftHitStand.lookup(playerHandMinPointSum, dealerUpcardValue);
        } else {
            return matrixForHardHitStand.lookup(playerHandMinPointSum, dealerUpcardValue);
        }
    }

    @Override
    public boolean shouldGetInsurance() {
        // never get insurance if not counting cards.
        return false;
    }
}
