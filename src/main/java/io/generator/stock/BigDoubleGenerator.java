package io.generator.stock;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class BigDoubleGenerator extends DoubleGenerator {

    public Double generate() {
        return super.generate() * 1000000;
    }

}
