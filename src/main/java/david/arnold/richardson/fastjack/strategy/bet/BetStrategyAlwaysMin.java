package david.arnold.richardson.fastjack.strategy.bet;

public class BetStrategyAlwaysMin extends BetStrategy {
    @Override
    public long getBetAmount() {
        return 0;
    }
}
