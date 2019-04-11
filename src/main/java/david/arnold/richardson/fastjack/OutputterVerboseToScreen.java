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
    public void freshCards(int numDecks) {
        showMessage("Dealer opens " + numDecks + " fresh decks of cards.");
    }

    @Override
    public void shuffle() {
        showMessage("Dealer shuffles.");
    }

    @Override
    public void placeCutCard(Shoe shoe) {
        showMessage("Dealer places the cut card with " + shoe.getNumCardsAfterCutCard() + " cards after it.");
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
    public void roundAborted() {
        showMessage("Round aborted.");
    }

    @Override
    public void placeBet(
            Player player,
            int seatNumber,
            long desiredBetAmount) {
        showMessage("Seat " + (seatNumber + 1)
                + " (" + player.getPlayerName()
                + ") bets " + MoneyHelper.formatForDisplay(desiredBetAmount) + ".");
    }

    @Override
    public void sitPlayer(Player player, int seatNumber) {
        showMessage("Seat " + (seatNumber + 1) + " contains " + player.getPlayerName() + ".");
    }

    @Override
    public void showDealtHand(
            Player player,
            int seatNumber,
            HandForPlayer hand) {
        showMessage("Seat " + (seatNumber + 1) + " (" + player.getPlayerName() + ") gets " + hand.show() + ".");
    }

    @Override
    public void dealerUpcardIsAce() {
        showMessage("Dealer upcard is an ace. Insurance is made available.");
    }

    @Override
    public void insuranceBetMade(Seat seat, int seatNumber, long insuranceBet) {
        if (insuranceBet > 0L) {
            showMessage("Seat " + (seatNumber + 1)
                    + " (" + seat.getPlayer().getPlayerName() + ") makes an insurance bet of "
                    + MoneyHelper.formatForDisplay(insuranceBet) + ".");
        }
    }

    @Override
    public void revealDealerHand(HandForDealer handForDealer) {
        showMessage("Dealer turns over the hole card, and it's " + handForDealer.showHoleCard() + ".");
        showMessage("Dealer's hand is " + handForDealer.show() + ".");
    }
}
