package io.model.dummymaker.scan;

import io.model.dummymaker.annotation.prime.PrimeGenAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 29.05.2017
 */
public class PopulateAnnotationScanner extends AnnotationScanner {

    private Predicate<Annotation> primePredicate = (a) -> a.annotationType().equals(PrimeGenAnnotation.class);

    @Override
    public Map<Field, Set<Annotation>> scan(Class t) {
        Map<Field, Set<Annotation>> mapToFilter = super.scan(t);

        return (!mapToFilter.isEmpty())
                    ? mapToFilter.entrySet().stream()
                        .filter(set -> set.getValue()
                                .stream().anyMatch(primePredicate))
                        .map(set -> {
                            set.setValue(set.getValue().stream().filter(primePredicate).collect(Collectors.toSet()));
                            return set;
                        })
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))

                    : mapToFilter;
    }
}
