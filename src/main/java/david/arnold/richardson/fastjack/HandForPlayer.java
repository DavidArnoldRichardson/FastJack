package david.arnold.richardson.fastjack;

public class HandForPlayer extends Hand {
    private long betAmount;

    public HandForPlayer(Shoe shoe) {
        // could be all twos, to a max of 11 (22 points), where the player busts.
        super(shoe, 11);
    }

    @Override
    public void resetHelper() {
        this.betAmount = 0L;
    }

    public long getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(long betAmount) {
        this.betAmount = betAmount;
    }
}
