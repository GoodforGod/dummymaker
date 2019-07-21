package io.dummymaker.factory.impl;

import io.dummymaker.scan.IPopulateScanner;

/**
 * Populate gen annotated object fields without embedded ones
 * May lead to recursion if not used properly (when used in extensions)
 *
 * @author GoodforGod
 * @see PopulateFactory
 * @since 10.03.2018
 */
class GenDepthFactory extends GenFactory {

    GenDepthFactory(IPopulateScanner populateScanner) {
        super(populateScanner);
    }

    /**
     * Fill entity with data by starting with specified depth
     * @param t entity
     * @param depth to start entity data fill with
     * @param <T> type
     * @param factoryStorage storage as gen storage
     * @return entity filled with data
     */
    <T> T fillWithDepth(T t, int depth, GenStorage factoryStorage) {
        if (t == null)
            return null;

        return fillEntity(t, factoryStorage, depth);
    }
}
