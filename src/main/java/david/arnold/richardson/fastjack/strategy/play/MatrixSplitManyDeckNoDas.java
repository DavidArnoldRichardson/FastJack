package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.PlayerDecision;

import static david.arnold.richardson.fastjack.PlayerDecision.SPL;
import static david.arnold.richardson.fastjack.PlayerDecision.n_a;

public class MatrixSplitManyDeckNoDas extends Matrix {

    public MatrixSplitManyDeckNoDas() {
        table = new PlayerDecision[][]{
                //A    2    3    4    5    6    7    8    9    T
                {SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL}, // 2: AA
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 3: (unused)
                {n_a, n_a, n_a, SPL, SPL, SPL, SPL, n_a, n_a, n_a}, // 4: 22
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 5: (unused)
                {n_a, n_a, n_a, SPL, SPL, SPL, SPL, n_a, n_a, n_a}, // 6: 33
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 7: (unused)
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 8: 44
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 9: (unused)
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 10: 55
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 11: (unused)
                {n_a, n_a, SPL, SPL, SPL, SPL, n_a, n_a, n_a, n_a}, // 12: 66
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 13: (unused)
                {n_a, SPL, SPL, SPL, SPL, SPL, SPL, n_a, n_a, n_a}, // 14: 77
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 15: (unused)
                {SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL, SPL}, // 16: 88
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 17: (unused)
                {n_a, SPL, SPL, SPL, SPL, SPL, n_a, SPL, SPL, n_a}, // 18: 99
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}, // 19: (unused)
                {n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a, n_a}  // 20: TT
        };
    }
}
