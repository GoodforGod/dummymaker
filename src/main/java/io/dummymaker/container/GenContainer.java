package io.dummymaker.container;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.annotation.special.GenCustom;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.EmbeddedGenerator;
import io.dummymaker.generator.simple.impl.NullGenerator;

import java.lang.annotation.Annotation;

/**
 * Container with core annotation and its child marker annotation
 * Used by populate scanners and factories
 *
 * @author GoodforGod
 * @see PrimeGen
 * @see ComplexGen
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

    /**
     * Its child marker annotation like GenString, etc
     */
    private final Annotation marker;
    private final boolean isComplex;
    private final boolean isAuto;
    private final int autoDepth;

    /**
     * Field set generator
     * Can be enriched
     */
    private Class<? extends IGenerator> generator;

    private GenContainer(final Annotation core,
                         final Annotation marker,
                         final boolean isComplex,
                         final boolean isAuto,
                         final int autoDepth,
                         final Class<? extends IGenerator> generator) {
        this.marker = marker;
        this.core = core;
        this.isComplex = isComplex;
        this.isAuto = isAuto;
        this.autoDepth = autoDepth;

        if (generator != null) {
            this.generator = generator;
        } else {
            if (core == null) {
                this.generator = NullGenerator.class;
            } else {
                this.generator = (isComplex)
                        ? ((ComplexGen) core).value()
                        : ((PrimeGen) core).value();
            }
        }
    }

    public static GenContainer asCustom(Annotation marker) {
        final Class<? extends IGenerator> generator = ((GenCustom) marker).value();
        final boolean isComplex = generator.isAssignableFrom(IComplexGenerator.class);
        return new GenContainer(null, marker, isComplex, false, 1, generator);
    }

    public static GenContainer asGen(Annotation core, Annotation marker) {
        final boolean isComplex = ComplexGen.class.equals(core.annotationType());
        return new GenContainer(core, marker, isComplex, false, 1, null);
    }

    public static GenContainer asAuto(final Class<? extends IGenerator> generator,
                                      final boolean isComplex,
                                      final int depth) {
        final int parsedDepth = (depth < 1) ? 1 : Math.min(depth, GenEmbedded.MAX);
        return new GenContainer(null, null, isComplex, true, parsedDepth, generator);
    }

    public static GenContainer asAuto(boolean isComplex, int depth) {
        return asAuto(null, isComplex, depth);
    }

    public boolean isEmbedded() {
        return generator != null && generator.equals(EmbeddedGenerator.class);
    }

    public void enrich(Class<? extends IGenerator> generator) {
        this.generator = generator;
    }

    public Class<? extends IGenerator> getGenerator() {
        return generator;
    }

    public boolean isComplex() {
        return isComplex;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public Annotation getCore() {
        return core;
    }

    public Annotation getMarker() {
        return marker;
    }

    public int getAutoDepth() {
        return autoDepth;
    }
}
