package david.arnold.richardson.fastjack;

public class OutputterVerboseToScreen extends Outputter {
    public boolean isDisplaying() {
        return true;
    }

    private String getPrefix(
            Seat seat) {
        return getPrefix(seat, 0);
    }

    private String getPrefix(
            Seat seat,
            int handIndex) {
        return "Seat " + (seat.getSeatNumber() + 1)
                + " hand " + (handIndex + 1)
                + " (" + seat.getPlayer().getPlayerName() + ")";
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void startRound(int roundNumber) {
        showMessage("\nStarting round " + (roundNumber + 1) + ".");
    }

    @Override
    public void cutCardWasDrawn() {
        showMessage("Cut card was drawn.");
    }

    @Override
    public void shuffle(int numDecks) {
        if (numDecks == 1) {
            showMessage("Dealer shuffles the deck.");
        } else {
            showMessage("Dealer shuffles " + numDecks + " decks.");
        }
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
            Seat seat,
            int handIndex,
            long desiredBetAmount) {
        showMessage(getPrefix(seat, handIndex) + " bets " + MoneyPile.show(desiredBetAmount) + ".");
    }

    @Override
    public void sitPlayer(Seat seat) {
        showMessage(getPrefix(seat) + " has a bankroll of "
                + seat.getPlayer().getMoneyPile().formatForDisplay() + ".");
    }

    @Override
    public void showDealtHand(Seat seat) {
        if (seat.isPlayingThisRound()) {
            showMessage(getPrefix(seat) + " gets " + seat.getHand(0).show() + ".");
        }
    }

    @Override
    public void dealerUpcardIsAce() {
        showMessage("Dealer upcard is an ace. Insurance is made available.");
    }

    @Override
    public void insuranceBetMade(Seat seat) {
        if (seat.hasInsuranceBet()) {
            showMessage(getPrefix(seat)
                    + " makes an insurance bet of "
                    + seat.getInsuranceBetForDisplay() + ".");
        }
    }

    @Override
    public void payInsurance(Seat seat) {
        if (seat.hasInsuranceBet()) {
            showMessage(getPrefix(seat)
                    + " won insurance bet of "
                    + seat.getInsuranceBetForDisplay() + ", and got double back!");
        }
    }

    @Override
    public void revealDealerHand(HandForDealer handForDealer) {
        showMessage("Dealer turns over the hole card, and it's " + handForDealer.showHoleCard() + ".");
        showMessage("Dealer's hand is " + handForDealer.show() + ".");
    }

    @Override
    public void dealerHasBlackjack(HandForDealer handForDealer) {
        //showMessage("Dealer checks the hole card, then turns it over showing " + handForDealer.show() + ". Blackjack!");
    }

    @Override
    public void pushOnDealerBlackjack(Seat seat) {
        showMessage(getPrefix(seat)
                + " also has blackjack, and gets back the bet of "
                + seat.getHand(0).getBetAmountForDisplay() + ".");
    }

    @Override
    public void loseOnDealerBlackjack(Seat seat) {
        showMessage(getPrefix(seat)
                + " loses " + seat.getHand(0).getBetAmountForDisplay()
                + " to the dealer blackjack.");
    }

    @Override
    public void playerBlackjack(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat)
                + " got blackjack! Bet of " + hand.getBetAmountForDisplay()
                + " wins " + hand.getBetAmountBlackjackForDisplay() + ".");
    }

    @Override
    public void playerStand(
            Seat seat,
            HandForPlayer hand) {
        showMessage(getPrefix(seat, hand.getHandIndex()) + " stands with " + hand.show() + ".");
    }

    @Override
    public void playerHitAndBust(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat, hand.getHandIndex())
                + " hits and busts with " + hand.show()
                + ". Loses " + hand.getBetAmountForDisplay() + ".");
    }

    @Override
    public void playerHitAndGot21(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat, hand.getHandIndex())
                + " hits and gets 21 with " + hand.show() + ".");
    }

    @Override
    public void playerHit(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat, hand.getHandIndex())
                + " hits and now has " + hand.show() + ".");
    }

    @Override
    public void playerDoubledAndBust(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat, hand.getHandIndex())
                + " doubles and busts with " + hand.show() + ".");
    }

    @Override
    public void playerDoubledAndGot21(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat, hand.getHandIndex())
                + " doubles and gets 21 with " + hand.show() + ".");
    }

    @Override
    public void playerDoubled(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat, hand.getHandIndex())
                + " doubles and gets " + hand.show()
                + ". Bet is now " + hand.getBetAmountForDisplay() + ".");
    }

    @Override
    public void playerSurrendered(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat)
                + " surrenders with " + hand.show() + ".");
    }

    @Override
    public void playerSplits(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat, hand.getHandIndex())
                + " splits with " + hand.show() + ".");
    }

    @Override
    public void gotSecondCardOnSplit(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat, hand.getHandIndex())
                + " gets the second card on the split hand, and now has " + hand.show() + ".");
    }

    @Override
    public void gotSecondCardOnSplitAndGot21(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat, hand.getHandIndex())
                + " gets the second card on the split hand, and reached 21 points with " + hand.show() + ".");
    }

    @Override
    public void gotSecondCardOnSplitAndCannotContinue(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat, hand.getHandIndex())
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

    @Override
    public void playerWins(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat, hand.getHandIndex())
                + ", hand of " + hand.show() + " beat the dealer, and won "
                + hand.getBetAmountForDisplay() + ".");
    }

    @Override
    public void playerLoses(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat, hand.getHandIndex())
                + ", hand of " + hand.show() + " lost. Loses "
                + hand.getBetAmountForDisplay() + ".");
    }

    @Override
    public void playerPushes(Seat seat, HandForPlayer hand) {
        showMessage(getPrefix(seat, hand.getHandIndex())
                + ", hand of " + hand.show() + " tied with the dealer.");
    }

    @Override
    public void showRules(Rules rules) {
        showMessage(rules.show());
    }

    @Override
    public void playerDeclinesToBet(Seat seat) {
        showMessage(getPrefix(seat) + " doesn't place a bet.");
    }
}
