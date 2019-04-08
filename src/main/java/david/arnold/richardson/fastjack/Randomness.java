package david.arnold.richardson.fastjack;

import java.util.Random;

public class Randomness {
    private Random random;
    private Long seed;
    private Boolean overrideNextPercentCheckWithThisValue = null;

    public Randomness(Long seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }

    public boolean getRandomBoolean() {
        return (random.nextLong() & 1) == 1;
    }

    public int getRandomInt(int max) {
        return random.nextInt(max);
    }

    private int getRandomNumberFromZeroToValueExclusive(int maxValue) {
        return random.nextInt(maxValue);
    }

    public boolean checkRandomPercentChance(int percentChance) {
        if (overrideNextPercentCheckWithThisValue != null) {
            boolean result = overrideNextPercentCheckWithThisValue;
            overrideNextPercentCheckWithThisValue = null;
            return result;
        }

        if (percentChance <= 0) {
            return false;
        }
        if (percentChance >= 100) {
            return true;
        }
        return getRandomNumberFromZeroToValueExclusive(100) < percentChance;
    }

    public static long generateRandomSeed() {
        Random rand = new Random();
        return rand.nextLong();
    }

    public Long getSeed() {
        return seed;
    }

    public void overrideNextPercentCheckWithThisValue(Boolean overrideNextPercentCheckWithThisValue) {
        this.overrideNextPercentCheckWithThisValue = overrideNextPercentCheckWithThisValue;
    }
}
