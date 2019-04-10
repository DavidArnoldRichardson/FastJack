package david.arnold.richardson.fastjack;

public class Table {

    public static final int NUM_SEATS = 7;

    private Outputter outputter;
    private Player[] players = new Player[NUM_SEATS];
    private int numPlayers = 0;
    private Rules rules;
    private Shoe shoe;
    private HandForDealer handForDealer;

    public Table(
            Outputter outputter,
            Rules rules) {
        this.outputter = outputter;
        this.rules = rules;
        this.shoe = new Shoe(rules, outputter);
        for (int i = 0; i < NUM_SEATS; i++) {
            players[i] = null;
        }
        handForDealer = new HandForDealer(shoe);
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void addPlayer(Player player) {
        players[numPlayers++] = player;
    }

    public void playRounds(int numRoundsToPlay) {
        shoe.shuffle();
        shoe.setPenetration();
        shoe.burnCards();

        // Note: Everything that happens inside this loop is optimized for performance.
        // Don't do anything unnecessary. No allocating memory from the heap.
        for (int roundNumber = 0; roundNumber < numRoundsToPlay; roundNumber++) {
            playRound(roundNumber);
        }
    }

    void playRound(int roundNumber) {
        outputter.startRound(roundNumber);

        if (shoe.hasCutCardBeenDrawn()) {
            outputter.cutCardWasDrawn();
            shoe.shuffle();
            shoe.setPenetration();
            shoe.burnCards();
        }

        // set player bets
        for (int seatNumber = 0; seatNumber < numPlayers; seatNumber++) {
            players[seatNumber].createNewHandWithBet();
        }

        // deal first card to each player
        for (int seatNumber = 0; seatNumber < numPlayers; seatNumber++) {
            players[seatNumber].getHand(0).addCard(shoe.dealCard());
        }

        // deal first card to dealer
        handForDealer.addCard(shoe.dealCard());

        // deal second card to each player
        for (int seatNumber = 0; seatNumber < numPlayers; seatNumber++) {
            players[seatNumber].getHand(0).addCard(shoe.dealCard());
        }

        // deal hole card to dealer
        handForDealer.addCard(shoe.dealCard());
        outputter.showDealerUpcard(handForDealer);

        for (int seatNumber = 0; seatNumber < numPlayers; seatNumber++) {
            playPlayer(players[seatNumber]);
        }

        playDealer();

        // todo: handle bets

        handForDealer.reset();
        for (int seatNumber = 0; seatNumber < numPlayers; seatNumber++) {
            players[seatNumber].resetHands();
        }
    }

    private void playPlayer(Player player) {
        // More may be added due to splits.
        int handIndexToPlay = 0;

        while(player.getNumHandsInUse() > handIndexToPlay) {
            HandForPlayer hand = player.getHand(handIndexToPlay++);
        }
    }

    private void playPlayerHand(
            Player player,
            HandForPlayer hand) {
        outputter.showHand(player, hand);
    }

    private void playDealer() {
        outputter.showHoleCard(handForDealer);

        // todo
    }
}
