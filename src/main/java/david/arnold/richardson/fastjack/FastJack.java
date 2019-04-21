package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.run.RunBasicStrategyVerbose;

public class FastJack {
    public static void main(String... args) {
        // todo: hook up some useful arguments

        // todo: fix bug - player edge should be -0.64%, but it's around +7.0%, which is WAY off.
        //  ideas on fixing it:
        //  -- step through the logic in the debugger
        //  -- read verbose output, looking for the flaw
        new RunBasicStrategyVerbose().run();
    }
}
