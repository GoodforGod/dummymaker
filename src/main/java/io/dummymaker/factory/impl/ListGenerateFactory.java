package io.dummymaker.factory.impl;

import io.dummymaker.annotation.collection.GenList;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.collection.ICollectionGenerator;
import io.dummymaker.generator.impl.collection.impl.ListGenerator;
import io.dummymaker.util.BasicCastUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Generates list object filled with annotation generator specified type values
 *
 * @see GenList
 * @see io.dummymaker.factory.IGenerateFactory
 * @see BasicGenerateFactory
 *
 * @author GoodforGod
 * @since 08.03.2018
 */
public class ListGenerateFactory extends BasicGenerateFactory<ICollectionGenerator<?>> {

    private ICollectionGenerator<?> generator;

    public ListGenerateFactory() {
        super(GenList.class);
    }

    /**
     * Method to produce fields value
     *
     * @see GenList
     *
     * @param field field to populate
     * @param annotation fields annotations
     */
    @Override
    public Object generate(final Field field,
                           final Annotation annotation) {

        // lazy initialization and reuse of generator
        if(this.generator == null)
            this.generator = new ListGenerator();

        return generate(field, annotation, this.generator);
    }

    /**
     * Method to produce fields value
     *
     * @see GenList
     *
     * @param field field to populate
     * @param annotation fields annotations
     */
    @Override
    public Object generate(final Field field,
                           final Annotation annotation,
                           final ICollectionGenerator<?> generator) {
        if (field == null || annotation == null || !field.getType().isAssignableFrom(List.class))
            return null;

        if (generator == null)
            return generate(field, annotation);

        int fixed = ((GenList) annotation).fixed();
        int min = ((GenList) annotation).min();
        int max = ((GenList) annotation).max();
        if (fixed > 0) {
            min = max = fixed;
        }

        final IGenerator valueGenerator = BasicCastUtils.instantiate(((GenList) annotation).value());

        final Type fieldType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        return generator.generate(valueGenerator, ((Class<?>) fieldType), min, max);
    }
}
