package io.dummymaker.factory;

import io.dummymaker.generator.simple.IGenerator;

import java.lang.reflect.Field;

/**
 * Config that providers contract for generators discovery
 * Based on fields and classes for auto generation
 *
 * @see io.dummymaker.annotation.special.GenAuto
 *
 * @author GoodforGod
 * @since 27.07.2019
 */
public interface IGenSupplier {

    Class<? extends IGenerator> getSuitable(Field field);

    Class<? extends IGenerator> getSuitable(Field field, Class<?> fieldClass);
}
