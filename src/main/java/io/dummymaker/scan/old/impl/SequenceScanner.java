package io.dummymaker.scan.old.impl;

import io.dummymaker.annotation.complex.GenSequence;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

/**
 * Scanner to filter fields for enumerate annotation fields
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenSequence
 * @see MainAnnotationScanner
 * @since 07.06.2017
 */
public class SequenceScanner extends UniqueScanner {

    /**
     * Predicate to check for enumerate annotation
     *
     * @see GenSequence
     */
    private final Predicate<Annotation> isSequence = a -> GenSequence.class.equals(a.annotationType());

    @Override
    public @NotNull Map<Field, List<Annotation>> scan(Class<?> target) {
        final Map<Field, List<Annotation>> sequentialAnnotationMap = new HashMap<>();

        // Use only first found gen annotation on field
        for (final Field field : getValidFields(target)) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (isSequence.test(annotation)) {
                    sequentialAnnotationMap.put(field, Collections.singletonList(annotation));
                }
            }
        }

        return sequentialAnnotationMap;
    }
}
