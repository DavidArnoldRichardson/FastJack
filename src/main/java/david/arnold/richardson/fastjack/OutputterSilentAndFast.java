package david.arnold.richardson.fastjack;

public class OutputterSilentAndFast extends Outputter {

    @Override
    protected void showMessage(String message) {
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
    public void placeBet(Player player, int seatNumber, long desiredBetAmount) {
    }

    @Override
    public void sitPlayer(Player player, int seatNumber) {
    }

    @Override
    public void showDealtHand(Player player, int seatNumber, HandForPlayer hand) {
    }

    @Override
    public void dealerUpcardIsAce() {
    }

    @Override
    public void insuranceBetMade(Seat seat, long insuranceBet) {
    }

    @Override
    public void revealDealerHand(HandForDealer handForDealer) {
    }

    @Override
    public void payInsurance(Player player, int seatNumber, long insuranceBet) {
    }

    @Override
    public void dealerBlackjack(HandForDealer handForDealer) {
    }

    @Override
    public void pushOnDealerBlackjack(Player player, int seatNumber, long betAmount) {
    }

    @Override
    public void loseOnDealerBlackjack(Player player, int seatNumber, long betAmount) {
    }

    @Override
    public void playerBlackjack(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void playerStand(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void playerHitAndBust(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void playerHitAndGot21(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void playerHit(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void playerDoubledAndBust(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void playerDoubledAndGot21(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void playerDoubled(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void playerSurrendered(Seat seat, HandForPlayer hand) {
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
    public void dealerHandResult(HandForDealer handForDealer, boolean dealerBusted) {
    }

    @Override
    public void playerWins(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void dealerWins(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void playerPushes(Seat seat, HandForPlayer hand) {
    }

    @Override
    public void showRules(Rules rules) {
    }
}
