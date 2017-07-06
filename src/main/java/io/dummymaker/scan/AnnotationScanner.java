package io.dummymaker.scan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
    public Map<Field, Set<Annotation>> scan(final Class t) {
        final Map<Field, Set<Annotation>> classFieldAnnotations = new HashMap<>();

        try {
            for(final Field field : t.getDeclaredFields()) {
                for(final Annotation annotation : field.getAnnotations()) {
                    final Set<Annotation> annotatedField = classFieldAnnotations.putIfAbsent(field, createNode(annotation));

                    if(annotatedField != null) {
                        annotatedField.add(annotation);
                        classFieldAnnotations.replace(field, annotatedField);
                    }

                    for(Annotation primeAnnotation : annotation.annotationType().getDeclaredAnnotations()) {
                        final Set<Annotation> fieldPrimeAnnotated = classFieldAnnotations.putIfAbsent(field, createNode(primeAnnotation));

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

    private Set<Annotation> createNode(final Annotation a) {
        return new HashSet<Annotation>() {{ add(a); }};
    }
}
