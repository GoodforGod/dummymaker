package io.dummymaker.scan.container;

import java.lang.annotation.Annotation;

/**
 * Container with prime annotation and its child gen annotation
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
     */
    private final Annotation prime;

    /**
     * Its child gen annotation like:
     *
     * @see io.dummymaker.annotation.string.GenString
     * @see io.dummymaker.annotation.time.GenLocalDate
     */
    private final Annotation gen;

    public PopulateContainer(final Annotation prime,
                             final Annotation gen) {
        this.prime = prime;
        this.gen = gen;
    }

    public Annotation getPrime() {
        return prime;
    }

    public Annotation getGen() {
        return gen;
    }
}
