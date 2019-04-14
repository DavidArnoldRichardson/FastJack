package david.arnold.richardson.fastjack;

public class HandForPlayer extends Hand {
    private long betAmount;
    private boolean handIsResultOfSplit;
    private Seat seat;

    public HandForPlayer(
            Shoe shoe,
            Seat seat) {
        // could be all twos, to a max of 11 (22 points), where the player busts.
        super(shoe, 11);
        this.seat = seat;
    }

    @Override
    protected void resetHelper() {
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

    public boolean hasExactlyTwoCards() {
        return numCardsInHand == 2;
    }

    public boolean isPair() {
        if (!hasExactlyTwoCards()) {
            return false;
        }
        return shoe.getCardPointValue(indexesOfCards[0]) == shoe.getCardPointValue(indexesOfCards[1]);
    }

    public boolean isPairOfAces() {
        return isPair() && shoe.isAce(indexesOfCards[0]);
    }

    public boolean isSplittablePair() {

        if (!isPair()) {
            return false;
        }

        boolean playerLacksSufficientFunds = seat.getPlayer().getBankroll() < getBetAmount();
        if (playerLacksSufficientFunds) {
            return false;
        }

        boolean alreadyBeenTooManySplits = seat.getNumHandsInUse() == shoe.getRules().getMaxNumSplits() + 1;
        if (alreadyBeenTooManySplits) {
            return false;
        }

        if (isPairOfAces() && handIsResultOfSplit) {
            return shoe.getRules().isCanResplitAces();
        }

        return true;
    }

    public void setHandIsResultOfSplit() {
        this.handIsResultOfSplit = true;
    }

    public boolean isHandIsResultOfSplit() {
        return handIsResultOfSplit;
    }

    // passing the parameter in to save some CPU cycles
    public boolean canDoubleDown(
            int minHandSum) {
        if (!hasExactlyTwoCards()) {
            return false;
        }

        if (handIsResultOfSplit && !shoe.getRules().isCanDoubleAfterSplit()) {
            return false;
        }

        //noinspection RedundantIfStatement
        if (minHandSum != 10 && minHandSum != 11 && shoe.getRules().isCanDoubleOnTenOrElevenOnly()) {
            return false;
        }

        return true;
    }
}
