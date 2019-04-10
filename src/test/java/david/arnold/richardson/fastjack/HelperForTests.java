package david.arnold.richardson.fastjack;

import org.junit.Before;

public abstract class HelperForTests {

    protected Rules rules = Rules.getDefault();
    protected Shoe shoe;

    // These are indexes to a card with the corresponding value.
    protected int cA;
    protected int c2;
    protected int c3;
    protected int c4;
    protected int c5;
    protected int c6;
    protected int c7;
    protected int c8;
    protected int c9;
    protected int cT;

    @Before
    public void setupOneTest() {
        shoe = new Shoe(rules, new OutputterSilentAndFast());
        prepareCardsForTests(shoe);
    }

    protected void resetHand(Hand hand, int... cardIndexes) {
        hand.reset();
        for (int cardIndex : cardIndexes) {
            hand.addCard(cardIndex);
        }
    }

    // We override some of the cards, at the very back of the shoe,
    // so we can use their indexes for tests.
    private void prepareCardsForTests(Shoe shoe) {
        byte suite = 0x00;
        byte cardValue = 9; // This is a ten, zero-based.
        int index = shoe.getNumCards() - 1;

        shoe.cards[index] = shoe.createCard(suite, cardValue--);
        cT = index--;
        shoe.cards[index] = shoe.createCard(suite, cardValue--);
        c9 = index--;
        shoe.cards[index] = shoe.createCard(suite, cardValue--);
        c8 = index--;
        shoe.cards[index] = shoe.createCard(suite, cardValue--);
        c7 = index--;
        shoe.cards[index] = shoe.createCard(suite, cardValue--);
        c6 = index--;
        shoe.cards[index] = shoe.createCard(suite, cardValue--);
        c5 = index--;
        shoe.cards[index] = shoe.createCard(suite, cardValue--);
        c4 = index--;
        shoe.cards[index] = shoe.createCard(suite, cardValue--);
        c3 = index--;
        shoe.cards[index] = shoe.createCard(suite, cardValue--);
        c2 = index--;
        shoe.cards[index] = shoe.createCard(suite, cardValue);
        cA = index;
    }
}
