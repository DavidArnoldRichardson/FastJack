package david.arnold.richardson.fastjack.strategy.bet;

import david.arnold.richardson.fastjack.Player;
import david.arnold.richardson.fastjack.Rules;

public class BetStrategyAlwaysMin extends BetStrategy {

    private Player player;
    private Rules rules;

    public BetStrategyAlwaysMin(
            Player player,
            Rules rules) {
        this.player = player;
        this.rules = rules;
    }

    @Override
    public void setupLogic() {
    }

    @Override
    public long getBetAmount() {
        return rules.getMinBetAmount();
    }
}
