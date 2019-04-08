package david.arnold.richardson.fastjack;

public class HandForDealer extends Hand {
    public HandForDealer(Shoe shoe) {
        // could be all twos, to a max of 9 (18 points), where the dealer must stand.
        super(shoe, 9);
    }

    public boolean shouldDealerHit() {
        int minPointSum = computeMinPointSum();
        boolean hasAce = hasAtLeastOneAce();
        if (hasAce && minPointSum == 7) {
            return shoe.getRules().isH17();
        }
        return minPointSum < 17;
    }
}
