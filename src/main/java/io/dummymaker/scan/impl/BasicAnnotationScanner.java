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
 * @see IAnnotationScanner
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
public class BasicAnnotationScanner implements IAnnotationScanner {

    protected final Logger logger = Logger.getLogger(BasicAnnotationScanner.class.getSimpleName());

    @Override
    public Map<Field, List<Annotation>> scan(final Class t) {
        final Map<Field, List<Annotation>> fieldScanMap = new LinkedHashMap<>();

        try {
            for(final Field field : t.getDeclaredFields()) {

                // So we can avoid duplicates but not to use Set in contract for scanner
                final List<Annotation> annotations = Arrays.stream(field.getAnnotations())
                        .map(this::buildDeclaredAnnotationList)
                        .flatMap(List::stream)
                        .collect(Collectors.toList());

                fieldScanMap.put(field, annotations);
            }
        } catch (SecurityException e) {
            logger.warning(e.toString());
        }

        return fieldScanMap;
    }

    /**
     * Retrieve declared annotations from parent one and build set of them all
     * @param annotation parent annotation
     * @return parent annotation and its declared ones
     */
    private List<Annotation> buildDeclaredAnnotationList(final Annotation annotation) {
        final List<Annotation> list = Arrays.stream(annotation.annotationType().getDeclaredAnnotations())
                .collect(Collectors.toList());
        list.add(annotation);
        return list;
    }
}
