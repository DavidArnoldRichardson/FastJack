package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;

public abstract class PlayStrategy {
    public abstract PlayerDecision getPlay(HandForPlayer hand);

    public abstract boolean shouldGetInsurance();
}
