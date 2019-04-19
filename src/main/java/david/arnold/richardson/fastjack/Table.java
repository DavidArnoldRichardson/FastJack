package david.arnold.richardson.fastjack;

import java.util.HashSet;
import java.util.Set;

public class Table {

    public static final int NUM_SEATS = 7;

    private Outputter outputter;
    private Seat[] seats;
    private int numSeatsInUse = 0;
    private Shoe shoe;
    private Rules rules;
    private HandForDealer handForDealer;
    private MoneyPile moneyPile;

    public Table(
            Outputter outputter,
            Rules rules) {
        this.outputter = outputter;
        this.rules = rules;
        this.moneyPile = MoneyPile.createTableMoneyPile();
        this.shoe = new Shoe(this, outputter);

        seats = new Seat[NUM_SEATS];
        for (int i = 0; i < NUM_SEATS; i++) {
            seats[i] = new Seat(this, i);
        }
        handForDealer = new HandForDealer(shoe);
    }

    public Shoe getShoe() {
        return shoe;
    }

    public Seat addPlayer(Player player) {
        seats[numSeatsInUse].assignPlayerToSeat(player);
        Seat newlyOccupiedSeat = seats[numSeatsInUse];
        numSeatsInUse++;
        return newlyOccupiedSeat;
    }

    public Set<Player> getPlayers() {
        Set<Player> players = new HashSet<>();
        for (int i = 0; i < numSeatsInUse; i++) {
            players.add(seats[i].getPlayer());
        }
        return players;
    }

    public int playRounds(int numRoundsToPlay) {
        outputter.showRules(rules);

        shoe.shuffle();
        shoe.setPenetration();
        shoe.burnCards();

        // Note: Everything that happens inside this loop is optimized for performance.
        // Don't do anything unnecessary. No allocating memory from the heap if you can help it.
        int roundNumber = 0;
        boolean keepPlayingRounds = true;
        while (keepPlayingRounds) {
            boolean someonePlayed = playRound(roundNumber++);
            keepPlayingRounds = someonePlayed && (roundNumber < numRoundsToPlay);
        }
        return roundNumber;
    }

    public boolean playRound(int roundNumber) {
        outputter.startRound(roundNumber);

        if (shoe.hasCutCardBeenDrawn()) {
            outputter.cutCardWasDrawn();
            shoe.shuffle();
            shoe.setPenetration();
            shoe.burnCards();
        }

        // set player bets
        boolean atLeastOneBetWasPlaced = false;
        for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
            boolean betWasPlaced = seats[seatNumber].createNewHandWithBet();
            atLeastOneBetWasPlaced = atLeastOneBetWasPlaced || betWasPlaced;
        }

        if (!atLeastOneBetWasPlaced) {
            outputter.roundAborted();
            return false;
        }

