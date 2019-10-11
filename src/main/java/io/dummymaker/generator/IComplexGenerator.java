package io.dummymaker.generator;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.factory.IGenStorage;

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

    /**
     * Generates object for field marked by complex annotation
     *
     * @param annotation field was marked by
     * @param field      for which value is generated
     * @param storage    factory storage with generators and embedded entity production
     * @param depth      current depth of the generated field
     * @param parent     parent object class
     * @return generated object
     */
    Object generate(Class<?> parent, Field field, IGenStorage storage, Annotation annotation, int depth);
}
