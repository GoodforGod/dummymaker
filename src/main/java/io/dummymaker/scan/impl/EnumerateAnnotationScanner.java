package io.dummymaker.scan.impl;

import io.dummymaker.annotation.special.GenEnumerate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Scanner to filter fields for enumerate annotation fields
 *
 * @see GenEnumerate
 *
 * @see AnnotationScanner
 *
 * @author GoodforGod
 * @since 07.06.2017
 */
public class EnumerateAnnotationScanner extends AnnotationScanner {

    /**
     * Predicate to check for enumerate annotation
     *
     * @see GenEnumerate
     */
    private final Predicate<Annotation> numeratePredicate = (a) -> a.annotationType().equals(GenEnumerate.class);

    @Override
    public Map<Field, List<Annotation>> scan(final Class t) {
        final Map<Field, List<Annotation>> classFieldAnnotations = super.scan(t);

        return (classFieldAnnotations.isEmpty())
                ? classFieldAnnotations
                : classFieldAnnotations.entrySet().stream()
                    .filter(entry -> entry.getValue().stream().anyMatch(numeratePredicate))
                    .peek(e -> e.setValue(e.getValue().stream()
                            .filter(numeratePredicate)
                            .collect(Collectors.toList())))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
