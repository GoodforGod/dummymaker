package io.dummymaker.scan.impl;

import io.dummymaker.annotation.ComplexGen;
import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.container.impl.GenContainer;
import io.dummymaker.scan.IPopulateScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Scanner used by populate factory
 *
 * Scan for prime gen annotation and its child annotation
 * Scan also for GenEmbedded annotations to populate embedded fields
 *
 * @see PrimeGen
 * @see GenEmbedded
 *
 * @see BasicScanner
 * @see io.dummymaker.factory.IPopulateFactory
 *
 * @author GoodforGod
 * @since 29.05.2017
 */
public class PopulateScanner implements IPopulateScanner {

    /**
     * Predicate to check for core prime marker annotation
     *
     * @see PrimeGen
     */
    private final Predicate<Annotation> isGen = (a) -> a.annotationType().equals(PrimeGen.class)
            || a.annotationType().equals(ComplexGen.class);

    /**
     * Scan for prime gen annotation and its child annotation*
     *
     * @param t class to scan
     * @return populate field map, where
     * KEY is field that has populate annotations
     * VALUE are two annotations:
     * - 0 is primeGen annotation
     * - 1 is child primeGen annotation
     */
    @Override
    public Map<Field, GenContainer> scan(final Class t) {
        final Map<Field, GenContainer> populateAnnotationMap = new HashMap<>();

        // Use only first found gen annotation on field
        for(final Field field : t.getDeclaredFields()) {
            nextField:
            for(Annotation annotation : field.getDeclaredAnnotations()) {
                for(Annotation inline : annotation.annotationType().getDeclaredAnnotations()) {
                    if(isGen.test(inline)) {
                        populateAnnotationMap.put(field, new GenContainer(inline, annotation));
                        break nextField;
                    }
                }
            }
        }

        return populateAnnotationMap;
    }
}
