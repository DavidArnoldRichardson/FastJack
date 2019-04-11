package david.arnold.richardson.fastjack.strategy.play;

import david.arnold.richardson.fastjack.Rules;

public class DecisionTables {

    private DecisionTable decisionTableSurrenderS17;
    private DecisionTable decisionTableSurrenderH17;

    public DecisionTables(Rules rules) {
        decisionTableSurrenderS17 = new DecisionTableSurrenderS17(rules);
        decisionTableSurrenderH17 = new DecisionTableSurrenderH17(rules);
    }

    public DecisionTable getDecisionTableSurrenderS17() {
        return decisionTableSurrenderS17;
    }

    public DecisionTable getDecisionTableSurrenderH17() {
        return decisionTableSurrenderH17;
    }
}
