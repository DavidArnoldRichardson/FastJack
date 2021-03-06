package david.arnold.richardson.fastjack;

public class HandForPlayer extends Hand {
    private MoneyPile moneyPile;
    private boolean handIsResultOfSplit;
    private Seat seat;
    private int handIndex;

    public HandForPlayer(
            Shoe shoe,
            Seat seat,
            int handIndex) {
        super(shoe);
        this.seat = seat;
        this.handIndex = handIndex;
        this.moneyPile = new MoneyPile();
    }

    @Override
    protected void resetHelper() {
        if (moneyPile != null && moneyPile.hasSomeMoney()) {
            throw new RuntimeException("Bug in code! Hand still has money: " + this.moneyPile.getAmount());
        }
        handIsResultOfSplit = false;
    }

    public boolean hasBet() {
        return moneyPile.hasSomeMoney();
    }

    public void playerCreatesBet(
            Player player,
            long betAmount) {
        player.getMoneyPile().pay(moneyPile, betAmount);
    }

    public String getBetAmountForDisplay() {
        return moneyPile.formatForDisplay();
    }

    public String getBetAmountBlackjackForDisplay() {
        long amount = moneyPile.getAmount();
        return MoneyPile.show(amount + (amount >> 1));
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

        boolean playerLacksSufficientFunds = !seat.getPlayer().canAfford(moneyPile.getAmount());
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

    public boolean checkIfHandNeedsDealerToPlay() {
        return playIsComplete;
    }

    public void setPlayIsComplete() {
        this.playIsComplete = true;
    }

    public int getHandIndex() {
        return handIndex;
    }

    public MoneyPile getMoneyPile() {
        return moneyPile;
    }
}
