package david.arnold.richardson.fastjack;

public class Table {

    public static final int NUM_SEATS = 7;

    private Outputter outputter;
    private Seat[] seats;
    private long[] insuranceBets;
    private int numSeatsInUse = 0;
    private Shoe shoe;
    private Rules rules;
    private HandForDealer handForDealer;
    private long tableBankrollStart = 100000000000000L;
    private long tableBankroll = tableBankrollStart;

    public Table(
            Outputter outputter,
            Rules rules) {
        this.outputter = outputter;
        this.shoe = new Shoe(rules, outputter);
        this.rules = shoe.getRules();

        seats = new Seat[NUM_SEATS];
        insuranceBets = new long[NUM_SEATS];
        for (int i = 0; i < NUM_SEATS; i++) {
            seats[i] = new Seat(this);
            insuranceBets[i] = 0L;
        }
        handForDealer = new HandForDealer(shoe);
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void addPlayer(Player player) {
        seats[numSeatsInUse++].assignPlayerToSeat(player);
    }

    public int playRounds(int numRoundsToPlay) {
        shoe.shuffle();
        shoe.setPenetration();
        shoe.burnCards();

        // Note: Everything that happens inside this loop is optimized for performance.
        // Don't do anything unnecessary. No allocating memory from the heap.
        int roundNumber = 0;
        boolean keepPlayingRounds = true;
        while (keepPlayingRounds) {
            boolean someonePlayed = playRound(roundNumber++);
            keepPlayingRounds = someonePlayed && (roundNumber < numRoundsToPlay);
        }
        return roundNumber;
    }

    boolean playRound(int roundNumber) {
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
            atLeastOneBetWasPlaced = atLeastOneBetWasPlaced || seats[seatNumber].createNewHandWithBet();
        }

        if (!atLeastOneBetWasPlaced) {
            outputter.roundAborted();
            return false;
        }

        // deal first card to each player
        for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
            seats[seatNumber].getHand(0).addCard(shoe.dealCard());
        }

        // deal first card to dealer
        handForDealer.addCard(shoe.dealCard());

