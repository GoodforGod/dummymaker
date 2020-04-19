package io.dummymaker.generator.simple;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;

/**
 * Generates object as string like: object_1245155
 *
 * @author GoodforGod
 * @since 26.04.2018
 */
public class ObjectGenerator implements IGenerator<Object> {

    @Override
    public Object generate() {
        return "object_" + CollectionUtils.random(Integer.MAX_VALUE);
    }
}
