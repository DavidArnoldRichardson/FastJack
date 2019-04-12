package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.PlayerDecision;

import static david.arnold.richardson.fastjack.PlayerDecision.DBL;
import static david.arnold.richardson.fastjack.PlayerDecision.n_a;

// This is only valid for 4 deck through 8 deck games.
public class MatrixDoubleSoftManyDeckS17 extends Matrix {

    public MatrixDoubleSoftManyDeckS17() {
        table = new PlayerDecision[][]{
                //A    2    3    4    5    6    7    8    9    T
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 2: A,A
                {n_a, n_a, n_a, n_a, DBL, DBL, n_a, n_a, n_a, n_a}, // 3: A,2
                {n_a, n_a, n_a, n_a, DBL, DBL, n_a, n_a, n_a, n_a}, // 4: A,3
                {n_a, n_a, n_a, DBL, DBL, DBL, n_a, n_a, n_a, n_a}, // 5: A,4
                {n_a, n_a, n_a, DBL, DBL, DBL, n_a, n_a, n_a, n_a}, // 6: A,5
                {n_a, n_a, DBL, DBL, DBL, DBL, n_a, n_a, n_a, n_a}, // 7: A,6
                {n_a, n_a, DBL, DBL, DBL, DBL, n_a, n_a, n_a, n_a}, // 8: A,7
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 9: A,8
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 10: A,9
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 11: (unused)
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 12: (unused)
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 13: (unused)
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 14: (unused)
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 15: (unused)
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 16: (unused)
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 17: (unused)
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 18: (unused)
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 19: (unused)
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}  // 20: (unused)
        };
    }
}
