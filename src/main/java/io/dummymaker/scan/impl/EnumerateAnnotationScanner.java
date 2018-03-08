package io.dummymaker.scan.impl;

import io.dummymaker.annotation.special.GenEnumerate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Scanner to filter fields for enumerate annotation fields
 *
 * @see GenEnumerate
 *
 * @see BasicAnnotationScanner
 *
 * @author GoodforGod
 * @since 07.06.2017
 */
public class EnumerateAnnotationScanner extends UniqueAnnotationScanner {

    /**
     * Predicate to check for enumerate annotation
     *
     * @see GenEnumerate
     */
    private final Predicate<Annotation> numeratePredicate = (a) -> a.annotationType().equals(GenEnumerate.class);

    @Override
    public Map<Field, List<Annotation>> scan(final Class t) {
        final Map<Field, List<Annotation>> classFieldAnnotations = super.scan(t);

        return classFieldAnnotations.entrySet().stream()
                .filter(entry -> entry.getValue().stream().anyMatch(numeratePredicate))
                .peek(e -> e.setValue(e.getValue().stream()
                        .filter(numeratePredicate)
                        .collect(Collectors.toList())))
                .collect(LinkedHashMap<Field, List<Annotation>>::new,
                        (m, e) -> m.put(e.getKey(), e.getValue()),
                        (m, u) -> { }
                );
    }
}
