package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.PlayerDecision;

public abstract class Matrix {

    protected PlayerDecision[][] table;

    public PlayerDecision lookup(
            int playerHandMinPointSum,
            int dealerUpcardValue) {
        // Adjust the indexes:
        // the hand value index 0 corresponds to double aces (hand value of 2).
        // the dealer upcard index 0 corresponds to ace (value 1).
        return table[playerHandMinPointSum - 2][dealerUpcardValue - 1];
    }
}
