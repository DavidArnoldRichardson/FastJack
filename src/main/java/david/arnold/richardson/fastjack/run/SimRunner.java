package david.arnold.richardson.fastjack.run;

import david.arnold.richardson.fastjack.*;

import java.time.Duration;
import java.time.Instant;

public abstract class SimRunner {

    protected String[] playerNames = new String[]{"Alex", "Beth", "Carl", "Dora", "Eric", "Fran", "Gary"};
    protected Outputter outputterVerbose = new OutputterVerboseToScreen();
    protected Outputter outputterSilent = new OutputterSilentWithCarefulAccounting();
    protected Table table;
    protected boolean playIsVerbose;

    public void run() {
        Rules rules = getRules();

        table = new Table(
                playIsVerbose ? outputterVerbose : outputterSilent,
                rules);

        Instant startTime = Instant.now();
        SimRunResult simRunResult = runHelper(table);
        Instant completionTime = Instant.now();
        long timeElapsedInMillis = Duration.between(startTime, completionTime).toMillis();
        simRunResult.show(
                outputterVerbose,
                table,
                timeElapsedInMillis);
    }

    public abstract SimRunResult runHelper(Table table);

    protected abstract Rules getRules();

    protected abstract int getNumPlayers();
}
