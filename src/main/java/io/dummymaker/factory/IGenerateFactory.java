package io.dummymaker.factory;

import io.dummymaker.generator.IGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Generate factory used by populate factory to produce complex values
 *
 * @author GoodforGod
 * @since 08.03.2018
 */
public interface IGenerateFactory<T extends IGenerator> {

    Object generate(final Field field,
                    final Annotation annotation);

    Object generate(final Field field,
                    final Annotation annotation,
                    final T generator);
}
