package io.dummymaker.scan;

import io.dummymaker.annotation.special.GenRenameExport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Scanner to find rename annotation for fields and constructor
 *
 * NULL key in map indicates constructor (class) new name
 *
 * @see GenRenameExport
 *
 * @author GoodforGod (Anton Kurako)
 * @since 06.07.2017
 */
public class RenameAnnotationScanner implements IScanner<String, String> {

    private final Logger logger = Logger.getLogger(RenameAnnotationScanner.class.getSimpleName());

    /**
     * Check if field is annotation with rename annotation
     *
     * @see GenRenameExport
     */
    private final Predicate<Annotation> renameField = (a) -> a.annotationType().equals(GenRenameExport.class);

    /**
     * Scan for renamed fields or classes
     *
     * @see GenRenameExport
     *
     * @param t class to scan
     * @return Map where Key is old field value, and Value is new renamed value
     *
     * Or NULL key indicates constructor (class) new name
     */
    @Override
    public Map<String, String> scan(final Class t) {
        final Map<String, String> renameMap = new LinkedHashMap<>();
        try {
            Arrays.stream(t.getConstructors())
                    .map(c -> Arrays.stream(c.getAnnotations())
                            .filter(a -> a.annotationType().equals(GenRenameExport.class))
                            .findAny())
                    .filter(Optional::isPresent)
                    .findAny()
                    .map(Optional::get)
                    .ifPresent(c -> renameMap.put(null, ((GenRenameExport) c).name()));

            for (final Field field : t.getDeclaredFields()) {
                Arrays.stream(field.getAnnotations())
                        .filter(renameField)
                        .findFirst()
                        .ifPresent(annotation -> renameMap.put(field.getName(), ((GenRenameExport) annotation).name()));
            }
        } catch (SecurityException e) {
            logger.warning(e.toString());
        }

        return renameMap;
    }
}
