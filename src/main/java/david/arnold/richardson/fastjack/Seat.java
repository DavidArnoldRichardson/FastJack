package david.arnold.richardson.fastjack;

public class Seat {
    private int seatNumber;
    private Player player;
    private Table table;
    private HandForPlayer[] hands;
    private int numHandsInUse;
    private long insuranceBet;

    public Seat(
            Table table,
            int seatNumber) {
        this.seatNumber = seatNumber;
        this.table = table;
        Shoe shoe = table.getShoe();
        int maxNumHands = shoe.getRules().getMaxNumSplits() + 1;
        hands = new HandForPlayer[maxNumHands];
        for (int i = 0; i < maxNumHands; i++) {
            hands[i] = new HandForPlayer(shoe, this, i);
        }
        resetHands();
    }

    @Override
    public String toString() {
        if (player == null) {
            return "(empty)";
        }
        return player.getPlayerName();
    }

    public void assignPlayerToSeat(Player player) {
        this.player = player;
        table.getOutputter().sitPlayer(this);
    }

    public boolean isPlayingThisRound() {
        return hands[0].getBetAmount() > 0L;
    }

    public Player getPlayer() {
        return player;
    }

    public int getNumHandsInUse() {
        return numHandsInUse;
    }

    // just used for testing
    public void setNumHandsInUse(int numHandsInUse) {
        this.numHandsInUse = numHandsInUse;
    }

    public HandForPlayer getHand(int handIndex) {
        return hands[handIndex];
    }

    public boolean createNewHandWithBet() {
        long betAmount = MoneyHelper.computeAcceptableBet(
                player.getBetStrategy().getBetAmount(),
                player.getBankroll(),
                table.getShoe().getRules().getMinBetAmount(),
                table.getShoe().getRules().getMaxBetAmount());
        return createNewHandWithBet(betAmount);
    }

    public void createSplitHand(int handIndexToPlay) {
        HandForPlayer handToBeSplit = hands[handIndexToPlay];
        createNewHandWithBet(handToBeSplit.getBetAmount());
        HandForPlayer handThatWasCreated = hands[numHandsInUse - 1];
        handThatWasCreated.addCard(handToBeSplit.split());
        handToBeSplit.setHandIsResultOfSplit();
        handThatWasCreated.setHandIsResultOfSplit();
    }

    private boolean createNewHandWithBet(long betAmount) {
        HandForPlayer hand = hands[numHandsInUse++];
        hand.reset();

        if (betAmount == 0L) {
            table.getOutputter().playerDeclinesToBet(this);
            return false;
        }

        table.getOutputter().placeBet(this, hand.getHandIndex(), betAmount);
        hand.setBetAmount(betAmount);
        player.removeFromBankroll(betAmount);
        return true;
    }

    public void receiveCard() {
        if (isPlayingThisRound()) {
            hands[0].addCard(table.getShoe().dealCard());
        }
    }

    public void receiveSecondCard() {
        if (isPlayingThisRound()) {
            receiveCard();
            table.getOutputter().showDealtHand(this);
        }
    }

    public void makeInsuranceBet() {
        if (isPlayingThisRound()) {
            if (!player.getPlayStrategy().shouldGetInsurance()) {
                return;
            }

            long insuranceBet = hands[0].getBetAmount() >> 1;
            if (insuranceBet > player.getBankroll()) {
                insuranceBet = player.getBankroll();
            }
            player.removeFromBankroll(insuranceBet);
            table.getOutputter().insuranceBetMade(this);
        }
    }

    public long getInsuranceBet() {
        return insuranceBet;
    }

    public PlayerDecision getPlayerDecision(
            int handIndex,
            int dealerUpcardValue) {
        return player.getPlayStrategy().getPlay(
                hands[handIndex],
                dealerUpcardValue);
    }

    public void resetHands() {
        numHandsInUse = 0;
        this.insuranceBet = 0L;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void payInsuranceToPlayer() {
        if (insuranceBet > 0) {
            // original insurance bet returned, plus double it.
            player.payPlayer(insuranceBet * 3);
            table.addToBankroll(-(insuranceBet << 1));
            table.getOutputter().payInsurance(this);
        }
    }

    public void handleDealerGotBlackjack() {
        if (isPlayingThisRound()) {
            HandForPlayer hand = hands[0];
            long betAmount = hand.getBetAmount();
            if (betAmount > 0L) {
                boolean playerHandIsBlackjack = hand.isSoftTwentyOne();
                if (playerHandIsBlackjack) {
                    // player pushes
                    player.payPlayer(betAmount);
                    table.getOutputter().pushOnDealerBlackjack(this);
                } else {
                    // dealer's blackjack wins the bet
                    table.addToBankroll(betAmount);
                    table.getOutputter().loseOnDealerBlackjack(this);
                }
                hand.reset();
            }
        }
    }

    public void handleDealerBust() {
        // pay bets from right to left
        for (int handIndex = numHandsInUse - 1; handIndex >= 0; handIndex--) {
            HandForPlayer hand = hands[handIndex];
            if (hand.hasCards()) {
                table.getOutputter().playerWins(this, hand);
                long betAmount = hand.getBetAmount();
                player.addToBankroll(betAmount << 1);
                table.addToBankroll(-betAmount);
                hand.reset();
            }
        }
    }

    public void handleDealerStand(int dealerHandValue) {
        for (int handIndex = numHandsInUse - 1; handIndex >= 0; handIndex--) {
            HandForPlayer hand = hands[handIndex];
            if (hand.hasCards()) {
                int playerHandValue = hand.computeMaxPointSum();
                long betAmount = hand.getBetAmount();
                if (playerHandValue < dealerHandValue) {
                    table.getOutputter().playerLoses(this, hand);
                    table.addToBankroll(betAmount);
                } else if (playerHandValue > dealerHandValue) {
                    table.getOutputter().playerWins(this, hand);
                    player.addToBankroll(betAmount << 1);
                    table.addToBankroll(-betAmount);
                } else {
                    table.getOutputter().playerPushes(this, hand);
                    player.addToBankroll(betAmount);
                }
                hand.reset();
            }
        }
    }

    public boolean checkIfSeatNeedsDealerToPlay() {
        for (int i = 0; i < numHandsInUse; i++) {
            if (hands[i].checkIfHandNeedsDealerToPlay()) {
                return true;
            }
        }
        return false;
    }
}
