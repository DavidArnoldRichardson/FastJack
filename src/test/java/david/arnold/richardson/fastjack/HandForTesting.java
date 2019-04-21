package david.arnold.richardson.fastjack;

public class HandForTesting extends Hand {
    public HandForTesting(
            Shoe shoe) {
        super(shoe);
    }

    @Override
    public void resetHelper() {
        // noop
    }
}
