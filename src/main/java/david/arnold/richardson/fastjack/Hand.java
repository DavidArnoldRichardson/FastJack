package david.arnold.richardson.fastjack;

public abstract class Hand {
    protected Shoe shoe;
    protected int[] indexesOfCards;
    protected int numCardsInHand;

    // prevents computing max hand point value more often than necessary.
    protected boolean isTwentyOnePoints;

    protected Hand(
            Shoe shoe,
            int maxNumCardsInHand) {
        this.shoe = shoe;
        indexesOfCards = new int[maxNumCardsInHand];
        reset();
    }

    public String show() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numCardsInHand; i++) {
            builder.append(shoe.getCardForDisplay(indexesOfCards[i]));
            builder.append(" ");
        }
        return builder.toString();
    }

    public String showCard(int cardIndex) {
        return shoe.getCardForDisplay(cardIndex);
    }

    public void reset() {
        numCardsInHand = 0;
        isTwentyOnePoints = false;
        resetHelper();
    }

    public boolean hasCards() {
        return numCardsInHand > 0;
    }

    public abstract void resetHelper();

    public void addCard(int cardIndex) {
        indexesOfCards[numCardsInHand++] = cardIndex;
    }

    protected int computeMinPointSum() {
        int sum = 0;
        for (int i = 0; i < numCardsInHand; i++) {
            sum += shoe.getCardPointValue(indexesOfCards[i]);
        }
        return sum;
    }

    protected int computeMaxPointSum() {
        if (isTwentyOnePoints) {
            return 21;
        }

        boolean hasAnAce = false;
        int sum = 0;
        for (int i = 0; i < numCardsInHand; i++) {
            sum += shoe.getCardPointValue(indexesOfCards[i]);
            if (shoe.isAce(indexesOfCards[i])) {
                hasAnAce = true;
            }
        }
        if (hasAnAce && sum <= 11) {
            return sum + 10;
        }
        return sum;
    }

    protected boolean hasAtLeastOneAce() {
        for (int i = 0; i < numCardsInHand; i++) {
            if (shoe.isAce(indexesOfCards[i])) {
                return true;
            }
        }
        return false;
    }

    // returns true if the hand has an ace that can be 11
    public boolean isSoft() {
        boolean hasAtLeastOneAce = false;
        int sum = 0;
        for (int i = 0; i < numCardsInHand; i++) {
            sum += shoe.getCardPointValue(indexesOfCards[i]);
            if (shoe.isAce(indexesOfCards[i])) {
                hasAtLeastOneAce = true;
            }
        }

        if (!hasAtLeastOneAce) {
            return false;
        }
        return sum <= 11;
    }

    public boolean isBlackjack() {
        if (numCardsInHand != 2) {
            return false;
        }

        boolean hasAtLeastOneAce = false;
        int sum = 0;
        for (int i = 0; i < numCardsInHand; i++) {
            sum += shoe.getCardPointValue(indexesOfCards[i]);
            if (shoe.isAce(indexesOfCards[i])) {
                hasAtLeastOneAce = true;
            }
        }

        return hasAtLeastOneAce && sum == 11;
    }

    public boolean isBusted() {
        return computeMinPointSum() > 21;
    }

    public void setIsTwentyOnePoints() {
        isTwentyOnePoints = true;
    }
}
