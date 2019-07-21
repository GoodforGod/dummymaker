package io.dummymaker.container.impl;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.annotation.simple.string.GenString;
import io.dummymaker.annotation.special.GenCustom;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.NullGenerator;
import io.dummymaker.scan.IPopulateScanner;

import java.lang.annotation.Annotation;

/**
 * Container with core annotation and its child marker annotation
 * Used by populate scanners and factories
 *
 * @author GoodforGod
 * @see IPopulateScanner
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
     * Its child marker annotation like:
     *
     * @see GenString
     */
    private final Annotation marker;
    private final boolean isComplex;
    private final int autoDepth;

    private final Class<? extends IGenerator> generator;

    private GenContainer(final Annotation core,
                         final Annotation marker,
                         final boolean isComplex,
                         final int autoDepth,
                         final Class<? extends IGenerator> generator) {
        this.marker = marker;
        this.core = core;
        this.isComplex = isComplex;
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

    public static GenContainer asCustom(final Annotation marker) {
        final Class<? extends IGenerator> generatorClass = ((GenCustom) marker).value();
        final boolean isComplex = generatorClass.isAssignableFrom(IComplexGenerator.class);
        return new GenContainer(null, marker, isComplex, 1, generatorClass);
    }

    public static GenContainer asGen(final Annotation core,
                                     final Annotation marker) {
        final boolean isComplex = ComplexGen.class.equals(core.annotationType());
        return new GenContainer(core, marker, isComplex, 1, null);
    }

    public static GenContainer asAuto(final Class<? extends IGenerator> generatorClass,
                                      final boolean isComplex,
                                      final int autoDepth) {
        final int parsedDepth = (autoDepth < 1) ? 1 : (autoDepth > GenEmbedded.MAX) ? GenEmbedded.MAX : autoDepth;
        return new GenContainer(null, null, isComplex, parsedDepth, generatorClass);
    }

    public Class<? extends IGenerator> getGenerator() {
        return generator;
    }

    public boolean isComplex() {
        return isComplex;
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
