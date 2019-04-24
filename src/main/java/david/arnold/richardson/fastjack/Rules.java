package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.play.LogicHolder;

import java.util.HashMap;
import java.util.Map;

public class Rules {

    public static final String CARD_SYMBOLS = "A23456789TJQK";
    public static final String SUITE_SYMBOLS = "♠♥♣♦";
    public static final int NUM_SUITES = SUITE_SYMBOLS.length();
    public static final int NUM_CARDS_PER_SUITE = CARD_SYMBOLS.length();
    public static final int NUM_CARDS_PER_DECK = NUM_SUITES * NUM_CARDS_PER_SUITE;

    private Randomness randomness;
    private LogicHolder logicHolder;
    private Map<String, double[]> playerEdgeLookup;

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

        playerEdgeLookup = new HashMap<>();
        playerEdgeLookup.put("H17", new double[] {-0.3D, -0.599D, -0.738D, -0.784D, -0.807D});
        playerEdgeLookup.put("H17,RSA", new double[] {-0.26D, -0.529D, -0.654D, -0.696D, -0.717D});
        playerEdgeLookup.put("H17,DAS", new double[] {-0.17D, -0.456D, -0.595D, -0.640D, -0.664D});
        playerEdgeLookup.put("H17,DAS,RSA", new double[] {-0.12D, -0.387D, -0.512D, -0.552D, -0.573D});
        playerEdgeLookup.put("H17,LS", new double[] {-0.30D, -0.546D, -0.661D, -0.696D, -0.719D});
        playerEdgeLookup.put("H17,RSA,LS", new double[] {-0.25D, -0.476D, -0.578D, -0.609D, -0.629D});
        playerEdgeLookup.put("H17,DAS,LS", new double[] {-0.16D, -0.404D, -0.518D, -0.556D, -0.576D});
        playerEdgeLookup.put("H17,DAS,RSA,LS", new double[] {-0.12D, -0.334D, -0.434D, -0.468D, -0.486D});
        playerEdgeLookup.put("S17", new double[] {-0.11D, -0.395D, -0.526D, -0.569D, -0.589D});
        playerEdgeLookup.put("S17,RSA", new double[] {-0.06D, -0.323D, -0.44D, -0.478D, -0.496D});
        playerEdgeLookup.put("S17,DAS", new double[] {0.02D, -0.256D, -0.385D, -0.428D, -0.449D});
        playerEdgeLookup.put("S17,DAS,RSA", new double[] {0.07D, -0.184D, -0.3D, -0.337D, -0.356D});
        playerEdgeLookup.put("S17,LS", new double[] {-0.11D, -0.356D, -0.462D, -0.5D, -0.517D});
        playerEdgeLookup.put("S17,RSA,LS", new double[] {-0.07D, -0.285D, -0.377D, -0.409D, -0.423D});
        playerEdgeLookup.put("S17,DAS,LS", new double[] {0.02D, -0.216D, -0.324D, -0.359D, -0.374D});
        playerEdgeLookup.put("S17,DAS,RSA,LS", new double[] {0.06D, -0.145D, -0.239D, -0.269D, -0.281D});
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
            builder.append("H17,");
        } else {
            builder.append("S17,");
        }

        if (!canResplitAces) {
            builder.append("n");
        }
        builder.append("RSA,");

        if (!canDoubleAfterSplit) {
            builder.append("n");
        }
        builder.append("DAS,");

        if (!canHitSplitAces) {
            builder.append("n");
        }
        builder.append("HSA,");

        if (!lateSurrenderAvailable) {
            builder.append("n");
        }
        builder.append("LS,");

        if (!canDoubleOnTenOrElevenOnly) {
            builder.append("n");
        }
        builder.append("RDD]");

        return builder.toString();
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
        int edgeLookupIndex = getEdgeLookupNumDeckIndex();
        if (edgeLookupIndex == -1) {
            System.out.println("Invalid number of decks: " + numDecks);
            return 999D; // just a crazy value
        }
        String edgeLookupString = generateEdgeLookupString();
        double[] edgeValues = playerEdgeLookup.get(edgeLookupString);
        if (playerEdgeLookup == null) {
            System.out.println("Invalid rules configuration: " + edgeLookupString);
            return 998D; // just a crazy value
        }
        return edgeValues[edgeLookupIndex];
    }

    private int getEdgeLookupNumDeckIndex() {
        switch(numDecks) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 4:
                return 2;
            case 6:
                return 3;
            case 8:
                return 4;
            default:
                return -1; // unknown
        }
    }

    private String generateEdgeLookupString() {
        return (isH17 ? "H17" : "S17") +
                (canDoubleAfterSplit ? ",DAS" : "") +
                (canResplitAces ? ",RSA" : "") +
                (lateSurrenderAvailable ? ",LS" : "");
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
