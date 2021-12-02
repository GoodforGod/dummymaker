package io.dummymaker.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.NotNull;

/**
 * Basic util methods for collections
 *
 * @author GoodforGod
 * @since 08.03.2018
 */
public class CollectionUtils {

    private CollectionUtils() {}

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
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

    /**
     * @param array to get element
     * @param <T>   type of element
     * @return random element of array or null if array is empty
     */
    public static <T> T random(@NotNull T[] array) {
        return (array.length == 0)
                ? null
                : array[random(array.length)];
    }

    /**
     * Consider that accessing random element for {@link java.util.LinkedList} or {@link java.util.Set}
     * is O(N) where N is index of random element, this is due to this collections does not have random
     * access.
     *
     * @param collection to get random element from
     * @param <T>        type of element
     * @return random element of collection or null if collection is empty
     */
    public static <T> T random(@NotNull Collection<T> collection) {
        if (collection.isEmpty())
            return null;

        final int random = random(collection.size());
        if (collection instanceof List) {
            return ((List<T>) collection).get(random);
        } else {
            final Iterator<T> iterator = collection.iterator();
            for (int i = 0; i < random; i++)
                iterator.next();

            return iterator.next();
        }
    }

    public static int random(int max) {
        return random(0, max);
    }

    /**
     * @param min to get (inclusive)
     * @param max to get (exclusive)
     * @return random from min (included) to max (excluded)
     */
    public static int random(int min, int max) {
        return ((int) random(min, ((long) max)));
    }

    public static long random(long max) {
        return random(0, max);
    }

    /**
     * @param min to get (inclusive)
     * @param max to get (exclusive)
     * @return random from min (included) to max (excluded)
     */
    public static long random(long min, long max) {
        final long usedMin = (min < 1) ? 0 : min;
        final long usedMax = (max < 1) ? 0 : max;

        return (usedMin >= usedMax) ? usedMin : ThreadLocalRandom.current().nextLong(usedMin, usedMax);
    }
}
