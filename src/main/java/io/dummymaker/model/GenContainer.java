package io.dummymaker.model;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.ComplexGenerator;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.generator.simple.NullGenerator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Container with core annotation and its child marker annotation Used by populate scanners and
 * factories
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenCustom
 * @since 10.03.2018
 */
public class GenContainer {

    /**
     * Core annotation one of those two:
     *
     * @see PrimeGen
     * @see ComplexGen
     */
    private final Annotation core;

    private final Field field;

    /**
     * Its child marker annotation like GenString, etc
     */
    private final Annotation marker;
    private final boolean isComplex;
    private final boolean isAuto;

    /**
     * Field set generator Can be enriched
     */
    private final Class<? extends Generator> generator;
    private final Generator<?> generatorExample;

    private GenContainer(final Field field,
                         final Annotation core,
                         final Annotation marker,
                         final boolean isComplex,
                         final boolean isAuto,
                         final Class<? extends Generator> generator,
                         final Generator<?> generatorExample) {
        this.field = field;
        this.marker = marker;
        this.core = core;
        this.isComplex = isComplex;
        this.isAuto = isAuto;
        this.generatorExample = generatorExample;

        if (generator != null) {
            this.generator = generator;
        } else {
            if (core == null) {
                this.generator = NullGenerator.class;
            } else {
                this.generator = ((GenCustom) core).value();
            }
        }
    }

    public static GenContainer asCustom(Field field, Annotation marker) {
        final Class<? extends Generator> generator = ((GenCustom) marker).value();
        final boolean isComplex = generator.isAssignableFrom(ComplexGenerator.class);
        return new GenContainer(field, null, marker, isComplex, false, generator, null);
    }

    public static GenContainer asGen(Field field, Annotation core, Annotation marker) {
        final boolean isComplex = GenCustom.class.equals(core.annotationType());
        return new GenContainer(field, core, marker, isComplex, false, null, null);
    }

    public static GenContainer asAuto(Field field, Class<? extends Generator> generator, boolean isComplex) {
        return new GenContainer(field, null, null, isComplex, true, generator, null);
    }

    public static GenContainer asExample(Field field, Generator<?> generator, boolean isComplex) {
        return new GenContainer(field, null, null, isComplex, true, null, generator);
    }

    public boolean isEmbedded() {
        return EmbeddedGenerator.class.equals(generator);
    }

    public Class<? extends Generator> getGenerator() {
        return generator;
    }

    public Generator<?> getGeneratorExample() {
        return generatorExample;
    }

    public boolean haveGeneratorExample() {
        return generatorExample != null;
    }

    public boolean isComplex() {
        return isComplex;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public Field getField() {
        return field;
    }

    public Annotation getCore() {
        return core;
    }

    public Annotation getMarker() {
        return marker;
    }
}
