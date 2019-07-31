package io.dummymaker.scan.impl;

import io.dummymaker.annotation.special.GenSequence;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Scanner to filter fields for enumerate annotation fields
 *
 * @author GoodforGod
 * @see GenSequence
 * @see AnnotationScanner
 * @since 07.06.2017
 */
public class SequenceScanner extends UniqueScanner {

    /**
     * Predicate to check for enumerate annotation
     *
     * @see GenSequence
     */
    private final Predicate<Annotation> isEnumerate = (a) -> a.annotationType().equals(GenSequence.class);

    @Override
    public Map<Field, List<Annotation>> scan(final Class target) {
        final Map<Field, List<Annotation>> sequentialAnnotationMap = new HashMap<>();

        // Use only first found gen annotation on field
        for (final Field field : getAllDeclaredFields(target)) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (isEnumerate.test(annotation)) {
                    sequentialAnnotationMap.put(field, Collections.singletonList(annotation));
                    break;
                }
            }
        }

        return sequentialAnnotationMap;
    }
}