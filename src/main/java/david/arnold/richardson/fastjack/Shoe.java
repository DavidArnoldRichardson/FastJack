package david.arnold.richardson.fastjack;

import static david.arnold.richardson.fastjack.Rules.*;

public class Shoe {

    private Rules rules;
    private final byte[] cards;
    private final int numCards;
    private final int numDecks;

    private static final int SUITE_BITS_LEFT_SHIFT = 4;
    private static final byte SUITE_MASK = 0x03 << SUITE_BITS_LEFT_SHIFT;
    private static final byte CARD_SYMBOL_MASK = 0x0F;

    public Shoe(Rules rules) {
        this.rules = rules;
        this.numDecks = rules.getNumDecks();
        this.numCards = numDecks * Rules.NUM_CARDS_PER_DECK;
        this.cards = createCards();
        shuffle();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numCards; i++) {
            result.append(getCardSymbol(i));
            result.append(getSuiteSymbol(i));
            result.append(" ");
        }
        return result.toString();
    }

    public char getSuiteSymbol(int cardIndex) {
        return SUITE_SYMBOLS.charAt((cards[cardIndex] & SUITE_MASK) >> SUITE_BITS_LEFT_SHIFT);
    }

    public char getCardSymbol(int cardIndex) {
        return CARD_SYMBOLS.charAt((cards[cardIndex] & CARD_SYMBOL_MASK));
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

    private byte createCard(
            byte suiteIndex,
            byte cardValueIndex) {
        return (byte) ((byte) (suiteIndex << SUITE_BITS_LEFT_SHIFT) | cardValueIndex);
    }

    private void shuffle() {
        Randomness randomness = rules.getRandomness();
        for (int i = 0; i < numCards; i++) {
            int randomPosition = randomness.getRandomInt(numCards);
            byte temp = cards[i];
            cards[i] = cards[randomPosition];
            cards[randomPosition] = temp;
        }
    }
}
