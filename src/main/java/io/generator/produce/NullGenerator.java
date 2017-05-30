package io.generator.produce;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public class NullGenerator implements IGenerator<Object> {

    @Override
    public Object generate() {
        return null;
    }
}
