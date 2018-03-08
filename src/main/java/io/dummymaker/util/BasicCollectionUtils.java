package io.dummymaker.util;

import java.util.Collection;

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

}
