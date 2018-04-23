package io.dummymaker.bundle;

import io.dummymaker.annotation.simple.number.GenInteger;
import io.dummymaker.annotation.special.GenEmbedded;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 23.04.2018
 */
public class DummyEmb {

    @GenInteger
    private int anInt;

    @GenEmbedded(depth = 4)
    private DummyEmb emb;
}
