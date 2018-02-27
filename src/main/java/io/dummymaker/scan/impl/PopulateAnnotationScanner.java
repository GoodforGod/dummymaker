package io.dummymaker.scan.impl;

import io.dummymaker.annotation.PrimeGenAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Scanner annotations that utilize primeGenAnnotation only
 *
 * @see PrimeGenAnnotation
 * @see AnnotationScanner
 *
 * @author GoodforGod
 * @since 29.05.2017
 */
public class PopulateAnnotationScanner extends AnnotationScanner {

    /**
     * Predicate to check for core prime gen annotation
     *
     * @see PrimeGenAnnotation
     */
    private final Predicate<Annotation> acceptPredicate = (a) -> a.annotationType().equals(PrimeGenAnnotation.class);

    @Override
    public Map<Field, List<Annotation>> scan(final Class t) {
        final Map<Field, List<Annotation>> classFieldAnnotations = super.scan(t);

        return classFieldAnnotations.entrySet().stream()
                .filter(set -> set.getValue().stream().anyMatch(acceptPredicate))
                .peek(set -> set.setValue(set.getValue().stream()
                        .filter(acceptPredicate)
                        .collect(Collectors.toList())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
