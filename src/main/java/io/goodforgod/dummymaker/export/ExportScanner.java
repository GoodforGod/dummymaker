package io.goodforgod.dummymaker.export;

import io.goodforgod.dummymaker.GenType;
import io.goodforgod.dummymaker.annotation.export.GenExportIgnore;
import io.goodforgod.dummymaker.annotation.export.GenExportName;
import io.goodforgod.dummymaker.cases.NamingCase;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

/**
 * Scanner for special export annotations and produces export containers Which are used in exporters
 *
 * @author Anton Kurako (GoodforGod)
 * @see Exporter
 * @see GenExportIgnore
 * @see GenExportName
 * @since 03.06.2017
 */
final class ExportScanner {

    private static class ScanField {

        private final Field value;
        private final GenType type;

        private ScanField(Field value, GenType type) {
            this.value = value;
            this.type = type;
        }

        public Field value() {
            return value;
        }

        public GenType type() {
            return type;
        }
    }

    private final Predicate<Field> fieldPredicate;
    private final NamingCase namingCase;

    ExportScanner(Set<String> fieldsInclude, Set<String> fieldsExclude, NamingCase namingCase) {
        Set<String> fieldsIncludes = Collections.unmodifiableSet(fieldsInclude);
        Set<String> fieldsExcludes = Collections.unmodifiableSet(fieldsExclude);
        this.namingCase = namingCase;
        if (!fieldsExcludes.isEmpty()) {
            this.fieldPredicate = field -> !fieldsExcludes.contains(field.getName());
        } else if (!fieldsIncludes.isEmpty()) {
            this.fieldPredicate = field -> fieldsIncludes.contains(field.getName());
        } else {
            this.fieldPredicate = field -> true;
        }
    }

    @NotNull
    public List<ExportField> scan(Class<?> target) {
        return getExportFields(target).stream()
                .map(scanField -> ExportFieldFactory.build(scanField.value(), scanField.type().raw(), namingCase))
                .collect(Collectors.toList());
    }

    @NotNull
    private List<ScanField> getExportFields(Type target) {
        if (target == null || Object.class.equals(target)) {
            return Collections.emptyList();
        }

        final Class<?> targetClass = (target instanceof ParameterizedType)
                ? (Class<?>) ((ParameterizedType) target).getRawType()
                : ((Class<?>) target);

        final List<ScanField> superFields = getExportFields(targetClass.getGenericSuperclass());
        final List<ScanField> targetFields = Arrays.stream(targetClass.getDeclaredFields())
                .filter(f -> !f.isSynthetic())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .filter(f -> !Modifier.isNative(f.getModifiers()))
                .filter(f -> !Modifier.isSynchronized(f.getModifiers()))
                .filter(fieldPredicate)
                .filter(f -> Arrays.stream(f.getDeclaredAnnotations())
                        .noneMatch(a -> GenExportIgnore.class.equals(a.annotationType())))
                .flatMap(f -> GenType.ofType(f.getGenericType())
                        .map(v -> Stream.of(new ScanField(f, v)))
                        .orElse(Stream.empty()))
                .collect(Collectors.toList());

        if (superFields.isEmpty()) {
            return targetFields;
        } else {
            superFields.addAll(targetFields);
            return superFields;
        }
    }
}