        // deal second card to each player
        for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
            seats[seatNumber].getHand(0).addCard(shoe.dealCard());
        }

        // deal hole card to dealer
        handForDealer.addCard(shoe.dealCard());
        outputter.showDealerUpcard(handForDealer);

        // take insurance bets
        boolean upcardIsAce = handForDealer.isUpcardAce();
        if (upcardIsAce) {
            for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
                insuranceBets[seatNumber] = seats[seatNumber].determineInsuranceBet();
            }
        }

        if (handForDealer.isBlackjack()) {
            // pay out insurance
            if (upcardIsAce) {
                for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
                    long insuranceBet = insuranceBets[seatNumber];
                    if (insuranceBet > 0) {
                        long moneyToPayPlayer = insuranceBet * 3;
                        seats[seatNumber].getPlayer().payPlayer(moneyToPayPlayer);
                        tableBankroll -= insuranceBet << 1;
                        insuranceBets[seatNumber] = 0L;
                    }
                }
            }

            // clear out all the player hands because nobody plays when dealer gets blackjack
            for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
                Seat seat = seats[seatNumber];
                HandForPlayer hand = seat.getHand(0);
                long betAmount = hand.getBetAmount();
                boolean playerHandIsBlackjack = hand.isBlackjack();
                if (playerHandIsBlackjack) {
                    // player pushes
                    seat.getPlayer().payPlayer(betAmount);
                } else {
                    // dealer's blackjack wins the bet
                    tableBankroll += betAmount;
                }
                hand.reset();
            }
        } else {
            for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
                playSeat(seats[seatNumber]);
            }

            // player blackjacks have already been paid, and the hands reset, at this point.

            // Note: both sides of the conditional have some duplicate code.
            // This is to save a few CPU cycles, as we don't need to compute the dealer
            // hand value if the dealer busted.
            boolean dealerBusted = playDealer();
            if (dealerBusted) {
                // pay players with bets still on their hands
                for (int seatNumber = numSeatsInUse - 1; seatNumber >= 0; seatNumber--) {
                    Seat seat = seats[seatNumber];
                    int numHands = seat.getNumHandsInUse();
                    for (int handIndex = numHands - 1; handIndex >= 0; handIndex--) {
                        HandForPlayer hand = seat.getHand(handIndex);
                        if (hand.hasCards()) {
                            long betAmount = hand.getBetAmount();
                            seat.getPlayer().addToBankroll(betAmount << 1);
                            tableBankroll -= betAmount;
                            hand.reset();
                        }
                    }
                }
            } else {
                // pay players with hands that beat the dealer's hand
                // take bets from players with hands that lose against the dealer's hand
                int dealerHandValue = handForDealer.computeMaxPointSum();
                for (int seatNumber = numSeatsInUse - 1; seatNumber >= 0; seatNumber--) {
                    Seat seat = seats[seatNumber];
                    int numHands = seat.getNumHandsInUse();
                    for (int handIndex = numHands - 1; handIndex >= 0; handIndex--) {
                        HandForPlayer hand = seat.getHand(handIndex);
                        if (hand.hasCards()) {
                            int handValue = hand.computeMaxPointSum();
                            long betAmount = hand.getBetAmount();
                            if (handValue < dealerHandValue) {
                                // dealer win
                                tableBankroll += betAmount;
                            } else if (handValue > dealerHandValue) {
                                // player win
                                seat.getPlayer().addToBankroll(betAmount << 1);
                                tableBankroll -= betAmount;
                            } else {
                                // push
                                seat.getPlayer().addToBankroll(betAmount);
                            }
                            hand.reset();
                        }
                    }
                }
            }
        }

        handForDealer.reset();
        for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
            seats[seatNumber].resetHands();
        }

        return true;
    }

    private void playSeat(Seat seat) {
        long betAmount;
        long halfOfBetAmount;
        int playerHandValue;

        // first hand can be blackjack
        HandForPlayer firstHand = seat.getHand(0);
        if (firstHand.isBlackjack()) {
            betAmount = firstHand.getBetAmount();
            halfOfBetAmount = betAmount >> 1;
            seat.getPlayer().payPlayer((betAmount << 1) + halfOfBetAmount);
            tableBankroll -= (betAmount + halfOfBetAmount);
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
                keepPlaying = playOnSplitHand(hand);
            }

            while (keepPlaying) {
                PlayerDecision playerDecision = seat.getPlayerDecision(handIndexToPlay);
                switch (playerDecision) {
                    case Stand:
                        keepPlaying = false;
                        break;
                    case Hit:
                        hand.addCard(shoe.dealCard());
                        playerHandValue = hand.computeMaxPointSum();
                        keepPlaying = playerHandValue < 21;
                        if (playerHandValue > 21) {
                            betAmount = hand.getBetAmount();
                            tableBankroll += betAmount;
                            hand.reset();
                        } else if (playerHandValue == 21) {
                            hand.setIsTwentyOnePoints();
                        }
                        break;
                    case Split:
                        seat.createSplitHand(handIndexToPlay);
                        keepPlaying = playOnSplitHand(hand);
                        break;
                    case Double:
                        betAmount = hand.getBetAmount();
                        seat.getPlayer().removeFromBankroll(betAmount);
                        hand.setBetAmount(betAmount << 1);
                        hand.addCard(shoe.dealCard());
                        keepPlaying = false;
                        playerHandValue = hand.computeMaxPointSum();
                        if (playerHandValue > 21) {
                            betAmount = hand.getBetAmount();
                            tableBankroll += betAmount;
                            hand.reset();
                        } else if (playerHandValue == 21) {
                            hand.setIsTwentyOnePoints();
                        }
                        break;
                    case Surrender:
                        betAmount = hand.getBetAmount();
                        halfOfBetAmount = betAmount >> 1;
                        seat.getPlayer().addToBankroll(halfOfBetAmount);
                        this.tableBankroll += halfOfBetAmount;
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

    private boolean playOnSplitHand(HandForPlayer hand) {
        boolean keepPlaying = true;

        hand.addCard(shoe.dealCard());

        // check various situations that would make the player unable to continue with this hand
        if (hand.isBlackjack()) {
            keepPlaying = false;
        } else {
            if (hand.isPairOfAces()
                    && !rules.isCanHitSplitAces()
                    && !rules.isCanResplitAces()) {
                keepPlaying = false;
            }
        }
        return keepPlaying;
    }

    private boolean playDealer() {
        outputter.showHoleCard(handForDealer);
        while (handForDealer.shouldDealerHit()) {
            handForDealer.addCard(shoe.dealCard());
        }
        return handForDealer.isBusted();
    }

    public long getTableBankroll() {
        return tableBankroll;
    }
}
