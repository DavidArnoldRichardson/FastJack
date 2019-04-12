package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.PlayerDecision;

import static david.arnold.richardson.fastjack.PlayerDecision.SUR;
import static david.arnold.richardson.fastjack.PlayerDecision.n_a;

// This is only valid for 4 deck through 8 deck games.
public class MatrixSurrenderIsNotPairH17 extends Matrix {

    public MatrixSurrenderIsNotPairH17() {
        table = new PlayerDecision[][]{
                //A    2    3    4    5    6    7    8    9    T
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 2
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 3
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 4
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 5
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 6
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 7
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 8
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 9
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 10
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 11
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 12
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 13
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 14
                {SUR, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, SUR}, // 15
                {SUR, n_a, n_a, n_a, n_a, n_a, n_a, n_a, SUR, SUR}, // 16
                {SUR, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 17
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 18
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 19
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}  // 20
        };
    }
}
