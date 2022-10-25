package io.dummymaker.scan.impl;

import io.dummymaker.scan.IAnnotationScanner;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scan field for all annotations Core scanner implementation
 *
 * @author GoodforGod
 * @see IAnnotationScanner
 * @since 30.05.2017
 */
public class AnnotationScanner extends BasicScanner implements IAnnotationScanner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public @NotNull Map<Field, List<Annotation>> scan(final Class target) {
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
            } catch (SecurityException e) {
                logger.warn(e.getMessage());
            }
        }

        return fieldAnnotationsMap;
    }
}
