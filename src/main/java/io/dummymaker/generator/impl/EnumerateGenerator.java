package io.dummymaker.generator.impl;

import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.generator.IGenerator;

/**
 * Used like a dummy generator for @GenEnumerate
 *
 * @see GenEnumerate
 *
 * @author GoodforGod
 * @since 07.06.2017
 */
public class EnumerateGenerator implements IGenerator<Object> {

    @Override
    public Object generate() {
        return null;
    }
}
