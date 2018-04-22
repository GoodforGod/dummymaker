package io.dummymaker.factory.impl;

import io.dummymaker.factory.gen.IGenerateFactory;
import io.dummymaker.scan.impl.PopulateEmbeddedFreeScanner;

import java.lang.annotation.Annotation;
import java.util.Map;

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

    public GenPopulateEmbeddedFreeFactory(final Map<Class<? extends Annotation>, Class<? extends IGenerateFactory>> generateFactoryProviders) {
        super(new PopulateEmbeddedFreeScanner(), generateFactoryProviders);
    }
}
