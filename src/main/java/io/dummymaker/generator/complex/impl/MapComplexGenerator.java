package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenMap;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.dummymaker.util.BasicCastUtils.getGenericType;
import static io.dummymaker.util.BasicCollectionUtils.generateRandomAmount;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 22.04.2018
 */
public class MapComplexGenerator extends BasicComplexGenerator {

    public MapComplexGenerator() {
        super(new IdGenerator());
    }

    private Map generateMap(final int amount,
                            final Class<? extends IGenerator> keyGenerator,
                            final Class<? extends IGenerator> valueGenerator,
                            final Class<?> keyFieldType,
                            final Class<?> valueFieldType) {

        // Firstly try to generate initial object, so we won't allocate list if not necessary
        final Object initialKey     = generateValue(keyGenerator, keyFieldType);
        final Object initialValue   = generateValue(valueGenerator, valueFieldType);
        if(initialKey == null && initialValue == null)
            return Collections.emptyMap();

        final Map map = new HashMap<>(amount);
        map.put(initialKey, initialValue);
        for (int i = 0; i < amount - 1; i++) {
            final Object key    = generateValue(keyGenerator, keyFieldType);
            final Object value  = generateValue(valueGenerator, valueFieldType);

            if (key != null && value != null) {
                map.put(key, value);
            }
        }

        return map;
    }

    @Override
    public Object generate(final Annotation annotation,
                           final Field field) {
        if (field == null || annotation == null || !field.getType().isAssignableFrom(Map.class))
            return null;

        final GenMap a = ((GenMap) annotation);
        final Class<? extends IGenerator> keyGenerator   = a.key();
        final Class<? extends IGenerator> valueGenerator = a.value();

        final Type keyType   = getGenericType(field, 0);
        final Type valueType = getGenericType(field, 1);

        final int amount = generateRandomAmount(a.min(), a.max(), a.fixed()); // due to initial object

        return generateMap(amount, keyGenerator, valueGenerator, ((Class<?>) keyType), ((Class<?>) valueType));
    }

    @Override
    public Object generate() {
        return null;
    }
}
