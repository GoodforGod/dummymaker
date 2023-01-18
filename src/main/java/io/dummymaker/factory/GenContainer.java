package io.dummymaker.factory;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.parameterized.SequenceParameterizedGenerator;
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

    static GenContainer ofMarker(Field field, Generator<?> generator, int depth, boolean isComplex, Annotation marker) {
        return new GenContainer(field, getGenType(field), marker, isComplex, false, depth, generator);
    }

    static GenContainer ofRule(Field field, Generator<?> generator, int depth, boolean isComplex) {
        return new GenContainer(field, getGenType(field), null, isComplex, false, depth, generator);
    }

    static GenContainer ofAuto(Field field,
                               Generator<?> generator,
                               int depth,
                               boolean isComplex,
                               @Nullable Annotation marker) {
        return new GenContainer(field, getGenType(field), marker, isComplex, true, depth, generator);
    }

    private static GenType getGenType(Field field) {
        return SimpleGenType.ofField(field);
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

    boolean isSequence() {
        return generator instanceof SequenceParameterizedGenerator;
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
