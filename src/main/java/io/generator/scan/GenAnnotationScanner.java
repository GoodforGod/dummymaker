package io.generator.scan;

import io.generator.annotations.PrimeGenAnnotation;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 29.05.2017
 */
public class GenAnnotationScanner extends AnnotationScanner {

    @Override
    public List<Annotation> scan(Class t) {
        return super.scan(t).stream()
                .filter(a -> a.annotationType().isAnnotationPresent(PrimeGenAnnotation.class))
                .collect(Collectors.toList());
    }
}
