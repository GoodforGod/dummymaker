package io.dummymaker.factory.refactored;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
public interface GenContext {

    int depthMax();

    int depthCurrent();

    GenNode graph();
}
