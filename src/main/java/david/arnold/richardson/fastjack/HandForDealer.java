package david.arnold.richardson.fastjack;

public class HandForDealer extends Hand {

    private static final int CARD_INDEX_OF_UPCARD = 0;
    private static final int CARD_INDEX_OF_HOLE_CARD = 1;

    public HandForDealer(Shoe shoe) {
        super(shoe);
    }

    @Override
    protected void resetHelper() {
        // nothing to do here
    }

    public boolean shouldDealerHit() {
        boolean hasAtLeastOneAce = false;
        int minimumSum = 0;
        for (int i = 0; i < numCardsInHand; i++) {
            minimumSum += shoe.getCardPointValue(indexesOfCards[i]);
            if (shoe.isAce(indexesOfCards[i])) {
                hasAtLeastOneAce = true;
            }
        }

        if (hasAtLeastOneAce) {
            switch (minimumSum) {
                case 2: // or 12
                case 3: // or 13
                case 4: // or 14
                case 5: // or 15
                case 6: // or 16
                    return true;
                case 7: // or 17
                    return shoe.getRules().isH17();
                case 8: // or 18
                case 9: // or 19
                case 10: // or 20
                case 11: // or 21
                    return false;
                case 12: // ace is low
                case 13: // ace is low
                case 14: // ace is low
                case 15: // ace is low
                case 16: // ace is low
                    return true;
                default:
                    // anything more, stand.
                    return false;
            }
        }

        return minimumSum < 17;
    }

    public String showHoleCard() {
        return showCard(indexesOfCards[CARD_INDEX_OF_HOLE_CARD]);
    }

    public String showUpcard() {
        return showCard(indexesOfCards[CARD_INDEX_OF_UPCARD]);
    }

    public int getUpcardValue() {
        return shoe.getCardPointValue(indexesOfCards[CARD_INDEX_OF_UPCARD]);
    }
}
