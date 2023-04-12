package io.goodforgod.dummymaker;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 30.11.2022
 */
final class DefaultGenType implements GenType {

    private final Type type;
    private final Class<?> value;
    private final List<GenType> generics;

    private DefaultGenType(Type type, Class<?> value, List<GenType> generics) {
        this.type = type;
        this.value = value;
        this.generics = generics;
    }

    static Optional<GenType> ofInstantiatable(@NotNull Class<?> type) {
        final Optional<GenType> sealedClass = ofSealed(type);
        if (sealedClass.isPresent()) {
            return sealedClass;
        }

        if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
            return Optional.empty();
        }

        return Optional.of(new DefaultGenType(type, type, Collections.emptyList()));
    }

    static GenType ofClass(@NotNull Class<?> type) {
        final Optional<GenType> sealedClass = ofSealed(type);
        return sealedClass.orElseGet(() -> new DefaultGenType(type, type, Collections.emptyList()));
    }

    static Optional<GenType> ofType(@NotNull Type type) {
        if (type instanceof Class) {
            return Optional.of(ofClass((Class<?>) type));
        } else if (type instanceof TypeVariable) {
            if (((TypeVariable<?>) type).getGenericDeclaration() instanceof Class) {
                return Optional.of(new DefaultGenType(type,
                        ((Class<?>) ((TypeVariable<?>) type).getGenericDeclaration()),
                        Collections.emptyList()));
            } else if (((TypeVariable<?>) type).getGenericDeclaration() instanceof WildcardType) {
                return Optional.of(new DefaultGenType(type, Object.class, Collections.emptyList()));
            } else {
                return Optional.empty();
            }
        } else if (type instanceof ParameterizedType) {
            final List<GenType> generics = Arrays.stream(((ParameterizedType) type).getActualTypeArguments())
                    .map(GenType::ofType)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            return Optional.of(new DefaultGenType(type,
                    ((Class<?>) ((ParameterizedType) type).getRawType()),
                    generics));
        } else if (type instanceof WildcardType) {
            return Optional.of(new DefaultGenType(type, Object.class, Collections.emptyList()));
        } else {
            return Optional.empty();
        }
    }

    private static Optional<GenType> ofSealed(@NotNull Class<?> type) {
        try {
            final Method permittedMethod = Class.class.getMethod("getPermittedSubclasses");
            final Class<?>[] permitted = (Class<?>[]) permittedMethod.invoke(type);
            return Arrays.stream(permitted)
                    .map(DefaultGenType::ofType)
                    .findFirst()
                    .flatMap(v -> v);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Class<?> plainRawType() {
        final Class<?> raw = raw();
        if (raw.getTypeName().endsWith("[][]")) {
            return raw.getComponentType().getComponentType();
        } else if (raw.getTypeName().endsWith("[]")) {
            return raw.getComponentType();
        } else {
            return raw;
        }
    }

    @Override
    public @NotNull List<GenType> flatten() {
        final List<GenType> flat = new ArrayList<>();
        flat.add(ofClass(plainRawType()));

        generics.stream()
                .flatMap(t -> t.flatten().stream())
                .forEach(flat::add);

        return flat;
    }

    @Override
    public @NotNull Class<?> raw() {
        return value;
    }

    @Override
    public @NotNull List<GenType> generics() {
        return generics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DefaultGenType))
            return false;
        DefaultGenType that = (DefaultGenType) o;
        return Objects.equals(value, that.value) && Objects.equals(generics, that.generics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, generics);
    }

    @Override
    public String toString() {
        if (generics.isEmpty()) {
            return value.getSimpleName();
        } else {
            return generics.stream()
                    .map(GenType::toString)
                    .collect(Collectors.joining(", ", value.getSimpleName() + "<", ">"));
        }
    }
}
