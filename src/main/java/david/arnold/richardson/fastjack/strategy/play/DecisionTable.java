package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;

public abstract class DecisionTable {
    public abstract PlayerDecision lookup(HandForPlayer hand);
}
