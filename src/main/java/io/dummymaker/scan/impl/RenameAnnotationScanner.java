package io.dummymaker.scan.impl;

import io.dummymaker.annotation.special.GenRenameExport;
import io.dummymaker.scan.IScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Scan for renameExport annotation only
 *
 * NULL key indicates constructor/class new name
 *
 * @see GenRenameExport
 * @see BasicAnnotationScanner
 *
 * @author GoodforGod
 * @since 06.07.2017
 */
public class RenameAnnotationScanner implements IScanner<String, String> {

    private final Logger logger = Logger.getLogger(RenameAnnotationScanner.class.getSimpleName());

    /**
     * Check if field is annotation with rename annotation
     *
     * @see GenRenameExport
     */
    private final Predicate<Annotation> isRenamed = (a) -> a.annotationType().equals(GenRenameExport.class);

    /**
     * Scan for renamed fields or classes
     *
     * @see GenRenameExport
     *
     * @param t class to scan
     * @return Map where Key is old field value, and Value is new renamed value
     *
     * NULL key indicates constructor (class) new name
     */
    @Override
    public Map<String, String> scan(final Class t) {
        final Map<String, String> renameMap = new LinkedHashMap<>();
        try {
            Arrays.stream(t.getConstructors())
                    .map(c -> Arrays.stream(c.getAnnotations())
                            .filter(isRenamed)
                            .findAny().orElse(null))
                    .filter(Objects::nonNull)
                    .findAny()
                    .ifPresent(a -> renameMap.put(null, ((GenRenameExport) a).name()));

            for (final Field field : t.getDeclaredFields()) {
                Arrays.stream(field.getAnnotations())
                        .filter(isRenamed)
                        .findAny()
                        .ifPresent(a -> renameMap.put(field.getName(), ((GenRenameExport) a).name()));
            }
        } catch (SecurityException e) {
            logger.warning(e.toString());
        }

        return renameMap;
    }
}
