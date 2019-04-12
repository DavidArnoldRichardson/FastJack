package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.PlayerDecision;

import static david.arnold.richardson.fastjack.PlayerDecision.HIT;
import static david.arnold.richardson.fastjack.PlayerDecision.STD;

// This is only valid for 4 deck through 8 deck games.
public class MatrixHitStandHard extends Matrix {

    public MatrixHitStandHard() {
        table = new PlayerDecision[][]{
                //A    2    3    4    5    6    7    8    9    T
                {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT}, // 2
                {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT}, // 3
                {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT}, // 4
                {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT}, // 5
                {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT}, // 6
                {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT}, // 7
                {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT}, // 8
                {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT}, // 9
                {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT}, // 10
                {HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT, HIT}, // 11
                {HIT, HIT, HIT, STD, STD, STD, HIT, HIT, HIT, HIT}, // 12
                {HIT, STD, STD, STD, STD, STD, HIT, HIT, HIT, HIT}, // 13
                {HIT, STD, STD, STD, STD, STD, HIT, HIT, HIT, HIT}, // 14
                {HIT, STD, STD, STD, STD, STD, HIT, HIT, HIT, HIT}, // 15
                {HIT, STD, STD, STD, STD, STD, HIT, HIT, HIT, HIT}, // 16
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}, // 17
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}, // 18
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}, // 19
                {STD, STD, STD, STD, STD, STD, STD, STD, STD, STD}  // 20
        };
    }
}
