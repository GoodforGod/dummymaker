package io.dummymaker.scan.impl;

import io.dummymaker.annotation.ComplexGen;
import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.annotation.complex.GenMap;
import io.dummymaker.annotation.complex.GenSet;
import io.dummymaker.annotation.special.GenEmbedded;
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
 * @see ComplexGen
 *
 * @see GenIgnoreExport
 * @see GenForceExport
 *
 * @see UniqueScanner
 *
 * @author GoodforGod
 * @since 03.06.2017
 */
public class ExportScanner extends UniqueScanner {

    /**
     * Check for accepted for export annotations
     *
     * @see PrimeGen
     * @see ComplexGen
     *
     * @see GenForceExport
     */
    private final Predicate<Annotation> isAcceptable = (a) -> a.annotationType().equals(PrimeGen.class)
            || a.annotationType().equals(ComplexGen.class)
            || a.annotationType().equals(GenForceExport.class);

    /**
     * Check for ignorable annotations
     *
     * @see GenIgnoreExport
     */
    private final Predicate<Annotation> isIgnorable = (a) -> a.annotationType().equals(GenIgnoreExport.class)
            || a.annotationType().equals(GenEmbedded.class)
            || a.annotationType().equals(GenList.class)
            || a.annotationType().equals(GenSet.class)
            || a.annotationType().equals(GenMap.class);

    @Override
    public Map<Field, List<Annotation>> scan(final Class t) {
//        final Map<Field, List<Annotation>> exportAnnotationMap = new HashMap<>();
//
//        // Use only first found gen annotation on field
//        for(final Field field : t.getDeclaredFields()) {
//            if(Arrays.stream(field.getDeclaredAnnotations()).anyMatch(isIgnorable))
//                continue;
//
//            nextField:
//            for(Annotation annotation : field.getDeclaredAnnotations()) {
//                for(Annotation inline : annotation.annotationType().getDeclaredAnnotations()) {
//                    if(isAcceptable.test(inline)) {
//                        exportAnnotationMap.put(field, Collections.singletonList(inline));
//                        break nextField;
//                    }
//                }
//            }
//        }
//
//        return exportAnnotationMap;

        final Map<Field, List<Annotation>> classFieldAnnotations = super.scan(t);

        return classFieldAnnotations.entrySet().stream()
                .filter(e -> e.getValue().stream().noneMatch(isIgnorable))
                .peek(e -> e.setValue(e.getValue().stream()
                        .filter(isAcceptable)
                        .collect(Collectors.toList())))
                .filter(e -> !e.getValue().isEmpty())
                .collect(LinkedHashMap<Field, List<Annotation>>::new,
                        (m, e) -> m.put(e.getKey(), e.getValue()),
                        (m, u) -> { }
                );
    }
}
