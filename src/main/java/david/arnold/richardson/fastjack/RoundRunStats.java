package david.arnold.richardson.fastjack;

public class RoundRunStats {
    int roundNumber;
    int numShoesPlayed;

    public RoundRunStats() {
        roundNumber = 0;
        numShoesPlayed = 0;
    }

    public void incrementRoundNumber() {
        roundNumber++;
    }

    public void incrementNumShoesPlayed() {
        numShoesPlayed++;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int getNumShoesPlayed() {
        return numShoesPlayed;
    }
}
