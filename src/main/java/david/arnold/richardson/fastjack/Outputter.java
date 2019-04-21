package david.arnold.richardson.fastjack;

// Note: Why all these methods? Why not just use showMessage with a string?
// To save CPU. Don't allocate memory and do operations unless needed.
public abstract class Outputter {
    public abstract boolean isDisplaying();

    public abstract boolean usingCarefulAccounting();

    public abstract void showMessage(String message);

    public abstract void startRound(int roundNumber);

    public abstract void cutCardWasDrawn();

    public abstract void shuffle(int numDecks);

    public abstract void placeCutCard(Shoe shoe);

    public abstract void showBurnCards(Shoe shoe);

    public abstract void showDealerUpcard(HandForDealer handForDealer);

    public abstract void roundAborted();

    public abstract void placeBet(Seat seat, int handIndex, long desiredBetAmount);

    public abstract void sitPlayer(Seat seat);

    public abstract void showDealtHand(Seat seat);

    public abstract void dealerUpcardIsAce();

    public abstract void insuranceBetMade(Seat seat);

    public abstract void revealDealerHand(HandForDealer handForDealer);

    public abstract long playerWinsInsuranceBet(Seat seat);

    public abstract void pushOnDealerBlackjack(Seat seat);

    public abstract long loseOnDealerBlackjack(Seat seat);

    public abstract long playerBlackjackAndWins(Seat seat, HandForPlayer hand);

    public abstract void playerStand(Seat seat, HandForPlayer hand);

    public abstract long playerHitAndBust(Seat seat, HandForPlayer hand);

    public abstract void playerHitAndGot21(Seat seat, HandForPlayer hand);

    public abstract void playerHit(Seat seat, HandForPlayer hand);

    public abstract long playerDoubledAndBust(Seat seat, HandForPlayer hand);

    public abstract void playerDoubledAndGot21(Seat seat, HandForPlayer hand);

    public abstract void playerDoubled(Seat seat, HandForPlayer hand);

    public abstract long playerSurrendered(Seat seat, HandForPlayer hand);

    public abstract void playerSplits(Seat seat, HandForPlayer hand);

    public abstract void gotSecondCardOnSplit(Seat seat, HandForPlayer hand);

    public abstract void gotSecondCardOnSplitAndGot21(Seat seat, HandForPlayer hand);

    public abstract void gotSecondCardOnSplitAndCannotContinue(Seat seat, HandForPlayer hand);

    public abstract void showDealerHandResult(HandForDealer handForDealer, boolean dealerBusted);

    public abstract long playerWins(Seat seat, HandForPlayer hand);

    public abstract long playerLoses(Seat seat, HandForPlayer hand);

    public abstract void playerPushes(Seat seat, HandForPlayer hand);

    public abstract void showRules(Rules rules);

    public abstract void playerDeclinesToBet(Seat seat);
}
