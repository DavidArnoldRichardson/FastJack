package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.play.LogicHolder;

public class Rules {

    public static final String CARD_SYMBOLS = "A23456789TJQK";
    public static final String SUITE_SYMBOLS = "♠♥♣♦";
    public static final int NUM_SUITES = SUITE_SYMBOLS.length();
    public static final int NUM_CARDS_PER_SUITE = CARD_SYMBOLS.length();
    public static final int NUM_CARDS_PER_DECK = NUM_SUITES * NUM_CARDS_PER_SUITE;

    private Randomness randomness;
    private LogicHolder logicHolder;

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
    private boolean canDoubleOnTenOrElevenOnly;

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
            boolean lateSurrenderAvailable,
            boolean canDoubleOnTenOrElevenOnly) {
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
        this.canDoubleOnTenOrElevenOnly = canDoubleOnTenOrElevenOnly;

        this.logicHolder = new LogicHolder();
        this.randomness = new Randomness();
    }

    public String show() {
        String prefix = "████ ";

        @SuppressWarnings("StringBufferReplaceableByString")
        StringBuilder builder = new StringBuilder();
        builder.append("███████████████████████████████████████\n");
        builder.append(prefix).append("Seed: ").append(randomness.getSeed()).append("\n");
        builder.append(prefix).append("Number of decks: ").append(numDecks).append("\n");
        builder.append(prefix).append("Range of cards behind cut card: ")
                .append(minNumCardsBehindCutCard).append("-")
                .append(maxNumCardsBehindCutCard).append("\n");
        builder.append(prefix).append("Num burn cards: ").append(numBurnCards).append("\n");
        builder.append(prefix).append("Show burn cards: ").append(showBurnCards).append("\n");
        builder.append(prefix).append("Dealer hits soft 17s: ").append(isH17).append("\n");
        builder.append(prefix).append("Number of splits permitted: ").append(maxNumSplits).append("\n");
        builder.append(prefix).append("Can resplit aces: ").append(canResplitAces).append("\n");
        builder.append(prefix).append("Can hit split aces: ").append(canHitSplitAces).append("\n");
        builder.append(prefix).append("Can double down after split: ").append(canDoubleAfterSplit).append("\n");
        builder.append(prefix).append("Minimum bet amount: ").append(MoneyPile.show(minBetAmount)).append("\n");
        builder.append(prefix).append("Maximum bet amount: ").append(MoneyPile.show(maxBetAmount)).append("\n");
        builder.append(prefix).append("Late surrender available: ").append(lateSurrenderAvailable).append("\n");
        builder.append(prefix).append("Double down only on 10 or 11: ").append(canDoubleOnTenOrElevenOnly).append("\n");
        builder.append(prefix).append("Basic Strategy player edge: ").append(getPlayerEdge()).append("%\n");
        builder.append("███████████████████████████████████████");
        return builder.toString();
    }

    public String showSummary() {
        StringBuilder builder = new StringBuilder();
        builder.append("[").append(numDecks).append("D,");
        if (isH17) {
            builder.append(PlayerEdge.RULE_LABEL_H17 + ",");
        } else {
            builder.append(PlayerEdge.RULE_LABEL_S17 + ",");
        }

        if (!canResplitAces) {
            builder.append("n");
        }
        builder.append(PlayerEdge.RULE_LABEL_RSA + ",");

        if (!canDoubleAfterSplit) {
            builder.append("n");
        }
        builder.append(PlayerEdge.RULE_LABEL_DAS + ",");

        if (!canHitSplitAces) {
            builder.append("n");
        }
        builder.append(PlayerEdge.RULE_LABEL_HSA + ",");

        if (!lateSurrenderAvailable) {
            builder.append("n");
        }
        builder.append(PlayerEdge.RULE_LABEL_LS + ",");

        if (!canDoubleOnTenOrElevenOnly) {
            builder.append("n");
        }
        builder.append(PlayerEdge.RULE_LABEL_RDD + "]");

        return builder.toString();
    }

    public static Rules getCommon(
            PlayerEdge playerEdge,
            int numDecks,
            int minNumCardsBehindCutCard,
            int maxNumCardsBehindCutCard) {
        return playerEdge.getRules(
                numDecks,
                minNumCardsBehindCutCard,
                maxNumCardsBehindCutCard);
    }

    public static Rules getDefaultMostCommon() {
        return new Rules(
                6,
                125,
                125,
                1,
                false,
                true,
                3,
                false,
                false,
                true,
                500L,
                20000L,
                false,
                false);
    }

    public static Rules getDefaultSixDecks() {
        return new Rules(
                6,
                52 + 26,
                52 * 2,
                1,
                false,
                true,
                3,
                true,
                false,
                false,
                500L,
                20000L,
                false,
                false);
    }

    public static Rules getDefaultOneDeck() {
        return new Rules(
                1,
                26,
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
                false,
                false);
    }

    public static Rules getWendover6D() {
        return new Rules(
                6,
                30,
                70,
                1,
                false,
                true,
                3,
                true,
                false,
                false,
                500L,
                20000L,
                false,
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
                false,
                false);
    }

    public Randomness getRandomness() {
        return randomness;
    }

    // used for testing
    public void setRandomness(Randomness randomness) {
        this.randomness = randomness;
    }

    public LogicHolder getLogicHolder() {
        return logicHolder;
    }

    public double getPlayerEdge() {
        return PlayerEdge.getPlayerEdgeValue(this);
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

    public boolean isCanDoubleOnTenOrElevenOnly() {
        return canDoubleOnTenOrElevenOnly;
    }

    public void setCanDoubleOnTenOrElevenOnly(boolean canDoubleOnTenOrElevenOnly) {
        this.canDoubleOnTenOrElevenOnly = canDoubleOnTenOrElevenOnly;
    }
}
