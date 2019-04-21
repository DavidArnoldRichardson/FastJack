package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;
import david.arnold.richardson.fastjack.Table;

public class PlayStrategyAlwaysStand extends PlayStrategy {
    public PlayStrategyAlwaysStand(Table table) {
        super(table);
    }

    @Override
    public void setupLogic() {
    }

    @Override
    public PlayerDecision getPlay(
            HandForPlayer hand,
            int dealerUpcardValue,
            PlaySummary playSummary) {
        return PlayerDecision.STD;
    }

    @Override
    public boolean shouldGetInsurance() {
        return true;
    }
}
