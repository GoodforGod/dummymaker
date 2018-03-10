package io.dummymaker.scan.impl;

import io.dummymaker.scan.IAnnotationScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Scan for field and its unique annotations (without duplicates)
 * Core scanner implementation
 *
 * @see IAnnotationScanner
 * @see BasicScanner
 *
 * @author GoodforGod
 * @since 08.03.2018
 */
public class UniqueScanner extends BasicScanner {

    @Override
    public Map<Field, List<Annotation>> scan(final Class t) {
        return super.scan(t).entrySet().stream()
                .peek(e -> e.setValue(e.getValue().stream()
                        .distinct()
                        .collect(Collectors.toList())))
                .collect(LinkedHashMap<Field, List<Annotation>>::new,
                        (m, e) -> m.put(e.getKey(), e.getValue()),
                        (m, u) -> { }
                );
    }
}
