package david.arnold.richardson.fastjack;

public class Seat {
    private int seatNumber;
    private Player player;
    private Table table;
    private HandForPlayer[] hands;
    private int numHandsInUse;

    public Seat(
            Table table,
            int seatNumber) {
        this.seatNumber = seatNumber;
        this.table = table;
        Shoe shoe = table.getShoe();
        int maxNumHands = shoe.getRules().getMaxNumSplits() + 1;
        hands = new HandForPlayer[maxNumHands];
        for (int i = 0; i < maxNumHands; i++) {
            hands[i] = new HandForPlayer(shoe);
        }
        resetHands();
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

    public HandForPlayer getHand(int handIndex) {
        return hands[handIndex];
    }

    public boolean createNewHandWithBet() {
        long desiredBetAmount = MoneyHelper.computeAcceptableBet(
                player.getBetStrategy().getBetAmount(),
                player.getBankroll(),
                table.getShoe().getRules().getMinBetAmount(),
                table.getShoe().getRules().getMaxBetAmount());
        return createNewHandWithBet(desiredBetAmount);
    }

    private boolean createNewHandWithBet(long betAmount) {
        table.getOutputter().placeBet(player, seatNumber, betAmount);
        HandForPlayer hand = hands[numHandsInUse++];
        hand.reset();
        hand.setBetAmount(betAmount);
        player.removeFromBankroll(betAmount);
        return betAmount > 0L;
    }

    public void createSplitHand(int handIndexToPlay) {
        HandForPlayer handToBeSplit = hands[handIndexToPlay];
        createNewHandWithBet(handToBeSplit.getBetAmount());
        HandForPlayer handThatWasCreated = hands[numHandsInUse - 1];
        handThatWasCreated.addCard(handToBeSplit.split());
        handToBeSplit.setHandIsResultOfSplit();
        handThatWasCreated.setHandIsResultOfSplit();
    }

    public long determineInsuranceBet() {
        if (!player.getPlayStrategy().shouldGetInsurance()) {
            return 0L;
        }

        long insuranceBet = hands[0].getBetAmount() >> 1;
        if (insuranceBet > player.getBankroll()) {
            insuranceBet = player.getBankroll();
        }
        player.removeFromBankroll(insuranceBet);
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
    }

    public int getSeatNumber() {
        return seatNumber;
    }
}
