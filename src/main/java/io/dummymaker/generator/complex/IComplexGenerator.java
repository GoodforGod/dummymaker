package io.dummymaker.generator.complex;

import io.dummymaker.generator.IGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Complex Generator used by ComplexGen annotation to populate fields
 * When annotation have attributes or value generates for multiple field types
 *
 * @see io.dummymaker.factory.IPopulateFactory
 * @see io.dummymaker.annotation.ComplexGen
 *
 * @author GoodforGod
 * @since 21.04.2018
 */
public interface IComplexGenerator extends IGenerator {

    Object generate(final Annotation annotation,
                    final Field field);
}
