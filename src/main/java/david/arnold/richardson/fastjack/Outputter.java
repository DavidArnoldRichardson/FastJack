package david.arnold.richardson.fastjack;

// Note: Why all these methods? Why not just use showMessage with a string?
// To save CPU. Don't allocate memory and do operations unless needed.
public abstract class Outputter {
    protected abstract void showMessage(String message);

    public abstract void startRound(int roundNumber);

    public abstract void cutCardWasDrawn();

    public abstract void showHoleCard(HandForDealer handForDealer);

    public abstract void freshCards(int numDecks);

    public abstract void shuffle();

    public abstract void placeCutCard(Shoe shoe);

    public abstract void showBurnCards(Shoe shoe);

    public abstract void showDealerUpcard(HandForDealer handForDealer);

    public abstract void showHand(Player player, HandForPlayer hand);
}
