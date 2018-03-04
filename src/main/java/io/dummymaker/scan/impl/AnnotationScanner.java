package io.dummymaker.scan.impl;

import io.dummymaker.scan.IAnnotationScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Scan for all annotated fields (without duplicates)
 * Core scanner implementation
 *
 * @see IAnnotationScanner
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
public class AnnotationScanner implements IAnnotationScanner {

    private final Logger logger = Logger.getLogger(AnnotationScanner.class.getSimpleName());

    @Override
    public Map<Field, List<Annotation>> scan(final Class t) {
        final Map<Field, List<Annotation>> fieldScanMap = new LinkedHashMap<>();

        try {
            for(Field field : t.getDeclaredFields()) {

                // So we can avoid duplicates but not to use Set in contract for scanner
                final Set<Annotation> annotations = Arrays.stream(field.getAnnotations())
                        .map(a -> {
                            final Set<Annotation> set = Arrays.stream(a.annotationType().getDeclaredAnnotations())
                                    .collect(Collectors.toSet());
                            set.add(a);
                            return set;
                        })
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet());

                fieldScanMap.put(field, new ArrayList<>(annotations));
            }
        } catch (SecurityException e) {
            logger.warning(e.toString());
        }

        return fieldScanMap;
    }
}
