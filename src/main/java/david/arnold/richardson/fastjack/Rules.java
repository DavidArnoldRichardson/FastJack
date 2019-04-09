package david.arnold.richardson.fastjack;

public class Rules {

    public static final String CARD_SYMBOLS = "A23456789TJQK";
    public static final String SUITE_SYMBOLS = "♠♥♣♦";
    public static final int NUM_SUITES = SUITE_SYMBOLS.length();
    public static final int NUM_CARDS_PER_SUITE = CARD_SYMBOLS.length();
    public static final int NUM_CARDS_PER_DECK = NUM_SUITES * NUM_CARDS_PER_SUITE;

    private Randomness randomness;

    private int numDecks;
    private int minNumCardsBehindCutCard;
    private int maxNumCardsBehindCutCard;
    private int numBurnCards;
    private boolean isH17;

    public static Rules getDefault() {
        return new Rules(
                6,
                52,
                52,
                1,
                true);
    }

    public static Rules getWendover6D() {
        return new Rules(
                6,
                52,
                78,
                1,
                true);
    }

    public static Rules getWendover1D() {
        return new Rules(
                1,
                13,
                26,
                1,
                true);
    }

    public Rules(
            int numDecks,
            int minNumCardsBehindCutCard,
            int maxNumCardsBehindCutCard,
            int numBurnCards,
            boolean isH17) {
        this.numDecks = numDecks;
        this.minNumCardsBehindCutCard = minNumCardsBehindCutCard;
        this.maxNumCardsBehindCutCard = maxNumCardsBehindCutCard;
        this.numBurnCards = numBurnCards;
        this.isH17 = isH17;
    }

    public Randomness getRandomness() {
        if (randomness == null) {
            this.randomness = new Randomness(Randomness.generateRandomSeed());
        }
        return randomness;
    }

    public int getNumDecks() {
        return numDecks;
    }

    public void setNumDecks(int numDecks) {
        this.numDecks = numDecks;
    }

    public int getMinNumCardsBehindCutCard() {
        return minNumCardsBehindCutCard;
    }

    public void setMinNumCardsBehindCutCard(int minNumCardsBehindCutCard) {
        this.minNumCardsBehindCutCard = minNumCardsBehindCutCard;
    }

    public int getMaxNumCardsBehindCutCard() {
        return maxNumCardsBehindCutCard;
    }

    public void setMaxNumCardsBehindCutCard(int maxNumCardsBehindCutCard) {
        this.maxNumCardsBehindCutCard = maxNumCardsBehindCutCard;
    }

    public int getNumBurnCards() {
        return numBurnCards;
    }

    public void setNumBurnCards(int numBurnCards) {
        this.numBurnCards = numBurnCards;
    }

    public boolean isH17() {
        return isH17;
    }

    public void setH17(boolean h17) {
        isH17 = h17;
    }
}
