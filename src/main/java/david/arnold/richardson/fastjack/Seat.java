package david.arnold.richardson.fastjack;

public class Seat {
    private int seatNumber;
    private Player player;
    private Table table;
    private HandForPlayer[] hands;
    private int numHandsInUse;
    private MoneyPile insuranceBet;

    public Seat(
            Table table,
            int seatNumber) {
        this.seatNumber = seatNumber;
        this.table = table;
        this.insuranceBet = new MoneyPile();
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
        return hands[0].getMoneyPile().hasSomeMoney();
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
        long betAmount = MoneyPile.computeAcceptableBet(
                player.getBetStrategy().getBetAmount(),
                player.getAvailableFunds(),
                table.getShoe().getRules().getMinBetAmount(),
                table.getShoe().getRules().getMaxBetAmount());
        return createNewHandWithBet(betAmount);
    }

    public void createSplitHand(int handIndexToPlay) {
        HandForPlayer handToBeSplit = hands[handIndexToPlay];
        createNewHandWithBet(handToBeSplit.getMoneyPile().getAmount());
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
        hand.playerCreatesBet(player, betAmount);
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
            if (player.getPlayStrategy().shouldGetInsurance()) {
                if (hands[0].getMoneyPile().hasSomeMoney()) {
                    long insuranceBetAmount = hands[0].getMoneyPile().getAmount() >> 1;
                    if (player.canAfford(insuranceBetAmount)) {
                        player.getMoneyPile().pay(insuranceBet, insuranceBetAmount);
                        table.getOutputter().insuranceBetMade(this);
                    }
                }
            }
        }
    }

    public void loseInsuranceBet() {
        if (insuranceBet.hasSomeMoney()) {
            insuranceBet.payAll(table.getMoneyPile());
        }
    }

    public void payInsuranceToPlayer() {
        if (insuranceBet.hasSomeMoney()) {
            // original insurance bet returned, plus double it.
            long insuranceBetAmount = insuranceBet.getAmount();
            insuranceBet.payAll(player.getMoneyPile());
            table.getMoneyPile().pay(player.getMoneyPile(), insuranceBetAmount << 1);
            table.getOutputter().payInsurance(this);
        }
    }

    public boolean hasInsuranceBet() {
        return insuranceBet.hasSomeMoney();
    }

    public String getInsuranceBetForDisplay() {
        return insuranceBet.formatForDisplay();
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
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void handleDealerGotBlackjack() {
        if (isPlayingThisRound()) {
            HandForPlayer hand = hands[0];
            if (hand.hasBet()) {
                boolean playerHandIsBlackjack = hand.isSoftTwentyOne();
                if (playerHandIsBlackjack) {
                    // player pushes
                    hand.getMoneyPile().payAll(player.getMoneyPile());
                    table.getOutputter().pushOnDealerBlackjack(this);
                } else {
                    // dealer's blackjack wins the bet
                    hand.getMoneyPile().payAll(table.getMoneyPile());
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
                long betAmount = hand.getMoneyPile().getAmount();
                hand.getMoneyPile().payAll(player.getMoneyPile());
                table.getMoneyPile().pay(player.getMoneyPile(), betAmount);
                hand.reset();
            }
        }
    }

    public void handleDealerStand(int dealerHandValue) {
        for (int handIndex = numHandsInUse - 1; handIndex >= 0; handIndex--) {
            HandForPlayer hand = hands[handIndex];
            if (hand.hasCards()) {
                int playerHandValue = hand.computeMaxPointSum();
                long betAmount = hand.getMoneyPile().getAmount();
                if (playerHandValue < dealerHandValue) {
                    table.getOutputter().playerLoses(this, hand);
                    hand.getMoneyPile().payAll(table.getMoneyPile());
                } else if (playerHandValue > dealerHandValue) {
                    table.getOutputter().playerWins(this, hand);
                    hand.getMoneyPile().payAll(player.getMoneyPile());
                    table.getMoneyPile().pay(player.getMoneyPile(), betAmount);
                } else {
                    table.getOutputter().playerPushes(this, hand);
                    hand.getMoneyPile().payAll(table.getMoneyPile());
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
