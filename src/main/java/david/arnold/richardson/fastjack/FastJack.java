package david.arnold.richardson.fastjack;

import david.arnold.richardson.fastjack.run.RunBasicStrategyVerbose;

// todo: fix player edge bug. Currently it's at -2.9%
// todo: ideas on finding the bug:
//  - Check all the decision tables against the book, and against the test code

public class FastJack {

    // todo: hook up some useful arguments
    public static void main(String... args) {
        new RunBasicStrategyVerbose().run();
    }
}
