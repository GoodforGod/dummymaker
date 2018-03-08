package io.dummymaker.factory.impl;

import io.dummymaker.annotation.collection.GenMap;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.collection.IMapGenerator;
import io.dummymaker.generator.impl.collection.impl.MapGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Generates map object where
 * keys are generated as annotation keyGenerator specified type values
 * values are generated as annotation valueGenerator specified type values
 *
 * @see GenMap
 * @see io.dummymaker.factory.IGenerateFactory
 * @see BasicGenerateFactory
 *
 * @author GoodforGod
 * @since 08.03.2018
 */
public class MapGenerateFactory extends BasicGenerateFactory<IMapGenerator<?, ?>> {

    private static final Logger logger = Logger.getLogger(MapGenerateFactory.class.getName());

    public MapGenerateFactory() {
        super(GenMap.class);
    }

    /**
     * Method to produce fields value
     *
     * @see GenMap
     *
     * @param field field to populate
     * @param annotation fields annotations
     */
    @Override
    public Object generate(final Field field,
                           final Annotation annotation) {
        return generate(field, annotation, new MapGenerator());
    }

    /**
     * Method to produce fields value
     *
     * @see GenMap
     *
     * @param field field to populate
     * @param annotation fields annotations
     * @param generator map generator implementation
     */
    @Override
    public Object generate(final Field field,
                           final Annotation annotation,
                           final IMapGenerator<?, ?> generator) {
            try {
                if(field == null || annotation == null)
                    return null;

                // If nullable generator use default one
                if(generator == null)
                    return generate(field, annotation);

                if (!field.getType().isAssignableFrom(Map.class))
                    return null;

                int fixed = ((GenMap) annotation).fixed();
                int min = ((GenMap) annotation).min();
                int max = ((GenMap) annotation).max();
                if (fixed > 0) {
                    min = max = fixed;
                }

                final IGenerator keyGenerator = ((GenMap) annotation).key().newInstance();
                final IGenerator valueGenerator = ((GenMap) annotation).value().newInstance();
                final Type keyFieldType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                final Type valueFieldType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[1];

                return generator.generate(keyGenerator, valueGenerator,
                        ((Class<?>) keyFieldType), ((Class<?>) valueFieldType),
                        min, max);
            } catch (InstantiationException | IllegalAccessException e) {
                logger.warning(e.getMessage());
                return null;
            }
    }
}
