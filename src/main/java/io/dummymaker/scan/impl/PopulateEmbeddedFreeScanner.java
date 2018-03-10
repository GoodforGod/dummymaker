package io.dummymaker.scan.impl;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.scan.container.PopulateContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Scanner used by populate factory
 *
 * Scan for prime gen annotation and its child annotation
 * EXCLUDE EMBEDDED ANNOTATED FIELDS
 *
 * @see PrimeGen
 * @see GenEmbedded
 *
 * @see BasicScanner
 * @see io.dummymaker.factory.IPopulateFactory
 *
 * @author GoodforGod
 * @since 10.03.2018
 */
public class PopulateEmbeddedFreeScanner extends PopulateScanner {

    /**
     * Predicate to check for embedded marker annotation
     *
     * @see GenEmbedded
     */
    private final Predicate<Annotation> isEmbedded = (a) -> a.annotationType().equals(GenEmbedded.class);

    @Override
    public Map<Field, PopulateContainer> scan(final Class t) {
        return super.scan(t).entrySet()
                .stream()
                .filter(e -> !isEmbedded.test(e.getValue().getGen()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
