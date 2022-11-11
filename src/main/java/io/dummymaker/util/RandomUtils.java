package io.dummymaker.util;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
     * @param max to get (excluded)
     * @return random from 0 to max (excluded)
     */
    public static int random(int max) {
        return (int) generateRandom(0, max);
    }

    /**
     * @param max to get (excluded)
     * @return random from 0 to max (excluded)
     */
    public static long random(long max) {
        return generateRandom(0, max);
    }

    /**
     * @param from to get (inclusive)
     * @param to   to get (inclusive)
     * @return random from min (included) to max (excluded)
     */
    public static int random(int from, int to) {
        return (int) generateRandom(from, to);
    }

    /**
     * @param from to get (inclusive)
     * @param to   to get (inclusive)
     * @return random from min (included) to max (excluded)
     */
    public static long random(long from, long to) {
        return generateRandom(from, to);
    }

    /**
     * @param min to get (inclusive)
     * @param max to get (exclusive)
     * @return random from min (included) to max (excluded)
     */
    private static long generateRandom(long min, long max) {
        final long usedMin = (min < 1)
                ? 0
                : min;
        final long usedMax = (max < 1)
                ? 1
                : max;

        return (usedMin >= usedMax)
                ? usedMin
                : ThreadLocalRandom.current().nextLong(usedMin, usedMax);
    }
}
