package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.run.RunBasicStrategyVerbose;

public class FastJack {
    public static void main(String... args) {
        // todo: hook up some useful arguments

        // todo: fix bug - player edge should be -0.64%, but it's around +7.0%, which is WAY off.
        //  Ideas on finding the issue:
        //  -- Dealer play is probably not the issue, it's simple, and covered with test code.
        //  -- Basic Strategy is not the issue, if there was a bug in player decisions, the edge would be low, not high.
        //  -- Handling money might be the issue, though with careful accounting enabled, I still don't see a bug.
        //  -- Split hands, where other hands for the player busted, might be it. Need some tests around that.
        //  --
        //  --
        //  -- Step through the logic in the debugger, try to find a situation where something goes wrong.
        //  -- Read verbose output, looking for a logic error in the play.
        new RunBasicStrategyVerbose().run();
    }
}
