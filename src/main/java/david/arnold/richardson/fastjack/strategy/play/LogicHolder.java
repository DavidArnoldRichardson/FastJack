package david.arnold.richardson.fastjack.strategy.play;

// This holds an instantiation of all the logic tables, so they are hot and ready when needed.
public class LogicHolder {

    private Matrix matrixSurrenderIsPairS17;
    private Matrix matrixSurrenderIsPairH17;
    private Matrix matrixSurrenderIsNotPairS17;
    private Matrix matrixSurrenderIsNotPairH17;

    private Matrix matrixSplitDas;
    private Matrix matrixSplitSingleDeckNoDas;
    private Matrix matrixSplitDoubleDeckNoDas;
    private Matrix matrixSplitManyDeckNoDas;

    private Matrix matrixDoubleSoftManyDeckS17;
    private Matrix matrixDoubleSoftManyDeckH17;
    private Matrix matrixDoubleSoftDoubleDeckH17;
    private Matrix matrixDoubleSoftSingleDeck;

    private Matrix matrixDoubleHardManyDeckH17;
    private Matrix matrixDoubleHardManyDeckS17;
    private Matrix matrixDoubleHardDoubleDeck;
    private Matrix matrixDoubleHardSingleDeck;

    private Matrix matrixHitStandSoft;
    private Matrix matrixHitStandHard;

    public LogicHolder() {
        matrixSurrenderIsPairS17 = new MatrixSurrenderIsPairS17();
        matrixSurrenderIsPairH17 = new MatrixSurrenderIsPairH17();
        matrixSurrenderIsNotPairS17 = new MatrixSurrenderIsNotPairS17();
        matrixSurrenderIsNotPairH17 = new MatrixSurrenderIsNotPairH17();

        matrixSplitDas = new MatrixSplitDas();
        matrixSplitSingleDeckNoDas = new MatrixSplitSingleDeckNoDas();
        matrixSplitDoubleDeckNoDas = new MatrixSplitDoubleDeckNoDas();
        matrixSplitManyDeckNoDas = new MatrixSplitManyDeckNoDas();

        matrixDoubleSoftManyDeckS17 = new MatrixDoubleSoftManyDeckS17();
        matrixDoubleSoftManyDeckH17 = new MatrixDoubleSoftManyDeckH17();
        matrixDoubleSoftDoubleDeckH17 = new MatrixDoubleSoftDoubleDeckH17();
        matrixDoubleSoftSingleDeck = new MatrixDoubleSoftSingleDeck();

        matrixDoubleHardManyDeckH17 = new MatrixDoubleHardManyDeckH17();
        matrixDoubleHardManyDeckS17 = new MatrixDoubleHardManyDeckS17();
        matrixDoubleHardDoubleDeck = new MatrixDoubleHardDoubleDeck();
        matrixDoubleHardSingleDeck = new MatrixDoubleHardSingleDeck();

        matrixHitStandSoft = new MatrixHitStandSoft();
        matrixHitStandHard = new MatrixHitStandHard();
    }

    public Matrix getMatrixSurrenderIsPairS17() {
        return matrixSurrenderIsPairS17;
    }

    public Matrix getMatrixSurrenderIsPairH17() {
        return matrixSurrenderIsPairH17;
    }

    public Matrix getMatrixSurrenderIsNotPairS17() {
        return matrixSurrenderIsNotPairS17;
    }

    public Matrix getMatrixSurrenderIsNotPairH17() {
        return matrixSurrenderIsNotPairH17;
    }

    public Matrix getMatrixSplitDas() {
        return matrixSplitDas;
    }

    public Matrix getMatrixSplitSingleDeckNoDas() {
        return matrixSplitSingleDeckNoDas;
    }

    public Matrix getMatrixSplitDoubleDeckNoDas() {
        return matrixSplitDoubleDeckNoDas;
    }

    public Matrix getMatrixSplitManyDeckNoDas() {
        return matrixSplitManyDeckNoDas;
    }

    public Matrix getMatrixDoubleSoftManyDeckS17() {
        return matrixDoubleSoftManyDeckS17;
    }

    public Matrix getMatrixDoubleSoftManyDeckH17() {
        return matrixDoubleSoftManyDeckH17;
    }

    public Matrix getMatrixDoubleSoftDoubleDeckH17() {
        return matrixDoubleSoftDoubleDeckH17;
    }

    public Matrix getMatrixDoubleSoftSingleDeck() {
        return matrixDoubleSoftSingleDeck;
    }

    public Matrix getMatrixDoubleHardManyDeckH17() {
        return matrixDoubleHardManyDeckH17;
    }

    public Matrix getMatrixDoubleHardManyDeckS17() {
        return matrixDoubleHardManyDeckS17;
    }

    public Matrix getMatrixDoubleHardDoubleDeck() {
        return matrixDoubleHardDoubleDeck;
    }

    public Matrix getMatrixDoubleHardSingleDeck() {
        return matrixDoubleHardSingleDeck;
    }

    public Matrix getMatrixHitStandSoft() {
        return matrixHitStandSoft;
    }

    public Matrix getMatrixHitStandHard() {
        return matrixHitStandHard;
    }
}
