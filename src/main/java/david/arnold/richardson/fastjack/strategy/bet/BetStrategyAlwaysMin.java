package david.arnold.richardson.fastjack.strategy.bet;

import david.arnold.richardson.fastjack.Player;
import david.arnold.richardson.fastjack.Table;

public class BetStrategyAlwaysMin extends BetStrategy {

    private Player player;
    private Table table;

    public BetStrategyAlwaysMin(
            Player player,
            Table table) {
        this.player = player;
        this.table = table;
    }

    @Override
    public void setupLogic() {
    }

    @Override
    public long getBetAmount() {
        return table.getRules().getMinBetAmount();
    }
}
