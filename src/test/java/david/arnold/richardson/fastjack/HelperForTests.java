package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.bet.BetStrategyAlwaysMin;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategy;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategyBasic;
import org.junit.Before;

public abstract class HelperForTests {

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
        rulesDefault = Rules.getDefault();

        rulesDoubleDownLimited = Rules.getDefault();
        rulesDoubleDownLimited.setCanDoubleOnTenOrElevenOnly(true);

        rulesCanSurrender = Rules.getDefault();
        rulesCanSurrender.setLateSurrenderAvailable(true);

        rulesCannotSurrender = Rules.getDefault();
        rulesCannotSurrender.setLateSurrenderAvailable(false);

        shoe = new Shoe(
                rulesDefault,
                new OutputterSilentAndFast());

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

    protected PlayerDecision compute(int... cards) {
        return compute(rulesDefault, true, cards);
    }

    protected PlayerDecision computeCannotSplit(int... cards) {
        return compute(rulesDefault, false, cards);
    }

    protected PlayerDecision compute(
            Rules rules,
            int... cards) {
        return compute(rules, true, cards);
    }

    protected PlayerDecision compute(
            Rules rules,
            boolean splittingAvailable,

            // the first ones are indexes into the shoe
            // the last one is the dealer upcard value (needs to be converted to index into shoe).
            int... cards) {

        // todo: There is a bug here. There are two shoes, one built by the table, and this one.
        //  Problem is there are Rules all over the place. Should consolidate those, and only store them on the Table
        //  object, and have everything pull from that, and not have its own copy. Everything can link to the table.
        shoe.setRules(rules);
        PlayStrategy playStrategy = new PlayStrategyBasic(rules);
        Table table = new Table(new OutputterSilentAndFast(), rules);
        Player player = new Player("test", 100000L, rules, table);
        player.setStrategies(playStrategy, new BetStrategyAlwaysMin(player, rules));
        Seat seat = table.addPlayer(player);

        int indexOfDealerUpcardValue = cards.length - 1;
        int dealerUpcardValue = cards[indexOfDealerUpcardValue];
        int dealerUpcardIndexIntoShoe;
        switch(dealerUpcardValue) {
            case 1:
                dealerUpcardIndexIntoShoe = cA;
                break;
            case 2:
                dealerUpcardIndexIntoShoe = c2;
                break;
            case 3:
                dealerUpcardIndexIntoShoe = c3;
                break;
            case 4:
                dealerUpcardIndexIntoShoe = c4;
                break;
            case 5:
                dealerUpcardIndexIntoShoe = c5;
                break;
            case 6:
                dealerUpcardIndexIntoShoe = c6;
                break;
            case 7:
                dealerUpcardIndexIntoShoe = c7;
                break;
            case 8:
                dealerUpcardIndexIntoShoe = c8;
                break;
            case 9:
                dealerUpcardIndexIntoShoe = c9;
                break;
            case 10:
                dealerUpcardIndexIntoShoe = cT;
                break;
            default:
                throw new RuntimeException("bad value");
        }

        int numCardsInHand = cards.length - 1;
        HandForPlayer hand = new HandForPlayer(table.getShoe(), seat);
        for (int i = 0; i < numCardsInHand; i++) {
            hand.addCard(cards[i]);
        }
        System.out.println(hand.show());

        if (!splittingAvailable) {
            hand.setHandIsResultOfSplit();
            seat.setNumHandsInUse(rules.getMaxNumSplits() + 1);
        }

        return playStrategy.getPlay(hand, dealerUpcardIndexIntoShoe);
    }
}
