package david.arnold.richardson.fastjack;

import java.util.HashSet;
import java.util.Set;

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
        this.rules = rules;
        this.shoe = new Shoe(this, outputter);

        seats = new Seat[NUM_SEATS];
        insuranceBets = new long[NUM_SEATS];
        for (int i = 0; i < NUM_SEATS; i++) {
            seats[i] = new Seat(this, i);
            insuranceBets[i] = 0L;
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
            boolean betPlaced = seats[seatNumber].createNewHandWithBet();
            atLeastOneBetWasPlaced = atLeastOneBetWasPlaced || betPlaced;
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
            Seat seat = seats[seatNumber];
            HandForPlayer hand = seat.getHand(0);
            hand.addCard(shoe.dealCard());
            outputter.showDealtHand(seat.getPlayer(), seatNumber, hand);
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
                long insuranceBet = seats[seatNumber].determineInsuranceBet();
                insuranceBets[seatNumber] = insuranceBet;
                outputter.insuranceBetMade(seats[seatNumber], insuranceBet);
            }
        }

        if (handForDealer.isBlackjack()) {
            // pay out insurance
            if (upcardIsAce) {
                outputter.revealDealerHand(handForDealer);

                for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
                    long insuranceBet = insuranceBets[seatNumber];
                    if (insuranceBet > 0) {
                        long moneyToPayPlayer = insuranceBet * 3;
                        Player player = seats[seatNumber].getPlayer();
                        player.payPlayer(moneyToPayPlayer);
                        tableBankroll -= insuranceBet << 1;
                        insuranceBets[seatNumber] = 0L;
                        outputter.payInsurance(player, seatNumber, insuranceBet);
                    }
                }
            }

            outputter.dealerBlackjack(handForDealer);

            // clear out all the player hands because nobody plays when dealer gets blackjack
            for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
                Seat seat = seats[seatNumber];
                HandForPlayer hand = seat.getHand(0);
                long betAmount = hand.getBetAmount();
                boolean playerHandIsBlackjack = hand.isBlackjack();
                Player player = seat.getPlayer();
                if (playerHandIsBlackjack) {
                    // player pushes
                    player.payPlayer(betAmount);
                    outputter.pushOnDealerBlackjack(player, seatNumber, betAmount);
                } else {
                    // dealer's blackjack wins the bet
                    tableBankroll += betAmount;
                    outputter.loseOnDealerBlackjack(player, seatNumber, betAmount);
                }
                hand.reset();
            }
        } else {

            // collect lost insurance bets
            if (upcardIsAce) {
                for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
                    long insuranceBet = insuranceBets[seatNumber];
                    if (insuranceBet > 0) {
                        tableBankroll += insuranceBet;
                        insuranceBets[seatNumber] = 0L;
                    }
                }
            }

            // Players now play their hands
            for (int seatNumber = 0; seatNumber < numSeatsInUse; seatNumber++) {
                playSeat(seats[seatNumber], dealerUpcardValue);
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
                            outputter.playerWins(seat, hand);
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
                            int playerHandValue = hand.computeMaxPointSum();
                            long betAmount = hand.getBetAmount();
                            if (playerHandValue < dealerHandValue) {
                                outputter.playerLoses(seat, hand);
                                tableBankroll += betAmount;
                            } else if (playerHandValue > dealerHandValue) {
                                outputter.playerWins(seat, hand);
                                seat.getPlayer().addToBankroll(betAmount << 1);
                                tableBankroll -= betAmount;
                            } else {
                                outputter.playerPushes(seat, hand);
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

    private void playSeat(
            Seat seat,
            int dealerUpcardValue) {
        long betAmount;
        long halfOfBetAmount;
        int playerHandValue;

        // first hand can be blackjack
        HandForPlayer firstHand = seat.getHand(0);
        if (firstHand.isBlackjack()) {
            outputter.playerBlackjack(seat, firstHand);
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
                keepPlaying = playOnNewlySplitHand(seat, hand);
            }

            while (keepPlaying) {
                PlayerDecision playerDecision = seat.getPlayerDecision(handIndexToPlay, dealerUpcardValue);
                switch (playerDecision) {
                    case STD:
                        outputter.playerStand(seat, hand);
                        keepPlaying = false;
                        break;
                    case HIT:
                        hand.addCard(shoe.dealCard());
                        playerHandValue = hand.computeMaxPointSum();
                        keepPlaying = playerHandValue < 21;
                        if (playerHandValue > 21) {
                            // player bust
                            outputter.playerHitAndBust(seat, hand);
                            betAmount = hand.getBetAmount();
                            tableBankroll += betAmount;
                            hand.reset();
                        } else if (playerHandValue == 21) {
                            // player 21
                            outputter.playerHitAndGot21(seat, hand);
                            hand.setIsTwentyOnePoints();
                        } else {
                            // player hit and can hit more if they want
                            outputter.playerHit(seat, hand);
                        }
                        break;
                    case SPL:
                        outputter.playerSplits(seat, hand);
                        seat.createSplitHand(handIndexToPlay);
                        keepPlaying = playOnNewlySplitHand(seat, hand);
                        break;
                    case DBL:
                        betAmount = hand.getBetAmount();
                        seat.getPlayer().removeFromBankroll(betAmount);
                        betAmount = betAmount << 1;
                        hand.setBetAmount(betAmount);
                        hand.addCard(shoe.dealCard());
                        keepPlaying = false;
                        playerHandValue = hand.computeMaxPointSum();
                        if (playerHandValue > 21) {
                            // player bust
                            outputter.playerDoubledAndBust(seat, hand);
                            tableBankroll += betAmount;
                            hand.reset();
                        } else if (playerHandValue == 21) {
                            // player 21
                            outputter.playerDoubledAndGot21(seat, hand);
                            hand.setIsTwentyOnePoints();
                        } else {
                            // player got their card and is done
                            outputter.playerDoubled(seat, hand);
                        }
                        break;
                    case SUR:
                        outputter.playerSurrendered(seat, hand);
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

    private boolean playOnNewlySplitHand(
            Seat seat,
            HandForPlayer hand) {
        boolean keepPlaying = true;

        hand.addCard(shoe.dealCard());

        // check various situations that would make the player unable to continue with this hand
        if (hand.isBlackjack()) {
            keepPlaying = false;
            outputter.gotSecondCardOnSplitAndGot21(seat, hand);
        } else {
            if (hand.isPairOfAces()
                    && !rules.isCanHitSplitAces()
                    && !rules.isCanResplitAces()) {
                keepPlaying = false;
                outputter.gotSecondCardOnSplitAndCannotContinue(seat, hand);
            } else {
                outputter.gotSecondCardOnSplit(seat, hand);
            }
        }
        return keepPlaying;
    }

    private boolean playDealer() {
        while (handForDealer.shouldDealerHit()) {
            handForDealer.addCard(shoe.dealCard());
        }
        boolean dealerBusted = handForDealer.isBusted();
        outputter.dealerHandResult(handForDealer, dealerBusted);
        return dealerBusted;
    }

    public long getTableBankrollDelta() {
        return tableBankroll - tableBankrollStart;
    }

    public Rules getRules() {
        return rules;
    }

    public void setRules(Rules rules) {
        this.rules = rules;
    }

    public Outputter getOutputter() {
        return outputter;
    }
}
