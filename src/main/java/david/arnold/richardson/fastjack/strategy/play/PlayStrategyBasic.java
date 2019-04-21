package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;
import david.arnold.richardson.fastjack.Rules;
import david.arnold.richardson.fastjack.Table;

import static david.arnold.richardson.fastjack.PlayerDecision.STD;
import static david.arnold.richardson.fastjack.PlayerDecision.n_a;

public class PlayStrategyBasic extends PlayStrategy {

    Matrix matrixForSurrenderIsPair;
    Matrix matrixForSurrenderIsNotPair;
    Matrix matrixForSplit;
    Matrix matrixForSoftDouble;
    Matrix matrixForHardDouble;
    Matrix matrixForSoftHitStand;
    Matrix matrixForHardHitStand;

    public PlayStrategyBasic(Table table) {
        super(table);
    }

    @Override
    public void setupLogic() {
        Rules rules = table.getRules();
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
            int dealerUpcardValue,
            PlaySummary playSummary) {

        playSummary.setConditions(dealerUpcardValue, hand);

        int playerHandMinPointSum = hand.computeMinPointSum();
        PlayerDecision playerDecision;

        if (table.getRules().isLateSurrenderAvailable()) {
            if (hand.hasExactlyTwoCards()) {
                if (hand.isPair()) {
                    playerDecision = matrixForSurrenderIsPair.lookup(playerHandMinPointSum, dealerUpcardValue);
                } else {
                    playerDecision = matrixForSurrenderIsNotPair.lookup(playerHandMinPointSum, dealerUpcardValue);
                }
                if (playerDecision != n_a) {
                    playSummary.setPlayerDecision(playerDecision);
                    return playerDecision;
                }
            }
        }

        if (hand.isSplittablePair()) {
            playerDecision = matrixForSplit.lookup(playerHandMinPointSum, dealerUpcardValue);
            if (playerDecision != n_a) {
                playSummary.setPlayerDecision(playerDecision);
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
                playSummary.setPlayerDecision(playerDecision);
                return playerDecision;
            }
        }

        if (hand.isHandIsResultOfSplit()) {
            if (hand.firstCardIsAce()) {
                if (!table.getRules().isCanHitSplitAces()) {
                    playerDecision = STD;
                    playSummary.setPlayerDecision(playerDecision);
                    return playerDecision;
                }
            }
        }

        if (isSoft) {
            playerDecision = matrixForSoftHitStand.lookup(playerHandMinPointSum, dealerUpcardValue);
        } else {
            playerDecision = matrixForHardHitStand.lookup(playerHandMinPointSum, dealerUpcardValue);
        }

        playSummary.setPlayerDecision(playerDecision);
        return playerDecision;
    }

    @Override
    public boolean shouldGetInsurance() {
        // never get insurance if not counting cards.
        return false;
    }
}
