package david.arnold.richardson.fastjack;

public abstract class Hand {
    protected Shoe shoe;
    protected int[] indexesOfCards;
    protected int numCardsInHand;

    protected Hand(
            Shoe shoe,
            int maxNumCardsInHand) {
        this.shoe = shoe;
        indexesOfCards = new int[maxNumCardsInHand];
        numCardsInHand = 0;
    }

    public void reset() {
        numCardsInHand = 0;
    }

    public void addCard(int cardIndex) {
        indexesOfCards[numCardsInHand++] = cardIndex;
    }

    protected int computeMinPointSum() {
        int sum = 0;
        for (int i = 0; i < numCardsInHand; i++) {
            sum += shoe.getCardPointValue(i);
        }
        return sum;
    }

    protected boolean hasAtLeastOneAce() {
        for (int i = 0; i < numCardsInHand; i++) {
            if (shoe.isAce(i)) {
                return true;
            }
        }
        return false;
    }

    public int computeMaxPointSum() {
        int minPointSum = computeMinPointSum();
        if (!hasAtLeastOneAce()) {
            return minPointSum;
        }
        if (minPointSum <= 11) {
            return minPointSum + 10;
        }
        return minPointSum;
    }

    // returns true of the hand has an ace that can be 11
    public boolean isSoft() {
        if (!hasAtLeastOneAce()) {
            return false;
        }
        return computeMinPointSum() <= 11;
    }

    public boolean isBlackjack(boolean isHandResultOfSplit) {
        if (isHandResultOfSplit) {
            return false;
        }
        if (numCardsInHand != 2) {
            return false;
        }
        //noinspection RedundantIfStatement
        if (hasAtLeastOneAce() && computeMinPointSum() == 11) {
            return true;
        }
        return false;
    }

    public boolean isBusted() {
        return computeMinPointSum() > 21;
    }
}
