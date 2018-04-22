package io.dummymaker.generator.complex;

import io.dummymaker.annotation.ComplexGen;
import io.dummymaker.generator.simple.IGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Complex Generator used by ComplexGen annotation to populate fields
 * When annotation have attributes or value generates for multiple field types
 *
 * @see io.dummymaker.factory.IPopulateFactory
 * @see ComplexGen
 *
 * @author GoodforGod
 * @since 21.04.2018
 */
public interface IComplexGenerator extends IGenerator<Object> {

    Object generate(final Annotation annotation,
                    final Field field);
}
