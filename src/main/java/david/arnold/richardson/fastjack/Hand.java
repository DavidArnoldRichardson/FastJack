package david.arnold.richardson.fastjack;

public abstract class Hand {
    protected Shoe shoe;
    protected int[] indexesOfCards;
    protected int numCardsInHand;
    protected boolean playIsComplete;

    protected Hand(Shoe shoe) {
        this.shoe = shoe;
        indexesOfCards = new int[21]; // Tried to figure out the actual max value. Complicated. This works.
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

        showPointValue(builder, minSum, hasAtLeastOneAce);
        return builder.toString();
    }

    public void showPointValue(
            StringBuilder builder,
            int minSum,
            boolean hasAtLeastOneAce) {
        builder.append("(");
        builder.append(minSum);
        boolean hasTwoValues = hasAtLeastOneAce && minSum <= 11;
        if (hasTwoValues) {
            builder.append(" or ");
            builder.append(minSum + 10);
        }
        builder.append(")");
    }

    public void showSummary(StringBuilder builder) {
        boolean hasAtLeastOneAce = false;
        int minSum = 0;
        for (int i = 0; i < numCardsInHand; i++) {
            int indexOfCard = indexesOfCards[i];
            if (shoe.isAce(indexOfCard)) {
                hasAtLeastOneAce = true;
            }
            minSum += shoe.getCardPointValue(indexOfCard);
            builder.append(Rules.CARD_SYMBOLS.charAt(shoe.getCardPointValue(indexOfCard) - 1));
        }

        builder.append(",").append(minSum).append(",");
        boolean hasTwoValues = hasAtLeastOneAce && minSum <= 11;
        if (hasTwoValues) {
            builder.append(minSum + 10);
        } else {
            builder.append(minSum);
        }
    }

    public String showCard(int cardIndex) {
        return shoe.getCardForDisplay(cardIndex);
    }

    public void reset() {
        numCardsInHand = 0;
        playIsComplete = false;
        resetHelper();
    }

    protected abstract void resetHelper();

    public boolean hasCards() {
        return numCardsInHand > 0;
    }

    public void addCard(int cardIndex) {
        indexesOfCards[numCardsInHand++] = cardIndex;
    }

    public int computeMinPointSum() {
        int sum = 0;
        for (int i = 0; i < numCardsInHand; i++) {
            sum += shoe.getCardPointValue(indexesOfCards[i]);
        }
        return sum;
    }

    protected int computeMaxPointSum() {
        boolean hasAnAce = false;
        int sum = 0;
        for (int i = 0; i < numCardsInHand; i++) {
            sum += shoe.getCardPointValue(indexesOfCards[i]);
            if (shoe.isAce(indexesOfCards[i])) {
                hasAnAce = true;
            }
        }
        if (hasAnAce && sum <= 11) {
            sum += 10;
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

    public boolean firstCardIsAce() {
        if (numCardsInHand >= 1) {
            return shoe.isAce(indexesOfCards[0]);
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

    // Note that a blackjack doesn't just mean that it's an ace and a ten-point card.
    // The player can get those two cards as a result of a split, for example.
    public boolean isSoftTwentyOne() {
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
}
