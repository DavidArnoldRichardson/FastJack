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
    public void showHoleCard(HandForDealer handForDealer) {
    }

    @Override
    public void freshCards(int numDecks) {
    }

    @Override
    public void shuffle() {
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
    public void showHand(Player player, HandForPlayer hand) {
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
}
