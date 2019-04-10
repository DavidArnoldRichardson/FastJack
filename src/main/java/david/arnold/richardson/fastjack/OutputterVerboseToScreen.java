package david.arnold.richardson.fastjack;

public class OutputterVerboseToScreen extends Outputter {
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void startRound(int roundNumber) {
        showMessage("Starting round " + roundNumber + ".");
    }

    @Override
    public void cutCardWasDrawn() {
        showMessage("Cut card was drawn.");
    }

    @Override
    public void showHoleCard(HandForDealer handForDealer) {
        showMessage("Dealer shows her hole card: " + handForDealer.showHoleCard());
    }

    @Override
    public void freshCards(int numDecks) {
        showMessage("Dealer opens " + numDecks + " fresh decks of cards.");
    }

    @Override
    public void shuffle() {
        showMessage("Dealer shuffles.");
    }

    @Override
    public void placeCutCard(Shoe shoe) {
        showMessage("Dealer places the cut card with " + shoe.getNumCardsAfterCutCard() + " after it.");
    }

    @Override
    public void showBurnCards(Shoe shoe) {
        if (shoe.getRules().isShowBurnCards()) {
            int numBurnCards = shoe.getRules().getNumBurnCards();
            for (int i = 0; i < numBurnCards; i++) {
                showMessage("Dealer burns " + shoe.getCardForDisplay(i) + ".");
            }
        }
    }

    @Override
    public void showDealerUpcard(HandForDealer handForDealer) {
        showMessage("Dealer upcard is " + handForDealer.showUpcard() + ".");
    }

    @Override
    public void showHand(Player player, HandForPlayer hand) {
        showMessage("Player " + player.getPlayerName() + " has " + hand.show() + ".");
    }

    @Override
    public void roundAborted() {
        showMessage("Round aborted.");
    }
}
