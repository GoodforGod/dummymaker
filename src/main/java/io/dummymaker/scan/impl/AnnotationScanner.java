package io.dummymaker.scan.impl;

import io.dummymaker.scan.IAnnotationScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
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
public class AnnotationScanner extends BasicScanner implements IAnnotationScanner {

    private final Logger logger = Logger.getLogger(AnnotationScanner.class.getName());

    @Override
    public Map<Field, List<Annotation>> scan(final Class t) {
        final Map<Field, List<Annotation>> fieldAnnotationsMap = new LinkedHashMap<>();

        try {
            final List<Field> fields = getAllDeclaredFields(t);
            for (final Field field : fields) {
                if(field.isSynthetic())
                    continue;

                // So we can avoid duplicates but not to use Set in contract for scanner
                final List<Annotation> annotations = Arrays.stream(field.getAnnotations())
                        .map(this::getAllDeclaredAnnotations)
                        .flatMap(List::stream)
                        .collect(Collectors.toList());

                fieldAnnotationsMap.put(field, annotations);
            }
        } catch (SecurityException e) {
            logger.warning(e.toString());
        }

        return fieldAnnotationsMap;
    }

    /**
     * Retrieve declared annotations from parent one and build set of them all
     *
     * @param annotation parent annotation
     * @return parent annotation and its declared ones
     */
    protected List<Annotation> getAllDeclaredAnnotations(final Annotation annotation) {
        final List<Annotation> list = Arrays.stream(annotation.annotationType().getDeclaredAnnotations())
                .collect(Collectors.toList());

        list.add(annotation);
        return list;
    }

    protected List<Field> getAllDeclaredFields(Class tClass) {
        if(tClass == null || Object.class.equals(tClass))
            return Collections.emptyList();

        final List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(tClass.getDeclaredFields()));
        fields.addAll(getAllDeclaredFields(tClass.getSuperclass()));

        return fields;
    }
}
