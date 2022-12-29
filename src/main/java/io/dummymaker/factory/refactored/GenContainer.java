package io.dummymaker.factory.refactored;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.generator.simple.SequenceGenerator;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Container with core annotation and its child marker annotation Used by populate scanners and
 * factories
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenCustom
 * @since 24.11.2022
 */
public final class GenContainer {

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

    public static GenContainer ofMarker(Field field, Generator<?> generator, int depth, boolean isComplex, Annotation marker) {
        return new GenContainer(field, getGenType(field), marker, isComplex, false, depth, generator);
    }

    public static GenContainer ofRule(Field field, Generator<?> generator, int depth, boolean isComplex) {
        return new GenContainer(field, getGenType(field), null, isComplex, false, depth, generator);
    }

    public static GenContainer ofAuto(Field field, Generator<?> generator, int depth, boolean isComplex, @Nullable Annotation marker) {
        return new GenContainer(field, getGenType(field), marker, isComplex, true, depth, generator);
    }

    private static GenType getGenType(Field field) {
        return SimpleGenType.ofField(field);
    }

    public boolean isEmbedded() {
        return generator instanceof EmbeddedGenerator;
    }

    public Generator<?> getGenerator() {
        return generator;
    }

    public boolean isComplex() {
        return isComplex;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public boolean isSequence() {
        return generator instanceof SequenceGenerator;
    }

    public int getDepth() {
        return depth;
    }

    public Field getField() {
        return field;
    }

    public GenType getType() {
        return type;
    }

    public Annotation getMarker() {
        return marker;
    }
}
