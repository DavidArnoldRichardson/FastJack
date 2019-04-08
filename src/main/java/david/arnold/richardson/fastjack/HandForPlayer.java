package david.arnold.richardson.fastjack;

public class HandForPlayer extends Hand {
    public HandForPlayer(Shoe shoe) {
        // could be all twos, to a max of 11 (22 points), where the player busts.
        super(shoe, 11);
    }
}
