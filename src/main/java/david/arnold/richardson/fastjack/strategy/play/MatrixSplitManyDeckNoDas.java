package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.PlayerDecision;

import static david.arnold.richardson.fastjack.PlayerDecision.SPL;
import static david.arnold.richardson.fastjack.PlayerDecision.n_a;

public class MatrixSplitManyDeckNoDas extends Matrix {

    public MatrixSplitManyDeckNoDas() {
        table = new PlayerDecision[][]{
                //A    2    3    4    5    6    7    8    9    T
                {SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL}, // 2: A,A
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 3: (unused)
                {n_a, n_a, n_a, SPL, SPL, SPL, SPL, n_a, n_a, n_a}, // 4: 2,2
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 5: (unused)
                {n_a, n_a, n_a, SPL, SPL, SPL, SPL, n_a, n_a, n_a}, // 6: 3,3
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 7: (unused)
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 8: 4,4
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 9: (unused)
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 10: 5,5
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 11: (unused)
                {n_a, n_a, SPL, SPL, SPL, SPL, n_a, n_a, n_a, n_a}, // 12: 6,6
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 13: (unused)
                {n_a, SPL, SPL, SPL, SPL, SPL, SPL, n_a, n_a, n_a}, // 14: 7,7
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 15: (unused)
                {SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL}, // 16: 8,8
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 17: (unused)
                {n_a, SPL, SPL, SPL, SPL, SPL, n_a, SPL, SPL, n_a}, // 18: 9,9
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 19: (unused)
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}  // 20: T,T
        };
    }
}
