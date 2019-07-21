package io.dummymaker.generator.complex;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.factory.IComplexService;
import io.dummymaker.generator.simple.IGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Complex Generator used by ComplexGen annotation to populate fields
 * When annotation have attributes or value generates for multiple field types
 *
 * @author GoodforGod
 * @see io.dummymaker.factory.IPopulateFactory
 * @see ComplexGen
 * @since 21.04.2018
 */
public interface IComplexGenerator extends IGenerator<Object> {

    Object generate(final Annotation annotation,
                    final Field field,
                    final IComplexService storage,
                    final int depth);
}
