package io.dummymaker.factory.impl;

import io.dummymaker.factory.IPopulateEmbeddedFactory;
import io.dummymaker.scan.impl.PopulateEmbeddedFreeScanner;

/**
 * Populate gen annotated object fields without embedded ones
 * <p>
 * EXCLUDE EMBEDDED FIELDS
 *
 * @see BasicPopulateFactory
 *
 * @author GoodforGod
 * @since 10.03.2018
 */
public class GenPopulateEmbeddedFreeFactory extends BasicPopulateFactory implements IPopulateEmbeddedFactory {

    public GenPopulateEmbeddedFreeFactory() {
        super(new PopulateEmbeddedFreeScanner());
    }

    public <T> T populate(final T t,
                          final int depth) {
        return super.populate(t, depth);
    }
}
