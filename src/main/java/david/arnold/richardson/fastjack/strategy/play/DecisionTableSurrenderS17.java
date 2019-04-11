package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;

public class DecisionTableSurrenderS17 extends DecisionTable {

    private PlayerDecision[][] table;

    public DecisionTableSurrenderS17() {

        // across the top are the dealer upcard values. Index 0 is ace, index 9 is ten.
        // down the side are the player hand totals. Index 0 is 2 (pair of aces), index
        // 2
        // 2
        // 2
        // 2
        // 2
        // 2
        // 2
        // 2
        // 2
        // 2
        // 2
        // 2
        // 2
        // 2
        // 2
        // 2
        // 2
        // 2
        // 20
        table = new PlayerDecision[][] {
                {},
                {}
        };
    }

    @Override
    public PlayerDecision lookup(HandForPlayer hand) {

    }
}
