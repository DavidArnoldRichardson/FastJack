package david.arnold.richardson.fastjack;

public class MoneyPile {

    // This is in cents
    private long amount;

    private boolean canGoNegative;

    public MoneyPile() {
        this(0L, false);
    }

    private MoneyPile(
            long initialAmount,
            boolean canGoNegative) {
        this.amount = initialAmount;
        this.canGoNegative = canGoNegative;
    }

    public static MoneyPile createTableMoneyPile() {
        // Table can have negative money, just to make everything easy.
        return new MoneyPile(0L, true);
    }

    public static MoneyPile createPlayerMoneyPile(long initialAmount) {
        return new MoneyPile(initialAmount, false);
    }

    public void pay(
            MoneyPile recipient,
            long amountToPay) {
        if (!canGoNegative && amountToPay > amount) {
            throw new RuntimeException("Money going negative!");
        }

        recipient.amount += amountToPay;
        this.amount -= amountToPay;
    }

    public void payAll(MoneyPile recipient) {
        if (amount <= 0) {
            throw new RuntimeException("Bug! Can't pay all when 0 or negative amount.");
        }

        recipient.amount += amount;
        this.amount = 0L;
    }

    public boolean canAfford(long amountToCheck) {
        return amount >= amountToCheck;
    }

    public String formatForDisplay() {
        return show(amount);
    }

    public static String show(long amount) {
        boolean isNegative = amount < 0L;
        if (isNegative) {
            amount = -amount;
        }

        int numCents = (int) (amount % 100);
        long numDollars = amount / 100;
        String dollarString = String.valueOf(numDollars);
        String centsString = String.format("%02d", numCents);
        String result = "$" + dollarString + "." + centsString;
        if (isNegative) {
            result = "-" + result;
        }
        return result;
    }

    public long getAmount() {
        return amount;
    }

    public boolean hasSomeMoney() {
        return amount != 0L;
    }

    public static long computeAcceptableBet(
            long desiredBetAmount,
            long availableFunds,
            long minimumAllowed,
            long maximumAllowed) {
        if (minimumAllowed > availableFunds) {
            return 0L;
        }
        if (maximumAllowed > availableFunds) {
            maximumAllowed = availableFunds;
        }
        if (desiredBetAmount > availableFunds) {
            desiredBetAmount = availableFunds;
        }
        if (desiredBetAmount < minimumAllowed) {
            desiredBetAmount = minimumAllowed;
        }
        if (desiredBetAmount > maximumAllowed) {
            desiredBetAmount = maximumAllowed;
        }
        return desiredBetAmount;
    }
}
