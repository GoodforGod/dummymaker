package io.dummymaker.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * Basic util methods for collections
 *
 * @author Anton Kurako (GoodforGod)
 * @since 08.03.2018
 */
public final class CollectionUtils {

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

    public static <T> T getIndexWithSalt(List<T> list, String name, long salt) {
        return list.get(getIndexWithSalt(list.size(), name, salt));
    }

    public static int getIndexWithSalt(int size, String name, long salt) {
        int counter = 0;
        for (char c : name.toCharArray())
            counter += c;

        final long shift = counter + salt;
        return ((Long) Math.abs(shift % size)).intValue();
    }

    /**
     * @param array to get element
     * @param <T>   type of element
     * @return random element of array or null if array is empty
     */
    public static <T> T random(@NotNull T[] array) {
        return (array.length == 0)
                ? null
                : array[RandomUtils.random(array.length)];
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

        final int random = RandomUtils.random(collection.size());
        if (collection instanceof List) {
            return ((List<T>) collection).get(random);
        } else {
            final Iterator<T> iterator = collection.iterator();
            for (int i = 0; i < random; i++)
                iterator.next();

            return iterator.next();
        }
    }
}
