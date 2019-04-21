package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.strategy.play.PlaySummary;

public class Seat {
    private int seatNumber;
    private Player player;
    private Table table;
    private HandForPlayer[] hands;
    private HandForPlayer firstHand;
    private int numHandsInUse;
    private MoneyPile insuranceBet;

    // This may break if the same player has multiple seats.
    private long previousPlayerBankroll;
    private long deltaAfterRoundPlayed;

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
        this.firstHand = hands[0];
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
        this.previousPlayerBankroll = player.getMoneyPile().getAmount();
        table.getOutputter().sitPlayer(this);
    }

    public long computeAndUpdateBankrollTrackingAfterRound() {
        long newAmount = player.getMoneyPile().getAmount();
        long valueToReturn = previousPlayerBankroll;
        previousPlayerBankroll = newAmount;
        return newAmount - valueToReturn;
    }

    public long getDeltaAfterRoundPlayed() {
        return deltaAfterRoundPlayed;
    }

    public boolean isPlayingThisRound() {
        return firstHand.getMoneyPile().hasSomeMoney();
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

    public boolean createFirstHandWithBet() {
        deltaAfterRoundPlayed = 0L;
        long betAmount = MoneyPile.computeAcceptableBet(
                player.getBetStrategy().getBetAmount(),
                player.getMoneyPile().getAmount(),
                table.getShoe().getRules().getMinBetAmount(),
                table.getShoe().getRules().getMaxBetAmount());
        return createHandWithBet(betAmount);
    }

    public void createSplitHand(int handIndexToPlay) {
        HandForPlayer handToBeSplit = hands[handIndexToPlay];
        createHandWithBet(handToBeSplit.getMoneyPile().getAmount());
        HandForPlayer handThatWasCreated = hands[numHandsInUse - 1];
        handThatWasCreated.addCard(handToBeSplit.split());
        handToBeSplit.setHandIsResultOfSplit();
        handThatWasCreated.setHandIsResultOfSplit();
    }

    private boolean createHandWithBet(long betAmount) {
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
            firstHand.addCard(table.getShoe().dealCard());
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
                if (firstHand.getMoneyPile().hasSomeMoney()) {
                    long insuranceBetAmount = firstHand.getMoneyPile().getAmount() >> 1;
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
            deltaAfterRoundPlayed += table.getOutputter().playerWinsInsuranceBet(this);
            table.getMoneyPile().pay(player.getMoneyPile(), insuranceBetAmount << 1);
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
            int dealerUpcardValue,
            PlaySummary playSummary) {
        PlayerDecision playerDecision = player.getPlayStrategy().getPlay(
                hands[handIndex],
                dealerUpcardValue,
                playSummary);
        table.getOutputter().showMessage(playSummary.show());
        return playerDecision;
    }

    public void resetHands() {
        numHandsInUse = 0;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void handleDealerGotBlackjack() {
        if (isPlayingThisRound()) {
            if (firstHand.hasBet()) {
                boolean playerHandIsBlackjack = firstHand.isSoftTwentyOne();
                if (playerHandIsBlackjack) {
                    // player pushes
                    table.getOutputter().pushOnDealerBlackjack(this);
                    firstHand.getMoneyPile().payAll(player.getMoneyPile());
                } else {
                    // dealer's blackjack wins the bet
                    deltaAfterRoundPlayed += table.getOutputter().loseOnDealerBlackjack(this);
                    firstHand.getMoneyPile().payAll(table.getMoneyPile());
                }
                firstHand.reset();
            }
        }
    }

    public void handlePlayerGotBlackjackAndWon() {
        deltaAfterRoundPlayed += table.getOutputter().playerBlackjackAndWins(this, firstHand);
        long betAmount = firstHand.getMoneyPile().getAmount();
        firstHand.getMoneyPile().payAll(player.getMoneyPile());
        table.getMoneyPile().pay(player.getMoneyPile(), betAmount + (betAmount >> 1));
        firstHand.reset();
    }

    public void handlePlayerHitAndBust(HandForPlayer hand) {
        deltaAfterRoundPlayed += table.getOutputter().playerHitAndBust(this, hand);
        hand.getMoneyPile().payAll(table.getMoneyPile());
        hand.reset();
    }

    public void handlePlayerDoubleAndBust(HandForPlayer hand) {
        deltaAfterRoundPlayed += table.getOutputter().playerDoubledAndBust(this, hand);
        hand.getMoneyPile().payAll(table.getMoneyPile());
        hand.reset();
    }

    public void handlePlayerSurrender(HandForPlayer hand) {
        deltaAfterRoundPlayed += table.getOutputter().playerSurrendered(this, hand);
        long halfOfBetAmount = hand.getMoneyPile().getAmount() >> 1;
        hand.getMoneyPile().pay(player.getMoneyPile(), halfOfBetAmount);
        hand.getMoneyPile().payAll(table.getMoneyPile());
        hand.reset();
    }

    public void handleDealerBust() {
        // pay bets from right to left
        for (int handIndex = numHandsInUse - 1; handIndex >= 0; handIndex--) {
            HandForPlayer hand = hands[handIndex];
            if (hand.hasCards()) {
                deltaAfterRoundPlayed += table.getOutputter().playerWins(this, hand);
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
                    deltaAfterRoundPlayed += table.getOutputter().playerLoses(this, hand);
                    hand.getMoneyPile().payAll(table.getMoneyPile());
                } else if (playerHandValue > dealerHandValue) {
                    deltaAfterRoundPlayed += table.getOutputter().playerWins(this, hand);
                    hand.getMoneyPile().payAll(player.getMoneyPile());
                    table.getMoneyPile().pay(player.getMoneyPile(), betAmount);
                } else {
                    table.getOutputter().playerPushes(this, hand);
                    hand.getMoneyPile().payAll(player.getMoneyPile());
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

    public long getInsuranceBetAmount() {
        return insuranceBet.getAmount();
    }
}
