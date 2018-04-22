package io.dummymaker.factory.impl;

import io.dummymaker.scan.impl.PopulateScanner;

/**
 * Populate all object fields annotated with gen annotation
 *
 * @see BasicPopulateFactory
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
public class GenPopulateFactory extends BasicPopulateFactory {

    public GenPopulateFactory() {
        super(new PopulateScanner());
    }
}
