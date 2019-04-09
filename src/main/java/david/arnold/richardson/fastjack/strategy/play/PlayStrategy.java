package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.Play;

public abstract class PlayStrategy {
    public abstract Play getPlay(HandForPlayer hand);
}
