package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;
import david.arnold.richardson.fastjack.Rules;

import static david.arnold.richardson.fastjack.PlayerDecision.*;

// This is only valid for 4 deck through 8 deck games.
public class MatrixSurrenderH17 extends Matrix {

    public MatrixSurrenderH17(Rules rules) {
        super(rules);
        table = new PlayerDecision[][]{
                //A    2    3    4    5    6    7    8    9    T
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 2
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 3
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 4
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 5
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 6
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 7
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 8
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 9
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 10
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 11
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 12
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 13
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 14
                {SUR, dna, dna, dna, dna, dna, dna, dna, dna, SUR}, // 15
                {XXX, dna, dna, dna, dna, dna, dna, dna, XXX, XXX}, // 16
                {SUR, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 17
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 18
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}, // 19
                {dna, dna, dna, dna, dna, dna, dna, dna, dna, dna}  // 20
        };
    }

    @Override
    public PlayerDecision lookup(
            HandForPlayer hand,
            int playerHandValue,
            int dealerUpcardValue) {
        PlayerDecision playerDecision = lookup(table, playerHandValue, dealerUpcardValue);
        if (playerDecision == XXX && isSplittablePairOfEights(hand)) {
            return dna;
        }
        return playerDecision;
    }

    private boolean isSplittablePairOfEights(HandForPlayer hand) {
        // todo
        return false;
    }
}
