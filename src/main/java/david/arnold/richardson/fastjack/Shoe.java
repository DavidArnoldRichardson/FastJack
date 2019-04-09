package david.arnold.richardson.fastjack;

import static david.arnold.richardson.fastjack.Rules.*;

public class Shoe {

    private Rules rules;

    // All the card values are stored here in this array. They never leave.
    // All reference to the cards is done via the index into this array.
    byte[] cards;

    private final int numCards;
    private final int numDecks;

    // note: This is -1 if there are no burn cards.
    private int indexOfLastBurnCard;

    private int indexOfNextCardToBeDealt;
    private int indexOfCutCard;

    private static final int SUITE_BITS_LEFT_SHIFT = 4;
    private static final byte SUITE_MASK = 0x03 << SUITE_BITS_LEFT_SHIFT;
    private static final byte CARD_SYMBOL_MASK = 0x0F;

    public Shoe(Rules rules) {
        this.rules = rules;
        this.numDecks = rules.getNumDecks();
        this.numCards = numDecks * Rules.NUM_CARDS_PER_DECK;
        this.cards = createCards();
        resetAndShuffle();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numCards; i++) {
            result.append(getCardForDisplay(i));
            result.append(" ");
        }
        return result.toString();
    }

    public Rules getRules() {
        return rules;
    }

    private String getSuiteSymbol(int cardIndex) {
        return String.valueOf(SUITE_SYMBOLS.charAt((cards[cardIndex] & SUITE_MASK) >> SUITE_BITS_LEFT_SHIFT));
    }

    private String getCardValueSymbol(int cardIndex) {
        return String.valueOf(CARD_SYMBOLS.charAt((cards[cardIndex] & CARD_SYMBOL_MASK)));
    }

    public String getCardForDisplay(int cardIndex) {
        return getCardValueSymbol(cardIndex) + getSuiteSymbol(cardIndex);
    }

    public boolean isAce(int cardIndex) {
        return (cards[cardIndex] & CARD_SYMBOL_MASK) == 0;
    }

    public int getCardPointValue(int cardIndex) {
        // card value is +1 because it's zero-based.
        int cardValue = (cards[cardIndex] & CARD_SYMBOL_MASK) + 1;
        return cardValue >= 10 ? 10 : cardValue;
    }

    public int getNumCards() {
        return numCards;
    }

    public boolean hasCutCardBeenDrawn() {
        return indexOfNextCardToBeDealt >= indexOfCutCard;
    }

    private byte[] createCards() {
        int cardIndex = 0;
        byte[] cards = new byte[numCards];
        for (int deckNumber = 0; deckNumber < numDecks; deckNumber++) {
            for (byte suiteIndex = 0; suiteIndex < NUM_SUITES; suiteIndex++) {
                for (byte cardValueIndex = 0; cardValueIndex < NUM_CARDS_PER_SUITE; cardValueIndex++) {
                    cards[cardIndex++] = createCard(suiteIndex, cardValueIndex);
                }
            }
        }
        return cards;
    }

    byte createCard(
            byte suiteIndex,
            byte cardValueIndex) {
        return (byte) ((byte) (suiteIndex << SUITE_BITS_LEFT_SHIFT) | cardValueIndex);
    }

    private void resetAndShuffle() {
        Randomness randomness = rules.getRandomness();
        for (int i = 0; i < numCards; i++) {
            int randomPosition = randomness.getRandomInt(numCards);
            byte temp = cards[i];
            cards[i] = cards[randomPosition];
            cards[randomPosition] = temp;
        }
        this.indexOfNextCardToBeDealt = 0;
        this.indexOfLastBurnCard = 0;
        setCutCardIndex();
        burnCards();
    }

    private void setCutCardIndex() {
        // todo: make this random between the min and max
        this.indexOfCutCard = numCards - rules.getMinNumCardsBehindCutCard();
    }

    private void burnCards() {
        int numBurnCards = rules.getNumBurnCards();
        this.indexOfLastBurnCard = numBurnCards - 1;
        this.indexOfNextCardToBeDealt = numBurnCards;
    }
}
