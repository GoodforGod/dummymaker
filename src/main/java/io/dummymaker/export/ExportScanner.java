package io.dummymaker.export;

import io.dummymaker.annotation.export.GenExportForce;
import io.dummymaker.annotation.export.GenExportIgnore;
import io.dummymaker.annotation.export.GenExportName;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Scanner for special export annotations and produces export containers Which are used in exporters
 *
 * @author Anton Kurako (GoodforGod)
 * @see Exporter
 * @see GenExportIgnore
 * @see GenExportForce
 * @see GenExportName
 * @since 03.06.2017
 */
final class ExportScanner {

    private final FieldContainerFactory factory = new FieldContainerFactory();

    @NotNull
    List<ExportField> scan(Class<?> target) {
        return getValidFields(target).stream()
                .filter(f -> Arrays.stream(f.getDeclaredAnnotations())
                        .noneMatch(a -> GenExportIgnore.class.equals(a.annotationType())))
                .map(factory::build)
                .collect(Collectors.toList());
    }

    @NotNull
    private List<Field> getValidFields(Class<?> target) {
        return getAllFields(target).stream()
                .filter(f -> !f.isSynthetic())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .filter(f -> !Modifier.isNative(f.getModifiers()))
                .filter(f -> !Modifier.isSynchronized(f.getModifiers()))
                .filter(f -> !Modifier.isFinal(f.getModifiers()))
                .collect(Collectors.toList());
    }

    @NotNull
    private List<Field> getAllFields(Class<?> target) {
        if (target == null || Object.class.equals(target))
            return Collections.emptyList();

        final List<Field> collected = Arrays.stream(target.getDeclaredFields())
                .collect(Collectors.toList());

        collected.addAll(getValidFields(target.getSuperclass()));
        return collected;
    }
}
