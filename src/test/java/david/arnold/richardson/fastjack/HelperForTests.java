package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.bet.BetStrategyAlwaysMin;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategy;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategyBasic;
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

    protected final static int Ace = 1;
    protected final static int Two = 2;
    protected final static int Three = 3;
    protected final static int Four = 4;
    protected final static int Five = 5;
    protected final static int Six = 6;
    protected final static int Seven = 7;
    protected final static int Eight = 8;
    protected final static int Nine = 9;
    protected final static int Ten = 10;

    protected Rules rulesDefault = null;
    protected Rules rulesDoubleDownLimited = null;
    protected Rules rulesCanSurrender = null;
    protected Rules rulesCannotSurrender = null;

    @Before
    public void setupOneTest() {
        shoe = new Shoe(rules, new OutputterSilentAndFast());
        prepareCardsForTests(shoe);

        rulesDefault = Rules.getDefault();

        rulesDoubleDownLimited = Rules.getDefault();
        rulesDoubleDownLimited.setCanDoubleOnTenOrElevenOnly(true);

        rulesCanSurrender = Rules.getDefault();
        rulesCanSurrender.setLateSurrenderAvailable(true);

        rulesCannotSurrender = Rules.getDefault();
        rulesCannotSurrender.setLateSurrenderAvailable(false);
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

        // todo: replace with an array
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

    protected PlayerDecision compute(int... values) {
        return compute(rulesDefault, values);
    }

    protected PlayerDecision computeCannotSplit(int... values) {
        return compute(rules, true, values);
    }

    protected PlayerDecision compute(
            Rules rules,
            int... values) {
        return compute(rules, false, values);
    }

    protected PlayerDecision compute(
            Rules rules,
            boolean splittingNotAvailable,
            int... values) {
        int numValues = values.length;
        int numCardsInHand = numValues - 1;
        int[] handIndexes = new int[numValues - 1];
        for (int i = 0; i < numCardsInHand; i++) {
            switch(values[i]) {
                // todo: use an array
                case 1:
                    handIndexes[i] = cA;
                    break;
                case 2:
                    handIndexes[i] = c2;
                    break;
                case 3:
                    handIndexes[i] = c3;
                    break;
                case 4:
                    handIndexes[i] = c4;
                    break;
                case 5:
                    handIndexes[i] = c5;
                    break;
                case 6:
                    handIndexes[i] = c6;
                    break;
                case 7:
                    handIndexes[i] = c7;
                    break;
                case 8:
                    handIndexes[i] = c8;
                    break;
                case 9:
                    handIndexes[i] = c9;
                    break;
                case 10:
                    handIndexes[i] = cT;
                    break;
                default:
                    throw new RuntimeException("bad value");
            }
        }

        int indexOfDealerUpcardValue = numValues - 2;
        int dealerUpcardValue = values[indexOfDealerUpcardValue];

        PlayStrategy playStrategy = new PlayStrategyBasic(rules);
        Table table = new Table(
                new OutputterSilentAndFast(),
                rules);
        Player player = new Player("test", 100000L, rules, table);
        player.setStrategies(playStrategy, new BetStrategyAlwaysMin(player, rules));
        Seat seat = table.addPlayer(player);

        HandForPlayer hand = new HandForPlayer(table.getShoe(), seat);
        for (int i = 0; i < numCardsInHand; i++) {
            hand.addCard(handIndexes[i]);
        }

        if (splittingNotAvailable) {
            hand.setHandIsResultOfSplit();
            seat.setNumHandsInUse(rules.getMaxNumSplits() + 1);
        }

        return playStrategy.getPlay(hand, dealerUpcardValue);
    }
}
