package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.bet.BetStrategyAlwaysMin;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategy;
import david.arnold.richardson.fastjack.strategy.play.PlayStrategyBasic;

public abstract class HelperForTests {

    Table tableWithOneDeck;
    Table tableWithSixDecks;

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

    protected Rules rulesDefault6D;
    protected Rules rulesDefault1D;
    protected Rules rulesDoubleDownLimited;
    protected Rules rulesCanSurrender;
    protected Rules rulesCannotSurrender;

    public HelperForTests() {
        rulesDefault6D = Rules.getDefaultSixDecks();
        rulesDefault1D = Rules.getDefaultOneDeck();

        rulesDoubleDownLimited = Rules.getDefaultSixDecks();
        rulesDoubleDownLimited.setCanDoubleOnTenOrElevenOnly(true);

        rulesCanSurrender = Rules.getDefaultSixDecks();
        rulesCanSurrender.setLateSurrenderAvailable(true);

        rulesCannotSurrender = Rules.getDefaultSixDecks();
        rulesCannotSurrender.setLateSurrenderAvailable(false);

        tableWithOneDeck = new Table(new OutputterSilentAndFast(), rulesDefault1D);
        tableWithSixDecks = new Table(new OutputterSilentAndFast(), rulesDefault6D);

        prepareCardsForTests(tableWithOneDeck.getShoe());
        prepareCardsForTests(tableWithSixDecks.getShoe());
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
        return compute(rulesDefault6D, true, cards);
    }

    protected PlayerDecision computeCannotSplit(int... cards) {
        return compute(rulesDefault6D, false, cards);
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

        tableWithSixDecks.setRules(rules);
        PlayStrategy playStrategy = new PlayStrategyBasic(rules);
        Player player = new Player("test", 100000L, tableWithSixDecks);
        player.setStrategies(playStrategy, new BetStrategyAlwaysMin(player, rules));
        Seat seat = tableWithSixDecks.addPlayer(player);

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
        HandForPlayer hand = new HandForPlayer(tableWithSixDecks.getShoe(), seat);
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
