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
            hands[i] = new HandForPlayer(shoe, this);
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
        table.getOutputter().sitPlayer(player, seatNumber);
        this.player = player;
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

        table.getOutputter().placeBet(player, seatNumber, betAmount);
        hand.setBetAmount(betAmount);
        player.removeFromBankroll(betAmount);
        return true;
    }

    public void makeInsuranceBet() {
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
}
