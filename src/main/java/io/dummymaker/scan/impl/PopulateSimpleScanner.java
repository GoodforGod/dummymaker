package io.dummymaker.scan.impl;

import io.dummymaker.annotation.complex.*;
import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.container.impl.GenContainer;
import io.dummymaker.generator.simple.impl.EmbeddedGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Scanner used by populate factory
 * <p>
 * Scan for prime gen and complex gen annotation and its child annotation
 * EXCLUDE EMBEDDED ANNOTATED FIELDS
 *
 * @author GoodforGod
 * @see PrimeGen
 * @see ComplexGen
 * @see GenEmbedded
 * @see AnnotationScanner
 * @see io.dummymaker.factory.IPopulateFactory
 * @since 10.03.2018
 */
public class PopulateSimpleScanner extends PopulateScanner {

    /**
     * Predicate to check for embedded marker annotation
     * Also check for GenCollection annotations with EmbeddedGenerator
     *
     * @see GenEmbedded
     */
    private final Predicate<Annotation> isEmbedded = (a) -> a != null
            && (a.annotationType().equals(GenEmbedded.class)
            || (a.annotationType().equals(GenList.class) && EmbeddedGenerator.class.equals(((GenList) a).value()))
            || (a.annotationType().equals(GenSet.class) && EmbeddedGenerator.class.equals(((GenSet) a).value()))
            || (a.annotationType().equals(GenArray.class) && EmbeddedGenerator.class.equals(((GenArray) a).value()))
            || (a.annotationType().equals(GenArray2D.class) && EmbeddedGenerator.class.equals(((GenArray2D) a).value())));

    /**
     * Predicate to check for embedded marker annotation in map annotation
     *
     * @see GenEmbedded
     */
    private final Predicate<Annotation> isEmbeddedMap = (a) -> a != null
            && (a.annotationType().equals(GenMap.class)
            && (EmbeddedGenerator.class.equals(((GenMap) a).key()) || EmbeddedGenerator.class.equals(((GenMap) a).value())));

    @Override
    public Map<Field, GenContainer> scan(final Class target) {
        return super.scan(target).entrySet().stream()
                .filter(e -> !EmbeddedGenerator.class.equals(e.getValue().getGenerator()))
                .filter(e -> !isEmbeddedMap.test(e.getValue().getMarker()))
                .filter(e -> !isEmbedded.test(e.getValue().getMarker()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
