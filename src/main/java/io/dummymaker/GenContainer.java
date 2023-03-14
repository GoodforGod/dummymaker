package io.dummymaker;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.jetbrains.annotations.Nullable;

/**
 * Container with core annotation and its child marker annotation Used by populate scanners and
 * factories
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenCustom
 * @since 24.11.2022
 */
final class GenContainer {

    private final Field field;
    private final GenType type;
    private final Annotation marker;
    private final boolean isComplex;
    private final boolean isAuto;
    private final int depth;
    private final Generator<?> generator;

    private GenContainer(Field field,
                         GenType type,
                         Annotation marker,
                         boolean isComplex,
                         boolean isAuto,
                         int depth,
                         Generator<?> generator) {
        this.field = field;
        this.marker = marker;
        this.type = type;
        this.isComplex = isComplex;
        this.isAuto = isAuto;
        this.depth = depth;
        this.generator = generator;
    }

    static GenContainer ofMarker(Field field,
                                 GenType type,
                                 Generator<?> generator,
                                 int depth,
                                 boolean isComplex,
                                 Annotation marker) {
        return new GenContainer(field, type, marker, isComplex, false, depth, generator);
    }

    static GenContainer ofRule(Field field,
                               GenType type,
                               Generator<?> generator,
                               int depth,
                               boolean isComplex) {
        return new GenContainer(field, type, null, isComplex, false, depth, generator);
    }

    static GenContainer ofAuto(Field field,
                               GenType type,
                               Generator<?> generator,
                               int depth,
                               boolean isComplex,
                               @Nullable Annotation marker) {
        return new GenContainer(field, type, marker, isComplex, true, depth, generator);
    }

    boolean isEmbedded() {
        return generator instanceof EmbeddedGenerator;
    }

    Generator<?> getGenerator() {
        return generator;
    }

    boolean isComplex() {
        return isComplex;
    }

    boolean isAuto() {
        return isAuto;
    }

    int depth() {
        return depth;
    }

    Field field() {
        return field;
    }

    GenType type() {
        return type;
    }

    Annotation marker() {
        return marker;
    }
}
