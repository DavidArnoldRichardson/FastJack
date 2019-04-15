package david.arnold.richardson.fastjack;

public class MoneyHelper {
    public static String formatForDisplay(long currencyAmountInCents) {
        boolean isNegative = currencyAmountInCents < 0L;
        if (isNegative) {
            currencyAmountInCents = -currencyAmountInCents;
        }

        int numCents = (int) (currencyAmountInCents % 100);
        long numDollars = currencyAmountInCents / 100;
        String dollarString = String.valueOf(numDollars);
        String centsString = String.format("%02d", numCents);
        String result = "$" + dollarString + "." + centsString;
        if (isNegative) {
            result = "-" + result;
        }
        return result;
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
