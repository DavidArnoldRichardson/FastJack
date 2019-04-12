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

    @Override
    public String toString() {
        return show();
    }

    public String show() {
        if (numCardsInHand == 0) {
            return "(empty hand)";
        }

        StringBuilder builder = new StringBuilder();
        boolean hasAtLeastOneAce = false;
        int minSum = 0;
        for (int i = 0; i < numCardsInHand; i++) {
            int indexOfCard = indexesOfCards[i];
            if (shoe.isAce(indexOfCard)) {
                hasAtLeastOneAce = true;
            }
            minSum += shoe.getCardPointValue(indexOfCard);
            builder.append(shoe.getCardForDisplay(indexOfCard));
            builder.append(" ");
        }

        builder.append("(");
        builder.append(minSum);
        boolean hasTwoValues = hasAtLeastOneAce && minSum <= 11;
        if (hasTwoValues) {
            builder.append(" or ");
            builder.append(minSum + 10);
        }
        builder.append(")");

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

    public abstract void resetHelper();

    public boolean hasCards() {
        return numCardsInHand > 0;
    }

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
