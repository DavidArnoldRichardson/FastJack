package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.Rules;

// This holds an instantiation of all the matricies, so they are hot and ready when needed.
public class MatrixHolder {

    private Matrix matrixSurrenderS17;
    private Matrix matrixSurrenderH17;

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

    public MatrixHolder(Rules rules) {
        matrixSurrenderS17 = new MatrixSurrenderS17(rules);
        matrixSurrenderH17 = new MatrixSurrenderH17(rules);

        matrixSplitDas = new MatrixSplitDas(rules);
        matrixSplitSingleDeckNoDas = new MatrixSplitSingleDeckNoDas(rules);
        matrixSplitDoubleDeckNoDas = new MatrixSplitDoubleDeckNoDas(rules);
        matrixSplitManyDeckNoDas = new MatrixSplitManyDeckNoDas(rules);

        // todo: instantiate the missing matrixes
    }

    public Matrix getMatrixSurrenderS17() {
        return matrixSurrenderS17;
    }

    public Matrix getMatrixSurrenderH17() {
        return matrixSurrenderH17;
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
