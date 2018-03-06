package io.dummymaker.scan.impl;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.annotation.collection.GenList;
import io.dummymaker.annotation.collection.GenMap;
import io.dummymaker.annotation.collection.GenSet;
import io.dummymaker.annotation.time.GenTime;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Scanner annotations that utilize primeGenAnnotation only
 *
 * @see PrimeGen
 * @see AnnotationScanner
 *
 * @author GoodforGod
 * @since 29.05.2017
 */
public class PopulateAnnotationScanner extends AnnotationScanner {

    /**
     * Predicate to check for core prime gen annotation
     *
     * @see PrimeGen
     */
    private final Predicate<Annotation> markedAnnotationPredicate = (a) -> a.annotationType().equals(PrimeGen.class);
    private final Predicate<Annotation> populateAnnotationPredicate = (a) -> a.annotationType().equals(PrimeGen.class)
            || a.annotationType().equals(GenList.class)
            || a.annotationType().equals(GenSet.class)
            || a.annotationType().equals(GenMap.class)
            || a.annotationType().equals(GenTime.class);

    @Override
    public Map<Field, List<Annotation>> scan(final Class t) {
        final Map<Field, List<Annotation>> classFieldAnnotations = super.scan(t);

        return classFieldAnnotations.entrySet().stream()
                .filter(set -> set.getValue().stream().anyMatch(markedAnnotationPredicate))
                .peek(set -> set.setValue(set.getValue().stream()
                        .filter(populateAnnotationPredicate)
                        .collect(Collectors.toList())))
                .collect(LinkedHashMap<Field, List<Annotation>>::new,
                        (m, e) -> m.put(e.getKey(), e.getValue()),
                        (m, u) -> { }
                );
    }
}
