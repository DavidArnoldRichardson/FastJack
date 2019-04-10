package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;

public class PlayStrategyAlwaysStand extends PlayStrategy {
    @Override
    public PlayerDecision getPlay(HandForPlayer hand) {
        return PlayerDecision.Stand;
    }

    @Override
    public boolean shouldGetInsurance() {
        return true;
    }
}
