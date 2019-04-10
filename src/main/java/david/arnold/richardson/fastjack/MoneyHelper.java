package david.arnold.richardson.fastjack;

public class MoneyHelper {
    public static String formatForDisplay(long currencyAmountInCents) {
        int numCents = (int) (currencyAmountInCents % 100);
        long numDollars = currencyAmountInCents / 100;
        String dollarString = String.valueOf(numDollars);
        String centsString = String.format("%02d", numCents);
        return "$" + dollarString + "." + centsString;
    }

    public static long computeAcceptableBet(
            long desiredBet,
            long availableFunds,
            long minimumAllowed,
            long maximumAllowed) {
        if (minimumAllowed > availableFunds) {
            return 0L;
        }
        if (maximumAllowed > availableFunds) {
            maximumAllowed = availableFunds;
        }
        if (desiredBet > availableFunds) {
            desiredBet = availableFunds;
        }
        if (desiredBet < minimumAllowed) {
            desiredBet = minimumAllowed;
        }
        if (desiredBet > maximumAllowed) {
            desiredBet = maximumAllowed;
        }
        return desiredBet;
    }
}
