package io.dummymaker.util;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Basic util methods for collections
 *
 * @author GoodforGod
 * @since 08.03.2018
 */
public class BasicCollectionUtils {

    public static boolean isEmpty(final Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(final Collection collection) {
        return !isEmpty(collection);
    }

    public static int generateRandomAmount(final int min,
                                           final int max) {
        final int usedMin = (min < 1) ? 0 : min;
        final int usedMax = (max < 1) ? 0 : max;

        return (usedMin >= usedMax)
                ? usedMin
                : ThreadLocalRandom.current().nextInt(usedMin, usedMax);
    }

    public static int generateRandomAmount(final int min,
                                           final int max,
                                           final int fixed) {
        return (fixed > 0)
                ? fixed
                : generateRandomAmount(min, max);
    }
}
