package io.dummymaker.factory.impl;

import io.dummymaker.scan.impl.PopulateEmbeddedFreeScanner;

/**
 * Populate gen annotated object fields without embedded ones
 *
 * EXCLUDE EMBEDDED FIELDS
 *
 * @see BasicPopulateFactory
 *
 * @author GoodforGod
 * @since 10.03.2018
 */
public class GenPopulateEmbeddedFreeFactory extends BasicPopulateFactory {

    public GenPopulateEmbeddedFreeFactory() {
        super(new PopulateEmbeddedFreeScanner());
    }
}
