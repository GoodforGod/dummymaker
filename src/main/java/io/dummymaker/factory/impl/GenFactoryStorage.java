package io.dummymaker.factory.impl;

import io.dummymaker.generator.simple.IGenerator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 17.07.2019
 */
class GenFactoryStorage {

    private final Map<Field, IGenerator> sequantialGenerators = new HashMap<>();
}
