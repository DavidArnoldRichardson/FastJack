package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;
import david.arnold.richardson.fastjack.Table;

public abstract class PlayStrategy {

    protected Table table;

    public PlayStrategy(Table table) {
        this.table = table;
        setupLogic();
    }

    public abstract void setupLogic();

    public abstract PlayerDecision getPlay(
            HandForPlayer hand,
            int dealerUpcardValue);

    public abstract boolean shouldGetInsurance();
}
