package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.Rules;

public class MatrixHolder {

    private Matrix matrixSurrenderS17;
    private Matrix matrixSurrenderH17;

    public MatrixHolder(Rules rules) {
        matrixSurrenderS17 = new MatrixSurrenderS17(rules);
        matrixSurrenderH17 = new MatrixSurrenderH17(rules);
    }

    public Matrix getMatrixSurrenderS17() {
        return matrixSurrenderS17;
    }

    public Matrix getMatrixSurrenderH17() {
        return matrixSurrenderH17;
    }
}
