package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.play.DecisionTables;

public class Rules {

    public static final String CARD_SYMBOLS = "A23456789TJQK";
    public static final String SUITE_SYMBOLS = "♠♥♣♦";
    public static final int NUM_SUITES = SUITE_SYMBOLS.length();
    public static final int NUM_CARDS_PER_SUITE = CARD_SYMBOLS.length();
    public static final int NUM_CARDS_PER_DECK = NUM_SUITES * NUM_CARDS_PER_SUITE;

    private Randomness randomness;
    private DecisionTables decisionTables;

    private int numDecks;
    private int minNumCardsBehindCutCard;
    private int maxNumCardsBehindCutCard;
    private int numBurnCards;
    private boolean showBurnCards;
    private boolean isH17;
    private int maxNumSplits;
    private boolean canResplitAces;
    private boolean canHitSplitAces;
    private boolean canDoubleAfterSplit;
    private long minBetAmount;
    private long maxBetAmount;
    private boolean lateSurrenderAvailable;

    public static Rules getDefault() {
        return new Rules(
                6,
                52,
                52,
                1,
                false,
                true,
                3,
                true,
                false,
                false,
                500L,
                20000L,
                true);
    }

    public static Rules getWendover6D() {
        return new Rules(
                6,
                52,
                78,
                1,
                false,
                true,
                3,
                true,
                false,
                false,
                500L,
                20000L,
                false);
    }

    public static Rules getWendover1D() {
        return new Rules(
                1,
                13,
                26,
                1,
                false,
                true,
                3,
                true,
                false,
                false,
                500L,
                20000L,
                false);
    }

    public Rules(
            int numDecks,
            int minNumCardsBehindCutCard,
            int maxNumCardsBehindCutCard,
            int numBurnCards,
            boolean showBurnCards,
            boolean isH17,
            int maxNumSplits,
            boolean canResplitAces,
            boolean canHitSplitAces,
            boolean canDoubleAfterSplit,
            long minBetAmount,
            long maxBetAmount,
            boolean lateSurrenderAvailable) {
        this.numDecks = numDecks;
        this.minNumCardsBehindCutCard = minNumCardsBehindCutCard;
        this.maxNumCardsBehindCutCard = maxNumCardsBehindCutCard;
        this.numBurnCards = numBurnCards;
        this.showBurnCards = showBurnCards;
        this.isH17 = isH17;
        this.maxNumSplits = maxNumSplits;
        this.canResplitAces = canResplitAces;
        this.canHitSplitAces = canHitSplitAces;
        this.canDoubleAfterSplit = canDoubleAfterSplit;
        this.minBetAmount = minBetAmount;
        this.maxBetAmount = maxBetAmount;
        this.lateSurrenderAvailable = lateSurrenderAvailable;

        this.decisionTables = new DecisionTables(this);
    }

    public void setSeed(long seed) {
        this.randomness = new Randomness(seed);
    }

    public Randomness getRandomness() {
        if (randomness == null) {
            this.randomness = new Randomness(Randomness.generateRandomSeed());
        }
        return randomness;
    }

    public DecisionTables getDecisionTables() {
        return decisionTables;
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

    public boolean isShowBurnCards() {
        return showBurnCards;
    }

    public void setShowBurnCards(boolean showBurnCards) {
        this.showBurnCards = showBurnCards;
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

    public int getMaxNumSplits() {
        return maxNumSplits;
    }

    public void setMaxNumSplits(int maxNumSplits) {
        this.maxNumSplits = maxNumSplits;
    }

    public boolean isCanResplitAces() {
        return canResplitAces;
    }

    public void setCanResplitAces(boolean canResplitAces) {
        this.canResplitAces = canResplitAces;
    }

    public boolean isCanHitSplitAces() {
        return canHitSplitAces;
    }

    public void setCanHitSplitAces(boolean canHitSplitAces) {
        this.canHitSplitAces = canHitSplitAces;
    }

    public boolean isCanDoubleAfterSplit() {
        return canDoubleAfterSplit;
    }

    public void setCanDoubleAfterSplit(boolean canDoubleAfterSplit) {
        this.canDoubleAfterSplit = canDoubleAfterSplit;
    }

    public long getMinBetAmount() {
        return minBetAmount;
    }

    public void setMinBetAmount(long minBetAmount) {
        this.minBetAmount = minBetAmount;
    }

    public long getMaxBetAmount() {
        return maxBetAmount;
    }

    public void setMaxBetAmount(long maxBetAmount) {
        this.maxBetAmount = maxBetAmount;
    }

    public boolean isLateSurrenderAvailable() {
        return lateSurrenderAvailable;
    }

    public void setLateSurrenderAvailable(boolean lateSurrenderAvailable) {
        this.lateSurrenderAvailable = lateSurrenderAvailable;
    }
}
