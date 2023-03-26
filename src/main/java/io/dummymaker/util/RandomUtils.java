package io.dummymaker.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Basic util methods for collections
 *
 * @author Anton Kurako (GoodforGod)
 * @since 12.11.2022
 */
public final class RandomUtils {

    private RandomUtils() {}

    public static int random() {
        return ThreadLocalRandom.current().nextInt();
    }

    /**
     * @param max to get (inclusive)
     * @return random from 0 to max (inclusive)
     */
    public static int random(int max) {
        return (int) generateRandom(0, max);
    }

    /**
     * @param max to get (inclusive)
     * @return random from 0 to max (inclusive)
     */
    public static long random(long max) {
        return generateRandom(0, max);
    }

    /**
     * @param min to get (inclusive)
     * @param max to get (inclusive)
     * @return random from min (inclusive) to max (inclusive)
     */
    public static int random(int min, int max) {
        return (int) generateRandom(min, max);
    }

    /**
     * @param min to get (inclusive)
     * @param max to get (inclusive)
     * @return random from min (inclusive) to max (inclusive)
     */
    public static long random(long min, long max) {
        return generateRandom(min, max);
    }

    /**
     * @param min to get (inclusive)
     * @param max to get (inclusive)
     * @return random from min (inclusive) to max (inclusive)
     */
    private static long generateRandom(long min, long max) {
        final long usedMin = (min < 1)
                ? 0
                : min;
        final long usedMax = (max < 1)
                ? 0
                : max;

        if (usedMin == usedMax) {
            return usedMin;
        }

        return (usedMin >= usedMax)
                ? usedMin
                : ThreadLocalRandom.current().nextLong(usedMin, usedMax) + 1;
    }
}
