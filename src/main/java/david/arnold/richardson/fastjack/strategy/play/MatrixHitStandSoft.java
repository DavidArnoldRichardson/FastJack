package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.PlayerDecision;

import static david.arnold.richardson.fastjack.PlayerDecision.*;

// This is only valid for 4 deck through 8 deck games.
public class MatrixHitStandSoft extends Matrix {

    public MatrixHitStandSoft() {
        table = new PlayerDecision[][]{
                //A    2    3    4    5    6    7    8    9    T
                {HIT, HIT, HIT, STD, STD, STD, HIT, HIT, HIT, HIT}, // 2: A,A - treated as a 12, not a 2.
                {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT}, // 3: A,2
                {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT}, // 4: A,3
                {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT}, // 5: A,4
                {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT}, // 6: A,5
                {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT}, // 7: A,6
                {HIT, STD, STD, STD, STD, STD, STD, STD, HIT, HIT}, // 8: A,7
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}, // 9: A,8
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}, // 10: A,9
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}, // 11: (unused)
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}, // 12: (unused)
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}, // 13: (unused)
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}, // 14: (unused)
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}, // 15: (unused)
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}, // 16: (unused)
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}, // 17: (unused)
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}, // 18: (unused)
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}, // 19: (unused)
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}  // 20: (unused)
        };
    }
}
