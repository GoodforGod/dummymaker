package io.dummymaker.scan.impl;

import io.dummymaker.scan.IFieldScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Scan for all annotated fields, is prime (parent) scanner for others
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
public class AnnotationScanner implements IFieldScanner {

    private final Logger logger = Logger.getLogger(AnnotationScanner.class.getSimpleName());

    @Override
    public Map<Field, List<Annotation>> scan(final Class t) {
        final Map<Field, List<Annotation>> classFieldAnnotations = new LinkedHashMap<>();

        try {
            for(final Field field : t.getDeclaredFields()) {
                for(final Annotation annotation : field.getAnnotations()) {
                    final List<Annotation> annotatedField = classFieldAnnotations.putIfAbsent(field, createNode(annotation));

                    if(annotatedField != null) {
                        annotatedField.add(annotation);
                        classFieldAnnotations.replace(field, annotatedField);
                    }

                    for(Annotation primeAnnotation : annotation.annotationType().getDeclaredAnnotations()) {
                        final List<Annotation> fieldPrimeAnnotated = classFieldAnnotations.putIfAbsent(field, createNode(primeAnnotation));

                        if(fieldPrimeAnnotated != null) {
                            fieldPrimeAnnotated.add(primeAnnotation);
                            classFieldAnnotations.replace(field, fieldPrimeAnnotated);
                        }
                    }
                }
            }
        } catch (SecurityException e) {
            logger.warning(e.toString());
        }

        return classFieldAnnotations;
    }

    private List<Annotation> createNode(final Annotation a) {
        final List<Annotation> annotations = new ArrayList<>();
        annotations.add(a);
        return annotations;
    }
}
