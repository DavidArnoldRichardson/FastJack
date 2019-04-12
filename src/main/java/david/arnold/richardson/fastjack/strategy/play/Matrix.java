package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.HandForPlayer;
import david.arnold.richardson.fastjack.PlayerDecision;
import david.arnold.richardson.fastjack.Rules;

public abstract class Matrix {

    protected Rules rules;
    protected PlayerDecision[][] table;

    public Matrix(Rules rules) {
        this.rules = rules;
    }

    public abstract PlayerDecision lookup(
            HandForPlayer hand,
            int playerHandMinPointSum,
            int dealerUpcardValue);

    protected PlayerDecision lookup(
            PlayerDecision[][] table,
            int playerHandMinPointSum,
            int dealerUpcardValue) {
        // Adjust the indexes:
        // the hand value index 0 corresponds to double aces (hand value of 2).
        // the dealer upcard index 0 corresponds to ace (value 1).
        return table[playerHandMinPointSum - 2][dealerUpcardValue - 1];
    }
}