        // deal first card to each player who bet
        for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
            seats[seatNumber].receiveCard();
        }

        // deal first card to dealer
        handForDealer.addCard(shoe.dealCard());

        // deal second card to each player
        for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
            seats[seatNumber].receiveSecondCard();
        }

        // deal hole card to dealer
        handForDealer.addCard(shoe.dealCard());
        outputter.showDealerUpcard(handForDealer);
        int dealerUpcardValue = handForDealer.getUpcardValue();

        // take insurance bets
        boolean upcardIsAce = dealerUpcardValue == 1;
        if (upcardIsAce) {
            outputter.dealerUpcardIsAce();
            for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
                seats[seatNumber].makeInsuranceBet();
            }
        }

        if (handForDealer.isSoftTwentyOne()) {
            if (upcardIsAce) {
                // pay out insurance
                outputter.revealDealerHand(handForDealer);
                for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
                    seats[seatNumber].payInsuranceToPlayer();
                }
            }

            outputter.dealerHasBlackjack(handForDealer);

            // clear out all the player hands because nobody plays when dealer gets blackjack
            for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
                seats[seatNumber].handleDealerGotBlackjack();
            }
        } else {

            // collect lost insurance bets
            if (upcardIsAce) {
                for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
                    seats[seatNumber].loseInsuranceBet();
                }
            }

            // Players now play their hands
            for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
                playSeat(seats[seatNumber], dealerUpcardValue);
            }

            // player blackjacks have already been paid, and those hands reset, at this point.

            boolean dealerMustPlay = checkIfDealerMustPlay();
            boolean dealerBusted = playDealer(dealerMustPlay);
            if (dealerBusted) {
                // pay players with bets still on their hands
                for (int seatNumber = numSeatsInUse - 1; seatNumber >= 0; seatNumber--) {
                    seats[seatNumber].handleDealerBust();
                }
            } else {
                // pay players with hands that beat the dealer's hand
                // collect bet money from players with hands that lose against the dealer's hand
                int dealerHandValue = handForDealer.computeMaxPointSum();
                for (int seatNumber = numSeatsInUse - 1; seatNumber >= 0; seatNumber--) {
                    seats[seatNumber].handleDealerStand(dealerHandValue);
                }
            }
        }

        handForDealer.reset();
        for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
            seats[seatNumber].resetHands();
        }

        return true;
    }

    private boolean checkIfDealerMustPlay() {
        for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
            if (seats[seatNumber].checkIfSeatNeedsDealerToPlay()) {
                return true;
            }
        }
        return false;
    }

    private void playSeat(
            Seat seat,
            int dealerUpcardValue) {
        long betAmount;
        long halfOfBetAmount;
        int playerHandValue;

        // first hand can be blackjack
        HandForPlayer firstHand = seat.getHand(0);
        if (firstHand.isSoftTwentyOne()) {
            Player player = seat.getPlayer();
            outputter.playerBlackjack(seat, firstHand);
            betAmount = firstHand.getMoneyPile().getAmount();
            firstHand.getMoneyPile().payAll(player.getMoneyPile());
            moneyPile.pay(player.getMoneyPile(), betAmount + (betAmount >> 1));
            firstHand.reset();
            return;
        }

        // More hands may be added due to splits.
        int handIndexToPlay = 0;

        while (seat.getNumHandsInUse() > handIndexToPlay) {
            HandForPlayer hand = seat.getHand(handIndexToPlay);

            boolean keepPlaying = true;

            // this hand was created from a split. It needs another card.
            if (hand.numCardsInHand == 1) {
                keepPlaying = playOnNewlySplitHand(seat, hand);
            }

            while (keepPlaying) {
                PlayerDecision playerDecision = seat.getPlayerDecision(handIndexToPlay, dealerUpcardValue);
                switch (playerDecision) {
                    case STD:
                        hand.setPlayIsComplete();
                        outputter.playerStand(seat, hand);
                        keepPlaying = false;
                        break;
                    case HIT:
                        hand.addCard(shoe.dealCard());
                        playerHandValue = hand.computeMaxPointSum();
                        keepPlaying = playerHandValue < 21;
                        if (playerHandValue > 21) {
                            outputter.playerHitAndBust(seat, hand);
                            hand.getMoneyPile().payAll(moneyPile);
                            hand.reset();
                        } else if (playerHandValue == 21) {
                            hand.setPlayIsComplete();
                            outputter.playerHitAndGot21(seat, hand);
                        } else {
                            outputter.playerHit(seat, hand);
                        }
                        break;
                    case SPL:
                        outputter.playerSplits(seat, hand);
                        seat.createSplitHand(handIndexToPlay);
                        keepPlaying = playOnNewlySplitHand(seat, hand);
                        break;
                    case DBL:
                        seat.getPlayer().getMoneyPile().pay(
                                hand.getMoneyPile(),
                                hand.getMoneyPile().getAmount());
                        hand.addCard(shoe.dealCard());
                        keepPlaying = false;
                        playerHandValue = hand.computeMaxPointSum();
                        if (playerHandValue > 21) {
                            hand.getMoneyPile().payAll(moneyPile);
                            hand.reset();
                        } else if (playerHandValue == 21) {
                            hand.setPlayIsComplete();
                            outputter.playerDoubledAndGot21(seat, hand);
                        } else {
                            hand.setPlayIsComplete();
                            outputter.playerDoubled(seat, hand);
                        }
                        break;
                    case SUR:
                        outputter.playerSurrendered(seat, hand);
                        halfOfBetAmount = hand.getMoneyPile().getAmount() >> 1;
                        hand.getMoneyPile().pay(seat.getPlayer().getMoneyPile(), halfOfBetAmount);
                        hand.getMoneyPile().payAll(moneyPile);
                        keepPlaying = false;
                        hand.reset();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + playerDecision);
                }
            }
            handIndexToPlay++;
        }
    }

    private boolean playOnNewlySplitHand(
            Seat seat,
            HandForPlayer hand) {

        hand.addCard(shoe.dealCard());

        // check various situations that would make the player unable to continue with this hand
        if (hand.isSoftTwentyOne()) {
            hand.setPlayIsComplete();
            outputter.gotSecondCardOnSplitAndGot21(seat, hand);
            return false;
        } else {
            if (hand.isPairOfAces()) {
                if (rules.isCanHitSplitAces()) {
                    return true;
                }
                if (rules.isCanResplitAces()) {
                    return true;
                }
                hand.setPlayIsComplete();
                outputter.gotSecondCardOnSplitAndCannotContinue(seat, hand);
                return false;
            }
            outputter.gotSecondCardOnSplit(seat, hand);
            return true;
        }
    }

    private boolean playDealer(boolean dealerMustPlay) {
        boolean dealerBusted;
        if (dealerMustPlay) {
            while (handForDealer.shouldDealerHit()) {
                handForDealer.addCard(shoe.dealCard());
            }
            dealerBusted = handForDealer.isBusted();
        } else {
            dealerBusted = false;
        }
        outputter.dealerHandResult(handForDealer, dealerBusted);
        return dealerBusted;
    }

    public Rules getRules() {
        return rules;
    }

    public void setRules(Rules rules) {
        this.rules = rules;
    }

    // used for testing
    public void tweakShoe(int... cardValuesToSet) {
        shoe.setPenetration();
        for (int i = 0; i < cardValuesToSet.length; i++) {
            shoe.cards[i] = (byte) (cardValuesToSet[i] - 1);
        }
    }

    public Outputter getOutputter() {
        return outputter;
    }

    public MoneyPile getMoneyPile() {
        return moneyPile;
    }

    public long getTableBankrollDelta() {
        return moneyPile.getAmount();
    }
}
