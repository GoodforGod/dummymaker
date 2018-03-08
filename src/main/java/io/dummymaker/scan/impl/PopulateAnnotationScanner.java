package io.dummymaker.scan.impl;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.scan.IAnnotationScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Scanner used by populate factory
 *
 * Scan for prime gen annotation and its child annotation
 *
 * @see PrimeGen
 * @see BasicAnnotationScanner
 *
 * @author GoodforGod
 * @since 29.05.2017
 */
public class PopulateAnnotationScanner implements IAnnotationScanner {

    /**
     * Predicate to check for core prime marker annotation
     *
     * @see PrimeGen
     */
    private final Predicate<Annotation> isPrime = (a) -> a.annotationType().equals(PrimeGen.class);

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
    public Map<Field, List<Annotation>> scan(final Class t) {
        final Map<Field, List<Annotation>> populateAnnotationMap = new HashMap<>();

        for(final Field field : t.getDeclaredFields()) {
            for(Annotation annotation : field.getDeclaredAnnotations()) {
                for(Annotation inlined : annotation.annotationType().getDeclaredAnnotations()) {
                    if(isPrime.test(inlined)) {
                        populateAnnotationMap.put(field, Arrays.asList(inlined, annotation));
                    }
                }
            }
        }

        return populateAnnotationMap;
    }
}
