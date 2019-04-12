package david.arnold.richardson.fastjack;

public class OutputterVerboseToScreen extends Outputter {

    private String getPrefix(
            Player player,
            int seatNumber) {
        return "Seat " + (seatNumber + 1) + " (" + player.getPlayerName() + ")";
    }

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
        showMessage(getPrefix(player, seatNumber)
                + " bets " + MoneyHelper.formatForDisplay(desiredBetAmount) + ".");
    }

    @Override
    public void sitPlayer(Player player, int seatNumber) {
        showMessage(getPrefix(player, seatNumber)
                + " contains " + player.getPlayerName() + ".");
    }

    @Override
    public void showDealtHand(
            Player player,
            int seatNumber,
            HandForPlayer hand) {
        showMessage(getPrefix(player, seatNumber)
                + " gets " + hand.show() + ".");
    }

    @Override
    public void dealerUpcardIsAce() {
        showMessage("Dealer upcard is an ace. Insurance is made available.");
    }

    @Override
    public void insuranceBetMade(
            Seat seat,
            long insuranceBet) {
        if (insuranceBet > 0L) {
            showMessage(getPrefix(seat.getPlayer(), seat.getSeatNumber())
                    + " makes an insurance bet of "
                    + MoneyHelper.formatForDisplay(insuranceBet) + ".");
        }
    }

    @Override
    public void revealDealerHand(HandForDealer handForDealer) {
        showMessage("Dealer turns over the hole card, and it's " + handForDealer.showHoleCard() + ".");
        showMessage("Dealer's hand is " + handForDealer.show() + ".");
    }

    @Override
    public void payInsurance(Player player, int seatNumber, long insuranceBet) {
        showMessage(getPrefix(player, seatNumber)
                + " won the insurance bet of " + MoneyHelper.formatForDisplay(insuranceBet)
                + " and got an extra " + MoneyHelper.formatForDisplay(insuranceBet << 1) + ".");
    }

    @Override
    public void dealerBlackjack() {
        showMessage("All non-blackjack player hands lose on dealer blackjack.");
    }

    @Override
    public void pushOnDealerBlackjack(Player player, int seatNumber, long betAmount) {
        showMessage(getPrefix(player, seatNumber)
                + " also has blackjack, and gets back the bet of "
                + MoneyHelper.formatForDisplay(betAmount) + ".");
    }

    @Override
    public void loseOnDealerBlackjack(Player player, int seatNumber, long betAmount) {
        showMessage(getPrefix(player, seatNumber)
                + " loses " + MoneyHelper.formatForDisplay(betAmount)
                + " to the dealer blackjack.");
    }

    @Override
    public void playerBlackjack(Seat seat, HandForPlayer hand) {
        long betAmount = hand.getBetAmount();
        showMessage(getPrefix(seat.getPlayer(), seat.getSeatNumber())
                + " got blackjack! Bet of " + MoneyHelper.formatForDisplay(betAmount)
                + " wins " + MoneyHelper.formatForDisplay(betAmount + (betAmount >> 1)) + ".");
    }

    @Override
    public void playerStand(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat.getPlayer(), seat.getSeatNumber())
                + " stands with " + hand.show() + ".");
    }

    @Override
    public void playerHitAndBust(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat.getPlayer(), seat.getSeatNumber())
                + " hits and busts with " + hand.show() + ".");
    }

    @Override
    public void playerHitAndGot21(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat.getPlayer(), seat.getSeatNumber())
                + " hits and gets 21 with " + hand.show() + ".");
    }

    @Override
    public void playerHit(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat.getPlayer(), seat.getSeatNumber())
                + " hits and now has " + hand.show() + ".");
    }

    @Override
    public void playerDoubledAndBust(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat.getPlayer(), seat.getSeatNumber())
                + " doubles and busts with " + hand.show() + ".");
    }

    @Override
    public void playerDoubledAndGot21(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat.getPlayer(), seat.getSeatNumber())
                + " doubles and gets 21 with " + hand.show() + ".");
    }

    @Override
    public void playerDoubled(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat.getPlayer(), seat.getSeatNumber())
                + " doubles and gets " + hand.show() + ".");
    }

    @Override
    public void playerSurrendered(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat.getPlayer(), seat.getSeatNumber())
                + " surrenders with " + hand.show() + ".");
    }

    @Override
    public void playerSplits(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat.getPlayer(), seat.getSeatNumber())
                + " splits with " + hand.show() + ".");
    }

    @Override
    public void gotSecondCardOnSplit(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat.getPlayer(), seat.getSeatNumber())
                + " gets the second card on the split hand, and now has " + hand.show() + ".");
    }

    @Override
    public void gotSecondCardOnSplitAndGot21(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat.getPlayer(), seat.getSeatNumber())
                + " gets the second card on the split hand, and reached 21 points with " + hand.show() + ".");
    }

    @Override
    public void gotSecondCardOnSplitAndCannotContinue(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat.getPlayer(), seat.getSeatNumber())
                + " gets the second card on the split hand, and now ends play on the hand with " + hand.show() + ".");
    }

    @Override
    public void dealerHandResult(HandForDealer handForDealer, boolean dealerBusted) {
        if (dealerBusted) {
            showMessage("Dealer busts with " + handForDealer.show() + ".");
        } else {
            showMessage("Dealer ends up with " + handForDealer.show() + ".");
        }
    }
}