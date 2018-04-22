package io.dummymaker.factory.impl;

import io.dummymaker.factory.gen.IGenerateFactory;
import io.dummymaker.scan.impl.PopulateScanner;

import java.lang.annotation.Annotation;
import java.util.Map;

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

    public GenPopulateFactory(final Map<Class<? extends Annotation>, Class<? extends IGenerateFactory>> generateFactoryProviders) {
        super(new PopulateScanner(), generateFactoryProviders);
    }
}
