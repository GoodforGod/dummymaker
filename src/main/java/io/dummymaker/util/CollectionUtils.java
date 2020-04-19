package io.dummymaker.util;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Basic util methods for collections
 *
 * @author GoodforGod
 * @since 08.03.2018
 */
public class CollectionUtils {

    private CollectionUtils() {}

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static <T> T getIndexWithSalt(List<T> list, String name, int salt) {
        int counter = 0;
        for (char c : name.toCharArray())
            counter += c;

        final int shift = counter + salt;
        return list.get(Math.abs(shift % list.size()));
    }

    public static int getIndexWithSalt(int size, String name, int salt) {
        int counter = 0;
        for (char c : name.toCharArray())
            counter += c;

        final int shift = counter + salt;
        return Math.abs(shift % size);
    }

    public static int random() {
        return ThreadLocalRandom.current().nextInt();
    }

    public static int random(int max) {
        return random(0, max);
    }

    /**
     * @param min to get
     * @param max to get
     * @return random from min (included) to max (excluded)
     */
    public static int random(int min, int max) {
        return ((int) random(min, ((long) max)));
    }

    public static long random(long max) {
        return random(0, max);
    }

    /**
     *
     * @param min to get
     * @param max to get
     * @return random from min (included) to max (excluded)
     */
    public static long random(long min, long max) {
        final long usedMin = (min < 1) ? 0 : min;
        final long usedMax = (max < 1) ? 0 : max;

        return (usedMin >= usedMax) ? usedMin : ThreadLocalRandom.current().nextLong(usedMin, usedMax);
    }
}
