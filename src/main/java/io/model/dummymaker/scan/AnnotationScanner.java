package io.model.dummymaker.scan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 30.05.2017
 */
public class AnnotationScanner implements IScanner {

    @Override
    public Map<Field, Set<Annotation>> scan(Class t) {
        Map<Field, Set<Annotation>> map = new HashMap<>();

        for(Field field : t.getDeclaredFields()) {
            for(Annotation annotation : field.getAnnotations()) {
                Set<Annotation> fieldAnnotated = map.putIfAbsent(field, createNode(annotation));

                if(fieldAnnotated != null) {
                    fieldAnnotated.add(annotation);
                    map.replace(field, fieldAnnotated);
                }

                for(Annotation primeAnnotation : annotation.annotationType().getDeclaredAnnotations()) {
                    Set<Annotation> fieldPrimeAnnotated = map.putIfAbsent(field, createNode(primeAnnotation));

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
