package io.dummymaker;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 18.03.2023
 */
public final class GenFieldScanner {

    private GenFieldScanner() {}

    public static class GenField {

        private final Field field;
        private final GenType type;

        private GenField(Field field, GenType type) {
            this.field = field;
            this.type = type;
        }

        public Field field() {
            return field;
        }

        public GenType type() {
            return type;
        }
    }

    @NotNull
    public static List<GenField> scan(Type target) {
        if (target == null || Object.class.equals(target)) {
            return new ArrayList<>();
        }

        final Class<?> targetClass = (target instanceof ParameterizedType)
                ? (Class<?>) ((ParameterizedType) target).getRawType()
                : ((Class<?>) target);

        final List<GenField> superFields = scan(targetClass.getGenericSuperclass());
        final List<GenField> targetFields = Arrays.stream(targetClass.getDeclaredFields())
                .filter(f -> !f.isSynthetic())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .filter(f -> !Modifier.isNative(f.getModifiers()))
                .filter(f -> !Modifier.isSynchronized(f.getModifiers()))
                .filter(f -> !Modifier.isFinal(f.getModifiers()))
                .flatMap(f -> {
                    if (f.getGenericType() instanceof TypeVariable && target instanceof ParameterizedType) {
                        final TypeVariable<? extends Class<?>>[] typeParameters = targetClass.getTypeParameters();
                        for (int i = 0; i < typeParameters.length; i++) {
                            if (typeParameters[i].getTypeName().equals(f.getGenericType().getTypeName())) {
                                return Stream.of(new GenField(f,
                                        SimpleGenType.ofType(((ParameterizedType) target).getActualTypeArguments()[i])));
                            }
                        }
                    }

                    return Stream.of(new GenField(f, SimpleGenType.ofType(f.getGenericType())));
                })
                .collect(Collectors.toList());

        superFields.addAll(targetFields);
        return superFields;
    }
}
