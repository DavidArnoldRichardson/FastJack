package david.arnold.richardson.fastjack;

public class HandForPlayer extends Hand {
    private long betAmount;
    private boolean handIsResultOfSplit;

    public HandForPlayer(Shoe shoe) {
        // could be all twos, to a max of 11 (22 points), where the player busts.
        super(shoe, 11);
    }

    @Override
    public void resetHelper() {
        betAmount = 0L;
        handIsResultOfSplit = false;
    }

    public long getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(long betAmount) {
        this.betAmount = betAmount;
    }

    public int split() {
        numCardsInHand--;
        return indexesOfCards[1];
    }

    public boolean isPairOfAces() {
        return numCardsInHand == 2
                && shoe.isAce(indexesOfCards[0])
                && shoe.isAce(indexesOfCards[1]);
    }

    public boolean isHandIsResultOfSplit() {
        return handIsResultOfSplit;
    }

    public void setHandIsResultOfSplit() {
        this.handIsResultOfSplit = true;
    }
}
