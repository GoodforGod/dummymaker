package io.dummymaker;

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

    static DefaultGenType ofClass(@NotNull Class<?> type) {
        return new DefaultGenType(type, type, Collections.emptyList());
    }

    static DefaultGenType ofType(@NotNull Type type) {
        if (type instanceof Class) {
            return new DefaultGenType(type, (Class<?>) type, Collections.emptyList());
        } else if (type instanceof TypeVariable) {
            if (((TypeVariable<?>) type).getGenericDeclaration() instanceof Class) {
                return new DefaultGenType(type, ((Class<?>) ((TypeVariable<?>) type).getGenericDeclaration()),
                        Collections.emptyList());
            } else {
                return new DefaultGenType(type, Object.class, Collections.emptyList());
            }
        } else if (type instanceof ParameterizedType) {
            final List<GenType> generics = Arrays.stream(((ParameterizedType) type).getActualTypeArguments())
                    .map(GenType::ofType)
                    .collect(Collectors.toList());

            return new DefaultGenType(type, ((Class<?>) ((ParameterizedType) type).getRawType()), generics);
        } else if (type instanceof WildcardType) {
            return new DefaultGenType(type, Object.class, Collections.emptyList());
        } else {
            return new DefaultGenType(type, (Class<?>) type, Collections.emptyList());
        }
    }

    static Optional<GenType> ofInterface(@NotNull Class<?> interfaceType) {
        if (!interfaceType.isInterface()) {
            return Optional.empty();
        }

        try {
            final Method permittedMethod = Class.class.getMethod("getPermittedSubclasses");
            final Class<?>[] permitted = (Class<?>[]) permittedMethod.invoke(interfaceType);
            return Arrays.stream(permitted)
                    .filter(c -> !c.isInterface())
                    .map(GenType::ofClass)
                    .findFirst();
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
        flat.add(GenType.ofClass(plainRawType()));

        generics.stream()
                .flatMap(type -> type.flatten().stream())
                .forEach(flat::add);

        return flat;
    }

    @Override
    public @NotNull Class<?> raw() {
        return value;
    }

    @Override
    public @NotNull Type type() {
        return type;
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
                    .map(Object::toString)
                    .collect(Collectors.joining(", ", value.getSimpleName() + "<", ">"));
        }
    }
}
