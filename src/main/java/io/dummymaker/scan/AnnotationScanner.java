package io.dummymaker.scan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Scan for all annotated fields, is prime (parent) scanner for others
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
public class AnnotationScanner implements IFieldScanner {

    @Override
    public Map<Field, Set<Annotation>> scan(Class t) {
        final Map<Field, Set<Annotation>> map = new HashMap<>();

        for(Field field : t.getDeclaredFields()) {
            for(Annotation annotation : field.getAnnotations()) {
                final Set<Annotation> annotatedField = map.putIfAbsent(field, createNode(annotation));

                if(annotatedField != null) {
                    annotatedField.add(annotation);
                    map.replace(field, annotatedField);
                }

                for(Annotation primeAnnotation : annotation.annotationType().getDeclaredAnnotations()) {
                    final Set<Annotation> fieldPrimeAnnotated = map.putIfAbsent(field, createNode(primeAnnotation));

                    if(fieldPrimeAnnotated != null) {
                        fieldPrimeAnnotated.add(primeAnnotation);
                        map.replace(field, fieldPrimeAnnotated);
                    }
                }
            }
        }

        return map;
    }

    private Set<Annotation> createNode(Annotation a) {
        return new HashSet<Annotation>() {{ add(a); }};
    }
}
