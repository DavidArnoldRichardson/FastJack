package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.bet.BetStrategy;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategy;

public class Player {
    private String playerName;
    private long bankroll;
    private PlayStrategy playStrategy;
    private BetStrategy betStrategy;

    public Player(
            String playerName,
            long bankroll,
            PlayStrategy playStrategy,
            BetStrategy betStrategy) {
        this.playerName = playerName;
        this.bankroll = bankroll;
        this.playStrategy = playStrategy;
        this.betStrategy = betStrategy;
    }
}
