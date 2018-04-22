package io.dummymaker.scan.impl;

import io.dummymaker.annotation.special.GenEnumerate;

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
 * @see GenEnumerate
 *
 * @see BasicScanner
 *
 * @author GoodforGod
 * @since 07.06.2017
 */
public class EnumerateScanner extends UniqueScanner {

    /**
     * Predicate to check for enumerate annotation
     *
     * @see GenEnumerate
     */
    private final Predicate<Annotation> isEnumerate = (a) -> a.annotationType().equals(GenEnumerate.class);

    @Override
    public Map<Field, List<Annotation>> scan(final Class t) {
        final Map<Field, List<Annotation>> populateAnnotationMap = new HashMap<>();

        // Use only first found gen annotation on field
        for(final Field field : t.getDeclaredFields()) {
            for(Annotation annotation : field.getDeclaredAnnotations()) {
                if (isEnumerate.test(annotation)) {
                    populateAnnotationMap.put(field, Collections.singletonList(annotation));
                    break;
                }
            }
        }

        return populateAnnotationMap;
    }
}
