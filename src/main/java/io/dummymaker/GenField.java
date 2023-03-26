package io.dummymaker;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import java.lang.reflect.Field;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;

/**
 * Container with core annotation and its child marker annotation Used by populate scanners and
 * factories
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenCustom
 * @since 24.11.2022
 */
final class GenField {

    private final Field field;
    private final GenType type;
    private final boolean isComplex;
    private final Generator<?> generator;
    @Nullable
    private final Integer depth;

    private GenField(Field field, GenType type, boolean isComplex, Generator<?> generator, Integer depth) {
        this.field = field;
        this.type = type;
        this.isComplex = isComplex;
        this.generator = generator;
        this.depth = depth;
    }

    static GenField ofMarker(Field field,
                             GenType type,
                             Generator<?> generator,
                             boolean isComplex,
                             @Nullable Integer depth) {
        return new GenField(field, type, isComplex, generator, depth);
    }

    static GenField ofRule(Field field,
                           GenType type,
                           Generator<?> generator,
                           boolean isComplex,
                           @Nullable Integer depth) {
        return new GenField(field, type, isComplex, generator, depth);
    }

    static GenField ofAuto(Field field,
                           GenType type,
                           Generator<?> generator,
                           boolean isComplex,
                           Integer depth) {
        return new GenField(field, type, isComplex, generator, depth);
    }

    boolean isEmbedded() {
        return generator instanceof EmbeddedGenerator;
    }

    boolean isComplex() {
        return isComplex;
    }

    Generator<?> generator() {
        return generator;
    }

    String name() {
        return field.getName();
    }

    Object getDefaultValue(Object instance) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(instance);
    }

    void setValue(Object instance, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(instance, value);
    }

    GenType type() {
        return type;
    }

    Optional<Integer> depth() {
        return Optional.ofNullable(depth);
    }
}
