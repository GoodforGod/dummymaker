package io.dummymaker.export;

import io.dummymaker.GenType;
import io.dummymaker.annotation.export.GenExportForce;
import io.dummymaker.annotation.export.GenExportIgnore;
import io.dummymaker.annotation.export.GenExportName;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

    private static final ExportFieldFactory FACTORY = new ExportFieldFactory();

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

    @NotNull
    public List<ExportField> scan(Class<?> target) {
        return getExportFields(target).stream()
                .filter(scanField -> Arrays.stream(scanField.value().getDeclaredAnnotations())
                        .noneMatch(a -> GenExportIgnore.class.equals(a.annotationType())))
                .map(scanField -> FACTORY.build(scanField.value(), scanField.type()))
                .collect(Collectors.toList());
    }

    @NotNull
    private List<ScanField> getExportFields(Type target) {
        if (target == null || Object.class.equals(target)) {
            return new ArrayList<>();
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
                .flatMap(f -> {
                    if (f.getGenericType() instanceof TypeVariable && target instanceof ParameterizedType) {
                        final TypeVariable<? extends Class<?>>[] typeParameters = targetClass.getTypeParameters();
                        for (int i = 0; i < typeParameters.length; i++) {
                            if (typeParameters[i].getTypeName().equals(f.getGenericType().getTypeName())) {
                                return Stream.of(new ScanField(f,
                                        GenType.ofType(((ParameterizedType) target).getActualTypeArguments()[i])));
                            }
                        }
                    }

                    return Stream.of(new ScanField(f, GenType.ofType(f.getGenericType())));
                })
                .collect(Collectors.toList());

        superFields.addAll(targetFields);
        return superFields;
    }
}
