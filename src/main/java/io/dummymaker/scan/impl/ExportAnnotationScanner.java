package io.dummymaker.scan.impl;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.annotation.special.GenForceExport;
import io.dummymaker.annotation.special.GenIgnoreExport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Scanner for special export annotations
 *
 * @see PrimeGen
 * @see GenIgnoreExport
 * @see GenForceExport
 *
 * @see BasicAnnotationScanner
 *
 * @author GoodforGod
 * @since 03.06.2017
 */
public class ExportAnnotationScanner extends UniqueAnnotationScanner {

    /**
     * Check for accepted for export annotations
     *
     * @see GenForceExport
     * @see PrimeGen
     */
    private final Predicate<Annotation> acceptPredicate = (a) -> (a.annotationType().equals(PrimeGen.class)
            || ((a.annotationType().equals(GenForceExport.class))
            && ((GenForceExport) a).value()));

    /**
     * Check for ignorable annotations
     *
     * @see GenIgnoreExport
     */
    private final Predicate<Annotation> ignorePredicate = (a) -> (a.annotationType().equals(GenIgnoreExport.class)
            && ((GenIgnoreExport) a).value());

    @Override
    public Map<Field, List<Annotation>> scan(final Class t) {
        final Map<Field, List<Annotation>> classFieldAnnotations = super.scan(t);

        return classFieldAnnotations.entrySet().stream()
                .filter(e -> e.getValue().stream().noneMatch(ignorePredicate))
                .filter(e -> e.getValue().stream().anyMatch(acceptPredicate))
                .peek(e -> e.setValue(e.getValue().stream()
                        .filter(acceptPredicate)
                        .collect(Collectors.toList())))
                .collect(LinkedHashMap<Field, List<Annotation>>::new,
                        (m, e) -> m.put(e.getKey(), e.getValue()),
                        (m, u) -> { }
                );
    }
}
