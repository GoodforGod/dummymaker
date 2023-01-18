package io.dummymaker.scan.old.impl;

import io.dummymaker.scan.old.AnnotationScanner;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Scan field for all annotations Core scanner implementation
 *
 * @author Anton Kurako (GoodforGod)
 * @see AnnotationScanner
 * @since 30.05.2017
 */
public class MainAnnotationScanner extends AbstractScanner implements AnnotationScanner {

    @Override
    public @NotNull Map<Field, List<Annotation>> scan(final Class<?> target) {
        final Map<Field, List<Annotation>> fieldAnnotationsMap = new LinkedHashMap<>();

        final List<Field> validFields = getValidFields(target);
        for (final Field field : validFields) {
            try {
                // So we can avoid duplicates but not to use Set in contract for scanner
                final List<Annotation> annotations = Arrays.stream(field.getAnnotations())
                        .map(this::getAllAnnotations)
                        .flatMap(List::stream)
                        .collect(Collectors.toList());

                fieldAnnotationsMap.put(field, annotations);
            } catch (SecurityException e) {}
        }

        return fieldAnnotationsMap;
    }
}
