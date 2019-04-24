package david.arnold.richardson.fastjack;

public enum PlayerEdge {
    H17(new double[]{-0.3D, -0.599D, -0.738D, -0.784D, -0.807D}),
    H17_RSA(new double[]{-0.26D, -0.529D, -0.654D, -0.696D, -0.717D}),
    H17_DAS(new double[]{-0.17D, -0.456D, -0.595D, -0.640D, -0.664D}),
    H17_DAS_RSA(new double[]{-0.12D, -0.387D, -0.512D, -0.552D, -0.573D}),
    H17_LS(new double[]{-0.30D, -0.546D, -0.661D, -0.696D, -0.719D}),
    H17_RSA_LS(new double[]{-0.25D, -0.476D, -0.578D, -0.609D, -0.629D}),
    H17_DAS_LS(new double[]{-0.16D, -0.404D, -0.518D, -0.556D, -0.576D}),
    H17_DAS_RSA_LS(new double[]{-0.12D, -0.334D, -0.434D, -0.468D, -0.486D}),
    S17(new double[]{-0.11D, -0.395D, -0.526D, -0.569D, -0.589D}),
    S17_RSA(new double[]{-0.06D, -0.323D, -0.44D, -0.478D, -0.496D}),
    S17_DAS(new double[]{0.02D, -0.256D, -0.385D, -0.428D, -0.449D}),
    S17_DAS_RSA(new double[]{0.07D, -0.184D, -0.3D, -0.337D, -0.356D}),
    S17_LS(new double[]{-0.11D, -0.356D, -0.462D, -0.5D, -0.517D}),
    S17_RSA_LS(new double[]{-0.07D, -0.285D, -0.377D, -0.409D, -0.423D}),
    S17_DAS_LS(new double[]{0.02D, -0.216D, -0.324D, -0.359D, -0.374D}),
    S17_DAS_RSA_LS(new double[]{0.06D, -0.145D, -0.239D, -0.269D, -0.281D});

    private double[] playerEdgesByNumDecks;

    PlayerEdge(double[] playerEdgesByNumDecks) {
        this.playerEdgesByNumDecks = playerEdgesByNumDecks;
    }

    public static double getPlayerEdgeValue(Rules rules) {
        int edgeLookupIndex = getEdgeLookupNumDeckIndex(rules.getNumDecks());
        if (edgeLookupIndex == -1) {
            System.out.println("Invalid number of decks: " + rules.getNumDecks());
            return 999D; // just a crazy value
        }
        String edgeLookupString = generateEdgeLookupString(rules);
        try {
            return valueOf(edgeLookupString).playerEdgesByNumDecks[edgeLookupIndex];
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid rules configuration: " + edgeLookupString);
            return 998D; // just a crazy value
        }
    }

    private static int getEdgeLookupNumDeckIndex(int numDecks) {
        switch (numDecks) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 4:
                return 2;
            case 6:
                return 3;
            case 8:
                return 4;
            default:
                return -1; // unknown
        }
    }

    private static String generateEdgeLookupString(Rules rules) {
        return (rules.isH17() ? "H17" : "S17") +
                (rules.isCanDoubleAfterSplit() ? "_DAS" : "") +
                (rules.isCanResplitAces() ? "_RSA" : "") +
                (rules.isLateSurrenderAvailable() ? "_LS" : "");
    }
}
