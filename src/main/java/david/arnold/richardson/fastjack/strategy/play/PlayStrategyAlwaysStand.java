package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;
import david.arnold.richardson.fastjack.Rules;

public class PlayStrategyAlwaysStand extends PlayStrategy {
    public PlayStrategyAlwaysStand(Rules rules) {
        super(rules);
    }

    @Override
    public PlayerDecision getPlay(
            HandForPlayer hand,
            int dealerUpcardValue) {
        return PlayerDecision.STD;
    }

    @Override
    public boolean shouldGetInsurance() {
        return true;
    }
}
