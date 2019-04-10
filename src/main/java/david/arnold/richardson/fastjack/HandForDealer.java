package david.arnold.richardson.fastjack;

public class HandForDealer extends Hand {

    private static final int CARD_INDEX_OF_UPCARD = 0;
    private static final int CARD_INDEX_OF_HOLE_CARD = 1;

    public HandForDealer(Shoe shoe) {
        // could be all twos, to a max of 9 (18 points), where the dealer must stand.
        super(shoe, 9);
    }

    @Override
    public void resetHelper() {
        // nothing to do here
    }

    public boolean isBlackjack() {
        return isBlackjack(false);
    }

    public boolean shouldDealerHit() {
        int minPointSum = computeMinPointSum();
        boolean hasAce = hasAtLeastOneAce();
        if (hasAce) {
            if (minPointSum == 7) {
                return shoe.getRules().isH17();
            }
            return minPointSum <= 6;
        } else {
            return minPointSum < 17;
        }
    }

    public String showHoleCard() {
        return showCard(CARD_INDEX_OF_HOLE_CARD);
    }

    public String showUpcard() {
        return showCard(CARD_INDEX_OF_UPCARD);
    }

    public boolean isUpcardAce() {
        return shoe.getCardPointValue(indexesOfCards[CARD_INDEX_OF_UPCARD]) == 1;
    }

    public boolean isUpcardTen() {
        return shoe.getCardPointValue(indexesOfCards[CARD_INDEX_OF_UPCARD]) == 10;
    }
}
