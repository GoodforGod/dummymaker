package io.dummymaker.scan.impl;

import io.dummymaker.scan.IAnnotationScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Scan field for all annotations
 * Core scanner implementation
 *
 * @author GoodforGod
 * @see IAnnotationScanner
 * @since 30.05.2017
 */
public class AnnotationScanner extends BasicScanner implements IAnnotationScanner {

    private final Logger logger = Logger.getLogger(AnnotationScanner.class.getName());

    @Override
    public Map<Field, List<Annotation>> scan(final Class target) {
        final Map<Field, List<Annotation>> fieldAnnotationsMap = new LinkedHashMap<>();

        try {
            for (final Field field : getAllDeclaredFields(target)) {
                // So we can avoid duplicates but not to use Set in contract for scanner
                final List<Annotation> annotations = Arrays.stream(field.getAnnotations())
                        .map(this::getAllDeclaredAnnotations)
                        .flatMap(List::stream)
                        .collect(Collectors.toList());

                fieldAnnotationsMap.put(field, annotations);
            }
        } catch (SecurityException e) {
            logger.warning(e.getMessage());
        }

        return fieldAnnotationsMap;
    }
}
