package io.dummymaker.container.impl;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.BooleanGenerator;
import io.dummymaker.generator.simple.impl.NullGenerator;
import io.dummymaker.generator.simple.impl.UuidGenerator;
import io.dummymaker.generator.simple.impl.collection.impl.ListGenerator;
import io.dummymaker.generator.simple.impl.collection.impl.MapGenerator;
import io.dummymaker.generator.simple.impl.collection.impl.SetGenerator;
import io.dummymaker.generator.simple.impl.number.DoubleBigGenerator;
import io.dummymaker.generator.simple.impl.number.DoubleGenerator;
import io.dummymaker.generator.simple.impl.number.IntegerGenerator;
import io.dummymaker.generator.simple.impl.number.LongGenerator;
import io.dummymaker.generator.simple.impl.string.*;
import io.dummymaker.generator.simple.impl.time.impl.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.dummymaker.util.BasicCastUtils.getGenericType;
import static io.dummymaker.util.BasicCastUtils.instantiate;
import static io.dummymaker.util.BasicCollectionUtils.isEmpty;
import static java.util.Collections.singletonList;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 25.04.2018
 */
public class GeneratorsStorage {

    private final IGenerator nullGenerator = new NullGenerator();
    private final int salt = ((Long) System.currentTimeMillis()).hashCode();

    private final Map<Class<? extends IGenerator>, IGenerator> generators = new HashMap<>();

    private static final Map<Class, List<Class<? extends IGenerator>>> generatorsClassMap;

    static {
        generatorsClassMap = instantiateGenerators();
    }

    private static Map<Class, List<Class<? extends IGenerator>>> instantiateGenerators() {

        final Map<Class, List<Class<? extends IGenerator>>> collectedGenerators = Stream.of(
                DoubleBigGenerator.class,
                DoubleGenerator.class,
                IntegerGenerator.class,
                LongGenerator.class,

                // Strings
                CityGenerator.class,
                CompanyGenerator.class,
                CountryGenerator.class,
                EmailGenerator.class,
                IdBigGenerator.class,
                IdGenerator.class,
                JsonGenerator.class,
                NameGenerator.class,
                NickGenerator.class,
                NounGenerator.class,
                PassGenerator.class,
                PhoneGenerator.class,
                PhraseGenerator.class,
                StringGenerator.class,
                TagGenerator.class,

                // Time
                DateGenerator.class,
                LocalDateGenerator.class,
                LocalTimeGenerator.class,
                TimestampGenerator.class,
                LocalDateTimeGenerator.class,

                // Special
                BooleanGenerator.class,
                UuidGenerator.class
        ).collect(Collectors.groupingBy(
                GeneratorsStorage::extractGenericType
        ));

        collectedGenerators.put(List.class, singletonList(ListGenerator.class));
        collectedGenerators.put(Set.class, singletonList(SetGenerator.class));
        collectedGenerators.put(Map.class, singletonList(MapGenerator.class));

        return collectedGenerators;
    }

    private static Class<?> extractGenericType(Class<? extends IGenerator> generator) {
        return (generator.getGenericInterfaces().length == 0)
                ? ((Class<?>) getGenericType(((Class<?>) generator.getGenericSuperclass()).getGenericInterfaces()[0]))
                : ((Class<?>) getGenericType(generator.getGenericInterfaces()[0]));
    }

    public IGenerator getRandomGenInstance(final Field field) {
        final Class<?> fieldClass = field.getDeclaringClass();

        if(fieldClass.equals(List.class)) {
            final Class<?> genericType      = ((Class<?>) getGenericType(field.getGenericType()));
            return new ListGenerator(getRandomGenInstance(genericType));
        } else if(fieldClass.equals(Set.class)) {
            final Class<?> genericType      = ((Class<?>) getGenericType(field.getGenericType()));
            return new SetGenerator(getRandomGenInstance(genericType));
        } else if(fieldClass.equals(Map.class)) {
            final Class<?> genericKeyType   = ((Class<?>) getGenericType(field.getGenericType(), 0));
            final Class<?> genericValueType = ((Class<?>) getGenericType(field.getGenericType(), 1));
            return new MapGenerator(getRandomGenInstance(genericKeyType), getRandomGenInstance(genericValueType));
        }

        return getRandomGenInstance(fieldClass);
    }

    public IGenerator getRandomGenInstance(final Class<?> fieldClass) {
        final List<Class<? extends IGenerator>> genClasses = generatorsClassMap.get(fieldClass);
        if (isEmpty(genClasses))
            return nullGenerator;

        final int genIndex = getIndexWithSalt(genClasses.size(), fieldClass.getSimpleName(), salt);
        final Class<? extends IGenerator> getClass = genClasses.get(genIndex);
        return getGeneratorInstance(getClass);
    }

    public int getIndexWithSalt(final int size,
                                final String name,
                                final int salt) {
        final int hashed = name.hashCode() + salt;
        return hashed % size;
    }

    public IGenerator getGeneratorInstance(final Class<? extends IGenerator> generatorClass) {
        final IGenerator generator = generators.get(generatorClass);
        if(generator != null)
            return generator;

        final IGenerator iGenerator = instantiate(generatorClass);
        if(iGenerator == null)
            return nullGenerator;

        generators.put(generatorClass, iGenerator);
        return iGenerator;
    }
}
