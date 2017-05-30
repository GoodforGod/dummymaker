package io.generator.scan;

import io.generator.annotations.PrimeGenAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 29.05.2017
 */
public class GenAnnotationScanner extends AnnotationScanner {

    @Override
    public Map<Field, Set<Annotation>> scan(Class t) {
        return super.scan(t).entrySet().stream()
                .filter(set -> set.getValue()
                        .stream().anyMatch(a -> a.getClass().equals(PrimeGenAnnotation.class)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
