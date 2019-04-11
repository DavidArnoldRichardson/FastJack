package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;
import david.arnold.richardson.fastjack.Rules;

public abstract class PlayStrategy {

    protected Rules rules;

    public PlayStrategy(Rules rules) {
        this.rules = rules;
    }

    public abstract PlayerDecision getPlay(
            HandForPlayer hand,
            int dealerUpcardValue);

    public abstract boolean shouldGetInsurance();
}
