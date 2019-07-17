package io.dummymaker.factory.impl;

import io.dummymaker.factory.IPopulateEmbeddedFactory;
import io.dummymaker.scan.impl.PopulateSimpleScanner;

/**
 * Populate gen annotated object fields without embedded ones
 * <p>
 * EXCLUDE EMBEDDED FIELDS
 *
 * @see PopulateFactory
 *
 * @author GoodforGod
 * @since 10.03.2018
 */
public class GenSimpleFactory extends PopulateFactory implements IPopulateEmbeddedFactory {

    public GenSimpleFactory() {
        super(new PopulateSimpleScanner());
    }

    public <T> T populate(final T t, final int depth) {
        return super.populate(t, depth);
    }
}
