package io.dummymaker.container.impl;

import java.lang.annotation.Annotation;

/**
 * Container with core annotation and its child gen annotation
 * Used by populate scanners
 *
 * @see io.dummymaker.scan.IPopulateScanner
 * @see io.dummymaker.annotation.PrimeGen
 *
 * @author GoodforGod
 * @since 10.03.2018
 */
public class PopulateContainer {

    /**
     * @see io.dummymaker.annotation.PrimeGen
     * @see io.dummymaker.annotation.ComplexGen
     */
    private final Annotation core;

    /**
     * Its child gen annotation like:
     *
     * @see io.dummymaker.annotation.string.GenString
     * @see io.dummymaker.annotation.time.GenLocalDate
     */
    private final Annotation gen;

    public PopulateContainer(final Annotation core,
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
