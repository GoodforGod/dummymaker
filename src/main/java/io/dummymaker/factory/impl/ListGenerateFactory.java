package io.dummymaker.factory.impl;

import io.dummymaker.annotation.collection.GenList;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.collection.ICollectionGenerator;
import io.dummymaker.generator.impl.collection.impl.ListGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

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

    private static final Logger logger = Logger.getLogger(ListGenerateFactory.class.getName());

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
        return generate(field, annotation, new ListGenerator());
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
        try {
            if(field == null || annotation == null)
                return null;

            if(generator == null)
                return generate(field, annotation);

            if (!field.getType().isAssignableFrom(List.class))
                return null;

            int fixed = ((GenList) annotation).fixed();
            int min = ((GenList) annotation).min();
            int max = ((GenList) annotation).max();
            if (fixed > 0) {
                min = max = fixed;
            }

            final IGenerator valueGenerator = ((GenList) annotation).generator().newInstance();
            final Type fieldType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

            return generator.generate(valueGenerator, ((Class<?>) fieldType), min, max);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.warning(e.getMessage());
            return null;
        }
    }
}
