package david.arnold.richardson.fastjack;

public class HandForTesting extends Hand {
    public HandForTesting(
            Shoe shoe,
            int maxNumCardsInHand) {
        super(shoe, maxNumCardsInHand);
    }

    @Override
    public void resetHelper() {
        // noop
    }
}
