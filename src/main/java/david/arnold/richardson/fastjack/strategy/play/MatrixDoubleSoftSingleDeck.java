package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.PlayerDecision;

import static david.arnold.richardson.fastjack.PlayerDecision.DBL;
import static david.arnold.richardson.fastjack.PlayerDecision.n_a;

// This is only valid for 4 deck through 8 deck games.
public class MatrixDoubleSoftSingleDeck extends Matrix {

    public MatrixDoubleSoftSingleDeck() {
        table = new PlayerDecision[][]{
                //A    2    3    4    5    6    7    8    9    T
                {n_a, n_a, n_a, DBL, DBL, DBL, n_a, n_a, n_a, n_a}, // 2
                {n_a, n_a, n_a, DBL, DBL, DBL, n_a, n_a, n_a, n_a}, // 3
                {n_a, n_a, n_a, DBL, DBL, DBL, n_a, n_a, n_a, n_a}, // 4
                {n_a, n_a, n_a, DBL, DBL, DBL, n_a, n_a, n_a, n_a}, // 5
                {n_a, DBL, DBL, DBL, DBL, DBL, n_a, n_a, n_a, n_a}, // 6
                {n_a, n_a, DBL, DBL, DBL, DBL, n_a, n_a, n_a, n_a}, // 7
                {n_a, n_a, n_a, n_a, n_a, DBL, n_a, n_a, n_a, n_a}, // 8
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 9
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 10
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 11
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 12
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 13
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 14
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 15
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 16
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 17
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 18
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 19
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}  // 20
        };
    }
}
