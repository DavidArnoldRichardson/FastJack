package david.arnold.richardson.fastjack;

public class Rules {

    public static final String CARD_SYMBOLS = "A23456789TJQK";
    public static final String SUITE_SYMBOLS = "♠♥♣♦";
    public static final int NUM_SUITES = SUITE_SYMBOLS.length();
    public static final int NUM_CARDS_PER_SUITE = CARD_SYMBOLS.length();
    public static final int NUM_CARDS_PER_DECK = NUM_SUITES * NUM_CARDS_PER_SUITE;

    private int numDecks;
    private Randomness randomness;

    public Rules(int numDecks) {
        this.numDecks = numDecks;
    }

    public int getNumDecks() {
        return numDecks;
    }

    public void setNumDecks(int numDecks) {
        this.numDecks = numDecks;
    }

    public Randomness getRandomness() {
        if (randomness == null) {
            this.randomness = new Randomness(Randomness.generateRandomSeed());
        }
        return randomness;
    }

    public void setRandomness(Randomness randomness) {
        this.randomness = randomness;
    }
}
