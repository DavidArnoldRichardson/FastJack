package david.arnold.richardson.fastjack;

// Note: Why all these methods? Why not just use showMessage with a string?
// To save CPU. Don't allocate memory and do operations unless needed.
public abstract class Outputter {
    protected abstract void showMessage(String message);

    public abstract void startRound(int roundNumber);

    public abstract void cutCardWasDrawn();

    public abstract void freshCards(int numDecks);

    public abstract void shuffle();

    public abstract void placeCutCard(Shoe shoe);

    public abstract void showBurnCards(Shoe shoe);

    public abstract void showDealerUpcard(HandForDealer handForDealer);

    public abstract void roundAborted();

    public abstract void placeBet(Player player, int seatNumber, long desiredBetAmount);

    public abstract void sitPlayer(Player player, int seatNumber);

    public abstract void showDealtHand(Player player, int seatNumber, HandForPlayer hand);

    public abstract void dealerUpcardIsAce();

    public abstract void insuranceBetMade(Seat seat, long insuranceBet);

    public abstract void revealDealerHand(HandForDealer handForDealer);

    public abstract void payInsurance(Player player, int seatNumber, long insuranceBet);

    public abstract void dealerBlackjack();

    public abstract void pushOnDealerBlackjack(Player player, int seatNumber, long betAmount);

    public abstract void loseOnDealerBlackjack(Player player, int seatNumber, long betAmount);

    public abstract void playerBlackjack(Seat seat, HandForPlayer hand);

    public abstract void playerStand(Seat seat, HandForPlayer hand);

    public abstract void playerHitAndBust(Seat seat, HandForPlayer hand);

    public abstract void playerHitAndGot21(Seat seat, HandForPlayer hand);

    public abstract void playerHit(Seat seat, HandForPlayer hand);

    public abstract void playerDoubledAndBust(Seat seat, HandForPlayer hand);

    public abstract void playerDoubledAndGot21(Seat seat, HandForPlayer hand);

    public abstract void playerDoubled(Seat seat, HandForPlayer hand);

    public abstract void playerSurrendered(Seat seat, HandForPlayer hand);

    public abstract void playerSplits(Seat seat, HandForPlayer hand);

    public abstract void gotSecondCardOnSplit(Seat seat, HandForPlayer hand);

    public abstract void gotSecondCardOnSplitAndGot21(Seat seat, HandForPlayer hand);

    public abstract void gotSecondCardOnSplitAndCannotContinue(Seat seat, HandForPlayer hand);

    public abstract void dealerHandResult(HandForDealer handForDealer, boolean dealerBusted);
}
