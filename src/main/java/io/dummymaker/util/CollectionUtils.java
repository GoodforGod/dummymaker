package io.dummymaker.util;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Basic util methods for collections
 *
 * @author GoodforGod
 * @since 08.03.2018
 */
public class CollectionUtils {

    public static boolean isEmpty(final Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(final Collection collection) {
        return !isEmpty(collection);
    }

    public static int getRandomIndex(final Collection collection) {
        return generateRandomSize(0, collection.size() - 1);
    }

    public static int getIndexWithSalt(int size, String name, int salt) {
        int counter = 0;
        for (char c : name.toCharArray())
            counter += c;

        final int shift = counter + salt;
        return Math.abs(shift % size);
    }

    public static int generateRandomSize(int min, int max) {
        final int usedMin = (min < 1) ? 0 : min;
        final int usedMax = (max < 1) ? 0 : max;

        return (usedMin >= usedMax) ? usedMin : ThreadLocalRandom.current().nextInt(usedMin, usedMax);
    }

    public static int generateRandomSize(int min, int max, int fixed) {
        return (fixed > -1) ? fixed : generateRandomSize(min, max);
    }
}
