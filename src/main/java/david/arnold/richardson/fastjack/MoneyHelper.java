package david.arnold.richardson.fastjack;

public class MoneyHelper {
    public static String formatForDisplay(long currencyAmountInCents) {
        int numCents = (int) (currencyAmountInCents % 100);
        long numDollars = currencyAmountInCents / 100;
        String dollarString = String.valueOf(numDollars);
        String centsString = String.format("%02d", numCents);
        return "$" + dollarString + "." + centsString;
    }
}
