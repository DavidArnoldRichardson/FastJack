package david.arnold.richardson.fastjack.strategy.bet;

import david.arnold.richardson.fastjack.Table;

public class BetStrategyAlwaysMin extends BetStrategy {

    private Table table;

    public BetStrategyAlwaysMin(
            Table table) {
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
