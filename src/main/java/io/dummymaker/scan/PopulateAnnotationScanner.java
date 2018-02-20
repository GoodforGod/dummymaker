package io.dummymaker.scan;

import io.dummymaker.annotation.PrimeGenAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Scans for all fields annotated with super PrimeGenAnnotation annotations
 *
 * @see PrimeGenAnnotation
 *
 * @author GoodforGod
 * @since 29.05.2017
 */
public class PopulateAnnotationScanner extends AnnotationScanner {

    /**
     * Predicate to check for core prime gen annotation
     *
     * @see PrimeGenAnnotation
     */
    private final Predicate<Annotation> acceptPredicate = (a) -> a.annotationType().equals(PrimeGenAnnotation.class);

    @Override
    public Map<Field, List<Annotation>> scan(final Class t) {
        final Map<Field, List<Annotation>> classFieldAnnotations = super.scan(t);

        return (classFieldAnnotations.isEmpty())
                ? classFieldAnnotations
                : classFieldAnnotations.entrySet().stream()
                    .filter(set -> set.getValue().stream().anyMatch(acceptPredicate))
                    .map(e -> {
                        e.setValue(e.getValue().stream()
                                .filter(acceptPredicate)
                                .collect(Collectors.toList()));
                        return e;
                    })
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
