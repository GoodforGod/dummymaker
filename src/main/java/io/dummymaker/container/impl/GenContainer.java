package io.dummymaker.container.impl;

import io.dummymaker.annotation.ComplexGen;
import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.annotation.simple.string.GenString;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.scan.IPopulateScanner;

import java.lang.annotation.Annotation;

/**
 * Container with core annotation and its child marker annotation
 * Used by populate scanners and factories
 *
 * @see IPopulateScanner
 *
 * @see PrimeGen
 * @see ComplexGen
 *
 * @author GoodforGod
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
    private final boolean isAuto;

    private final Class<? extends IGenerator> generatorClass;

    private GenContainer(final Annotation core,
                         final Annotation marker,
                         final boolean isComplex,
                         final boolean isAuto,
                         final Class<? extends IGenerator> generatorClass) {
        this.marker = marker;
        this.core = core;
        this.isAuto = isAuto;
        this.isComplex = isComplex;

        if (generatorClass != null) {
            this.generatorClass = generatorClass;
        } else {
            this.generatorClass = (isComplex)
                    ? ((ComplexGen) core).value()
                    : ((PrimeGen) core).value();
        }
    }

    public static GenContainer asGen(final Annotation core,
                                     final Annotation marker) {
        return new GenContainer(core,
                marker,
                ComplexGen.class.equals(core.annotationType()),
                false,
                null);
    }

    public static GenContainer asAuto(final Class<? extends IGenerator> generatorClass,
                                      final boolean isComplex) {
        return new GenContainer(null,
                null,
                isComplex,
                true,
                generatorClass);
    }



    public Class<? extends IGenerator> getGeneratorClass() {
        return generatorClass;
    }

    public boolean isAuto() {
        return isAuto;
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
}
