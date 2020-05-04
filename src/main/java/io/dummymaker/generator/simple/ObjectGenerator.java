package io.dummymaker.generator.simple;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates object as string like: object_1245155
 *
 * @author GoodforGod
 * @since 26.04.2018
 */
public class ObjectGenerator implements IGenerator<Object> {

    @Override
    public @NotNull Object generate() {
        return "object_" + CollectionUtils.random(Integer.MAX_VALUE);
    }
}
