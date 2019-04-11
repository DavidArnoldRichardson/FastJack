package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;
import david.arnold.richardson.fastjack.Rules;

import static david.arnold.richardson.fastjack.PlayerDecision.*;

// This is only valid for 4 deck through 8 deck games.
public class DecisionTableSurrenderH17 extends DecisionTable {

    private PlayerDecision[][] table;

    public DecisionTableSurrenderH17(Rules rules) {
        super(rules);

        // todo: These are the values for S17, update for H17

        // across the top are the dealer upcard values. Index 0 is ace, index 9 is ten.
        // down the side are the player hand totals. Index 0 is 2 (pair of aces), index
        table = new PlayerDecision[][]{
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, SUR, DNA}, // 15
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, SUR, SPC, SUR}, // 16
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA},
                {DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA, DNA}
        };
    }

    @Override
    public PlayerDecision lookup(
            HandForPlayer hand,
            int playerHandValue,
            int dealerUpcardValue) {
        PlayerDecision playerDecision = lookup(table, playerHandValue, dealerUpcardValue);
        if (playerDecision == SPC && isSplittablePairOfEights(hand)) {
            return DNA;
        }
        return playerDecision;
    }

    private boolean isSplittablePairOfEights(HandForPlayer hand) {
        // todo
        return false;
    }
}
