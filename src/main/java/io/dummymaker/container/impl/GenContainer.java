package io.dummymaker.container.impl;

import io.dummymaker.annotation.ComplexGen;

import java.lang.annotation.Annotation;

/**
 * Container with core annotation and its child gen annotation
 * Used by populate scanners and factories
 *
 * @see io.dummymaker.scan.IPopulateScanner
 * @see io.dummymaker.annotation.PrimeGen
 *
 * @author GoodforGod
 * @since 10.03.2018
 */
public class GenContainer {

    /**
     * @see io.dummymaker.annotation.PrimeGen
     * @see ComplexGen
     */
    private final Annotation core;

    /**
     * Its child gen annotation like:
     *
     * @see io.dummymaker.annotation.simple.string.GenString
     * @see io.dummymaker.annotation.simple.time.GenLocalDate
     */
    private final Annotation gen;

    public GenContainer(final Annotation core,
                        final Annotation gen) {
        this.core = core;
        this.gen = gen;
    }

    public Annotation getCore() {
        return core;
    }

    public Annotation getGen() {
        return gen;
    }
}
