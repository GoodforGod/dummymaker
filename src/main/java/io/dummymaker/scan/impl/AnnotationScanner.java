package io.dummymaker.scan.impl;

import io.dummymaker.scan.IAnnotationScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Scan for all annotated fields
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
    public Map<Field, Set<Annotation>> scan(final Class t) {
        final Map<Field, Set<Annotation>> classFieldAnnotations = new LinkedHashMap<>();

        try {
            for(final Field field : t.getDeclaredFields()) {
                for(final Annotation annotation : field.getAnnotations()) {

                    // Retrieve prev field annotation list or associate new annotation with it
                    final Set<Annotation> annotatedField = classFieldAnnotations.putIfAbsent(field, createNewList(annotation));
                    if(annotatedField != null) {
                        annotatedField.add(annotation);
                    }

                    // Do the same for declared annotations on field
                    for(Annotation primeAnnotation : annotation.annotationType().getDeclaredAnnotations()) {
                        final Set<Annotation> fieldPrimeAnnotated = classFieldAnnotations.putIfAbsent(field, createNewList(primeAnnotation));
                        if(fieldPrimeAnnotated != null) {
                            fieldPrimeAnnotated.add(primeAnnotation);
                        }
                    }
                }
            }
        } catch (SecurityException e) {
            logger.warning(e.toString());
        }

        return classFieldAnnotations;
    }

    private Set<Annotation> createNewList(Annotation a) {
        final Set<Annotation> annotations = new HashSet<>();
        annotations.add(a);
        return annotations;
    }
}
