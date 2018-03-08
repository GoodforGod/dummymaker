package io.dummymaker.factory;

import io.dummymaker.generator.IGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 08.03.2018
 */
public interface IGenerateFactory<T extends IGenerator> {

    boolean isSuitable(final Annotation annotation);

    Annotation findSuitable(final List<Annotation> annotations);

    Object generate(final Field field,
                    final Annotation annotation);

    Object generate(final Field field,
                    final Annotation annotation,
                    final T generator);
}
