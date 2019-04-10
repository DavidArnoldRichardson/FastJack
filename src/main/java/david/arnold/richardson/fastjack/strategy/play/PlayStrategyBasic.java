package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.Play;

public class PlayStrategyBasic extends PlayStrategy {
    @Override
    public Play getPlay(HandForPlayer hand) {
        return Play.Stand;
    }
}
