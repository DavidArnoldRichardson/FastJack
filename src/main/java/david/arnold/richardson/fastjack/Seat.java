package david.arnold.richardson.fastjack;

public class Seat {
    private Player player;
    private HandForPlayer[] hands;
    private int numHandsInUse;

    public Seat(Table table) {
        Shoe shoe = table.getShoe();
        int maxNumHands = shoe.getRules().getMaxNumSplits() + 1;
        hands = new HandForPlayer[maxNumHands];
        for (int i = 0; i < maxNumHands; i++) {
            hands[i] = new HandForPlayer(shoe);
        }
        resetHands();
    }

    public void assignPlayerToSeat(Player player) {
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
        return createNewHandWithBet(player.getBetStrategy().getBetAmount());
    }

    private boolean createNewHandWithBet(long betAmount) {
        HandForPlayer hand = hands[numHandsInUse++];
        hand.reset();
        hand.setBetAmount(betAmount);
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

    public PlayerDecision getPlayerDecision(int handIndex) {
        return player.getPlayStrategy().getPlay(hands[handIndex]);
    }

    public void resetHands() {
        numHandsInUse = 0;
    }
}
