package david.arnold.richardson.fastjack;

public class OutputterSilentAndFast extends Outputter {
    public boolean isDisplaying() {
        return false;
    }

    @Override
    public void showMessage(String message) {
    }

    @Override
    public void startRound(int roundNumber) {
    }

    @Override
    public void cutCardWasDrawn() {
    }

    @Override
    public void shuffle(int numDecks) {
    }

    @Override
    public void placeCutCard(Shoe shoe) {
    }

    @Override
    public void showBurnCards(Shoe shoe) {
    }

    @Override
    public void showDealerUpcard(HandForDealer handForDealer) {
    }

    @Override
    public void roundAborted() {
    }

    @Override
    public void placeBet(Seat seat, int handIndex, long desiredBetAmount) {
    }

    @Override
    public void sitPlayer(Seat seat) {
    }

    @Override
    public void showDealtHand(Seat seat) {
    }

    @Override
    public void dealerUpcardIsAce() {
    }

    @Override
    public void insuranceBetMade(Seat seat) {
    }

    @Override
    public void revealDealerHand(HandForDealer handForDealer) {
    }

    @Override
    public long playerWinsInsuranceBet(Seat seat) {
        return seat.hasInsuranceBet() ? seat.getInsuranceBetAmount() << 1 : 0L;
    }

    @Override
    public void pushOnDealerBlackjack(Seat seat) {
    }

    @Override
    public long loseOnDealerBlackjack(Seat seat) {
        return -seat.getHand(0).getMoneyPile().getAmount();
    }

    @Override
    public long playerBlackjackAndWins(Seat seat, HandForPlayer hand) {
        long betAmount = hand.getMoneyPile().getAmount();
        return betAmount + (betAmount >> 1);
    }

    @Override
    public void playerStand(Seat seat, HandForPlayer hand) {
    }

    @Override
    public long playerHitAndBust(Seat seat, HandForPlayer hand) {
        return -hand.getMoneyPile().getAmount();
    }

    @Override
    public void playerHitAndGot21(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void playerHit(Seat seat, HandForPlayer hand) {
    }

    @Override
    public long playerDoubledAndBust(Seat seat, HandForPlayer hand) {
        return -(hand.getMoneyPile().getAmount() << 1);
    }

    @Override
    public void playerDoubledAndGot21(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void playerDoubled(Seat seat, HandForPlayer hand) {
    }

    @Override
    public long playerSurrendered(Seat seat, HandForPlayer hand) {
        return -(hand.getMoneyPile().getAmount() >> 1);
    }

    @Override
    public void playerSplits(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void gotSecondCardOnSplit(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void gotSecondCardOnSplitAndGot21(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void gotSecondCardOnSplitAndCannotContinue(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void showDealerHandResult(HandForDealer handForDealer, boolean dealerBusted) {
    }

    @Override
    public long playerWins(Seat seat, HandForPlayer hand) {
        return hand.getMoneyPile().getAmount();
    }

    @Override
    public long playerLoses(Seat seat, HandForPlayer hand) {
        return -hand.getMoneyPile().getAmount();
    }

    @Override
    public void playerPushes(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void showRules(Rules rules) {
    }

    @Override
    public void playerDeclinesToBet(Seat seat) {
    }
}
