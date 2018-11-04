package io.dummymaker.util;

import io.dummymaker.generator.complex.impl.ArrayComplexGenerator;
import io.dummymaker.generator.complex.impl.ListComplexGenerator;
import io.dummymaker.generator.complex.impl.MapComplexGenerator;
import io.dummymaker.generator.complex.impl.SetComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.BooleanGenerator;
import io.dummymaker.generator.simple.impl.NullGenerator;
import io.dummymaker.generator.simple.impl.ObjectGenerator;
import io.dummymaker.generator.simple.impl.UuidGenerator;
import io.dummymaker.generator.simple.impl.number.*;
import io.dummymaker.generator.simple.impl.string.*;
import io.dummymaker.generator.simple.impl.time.impl.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.dummymaker.util.BasicCastUtils.getGenericType;
import static io.dummymaker.util.BasicCollectionUtils.getIndexWithSalt;
import static java.util.Collections.singletonList;

/**
 * Utils for auto gen mapping
 *
 * @author GoodforGod
 * @since 26.04.2018
 */
public class BasicGenUtils {

    /**
     * Salt used to select always the same generator for specific field
     */
    private static final int SALT = ((Long) System.currentTimeMillis()).hashCode();

    /**
     * Map with class values (int, Long, String, etc) as keys and suitable generators classes
     */
    private static final Map<Class, List<Class<? extends IGenerator>>> AUTO_GENERATORS;

    static {
        AUTO_GENERATORS = instantiateAutoGeneratorsMap();
    }

    private static Map<Class, List<Class<? extends IGenerator>>> instantiateAutoGeneratorsMap() {

        final Map<Class, List<Class<? extends IGenerator>>> collectedGenerators = Stream.of(
                ByteGenerator.class,
                ShortGenerator.class,
                IntegerGenerator.class,
                LongGenerator.class,
                FloatGenerator.class,
                FloatBigGenerator.class,
                DoubleBigGenerator.class,
                DoubleGenerator.class,

                // Strings
                HexNumberGenerator.class,
                HexDataGenerator.class,
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
                ObjectGenerator.class,
                CharacterGenerator.class,
                CharGenerator.class,
                UuidGenerator.class
        ).collect(Collectors.groupingBy(
                BasicGenUtils::getGeneratorType
        ));

        // Complex
        collectedGenerators.put(List.class, singletonList(ListComplexGenerator.class));
        collectedGenerators.put(Set.class, singletonList(SetComplexGenerator.class));
        collectedGenerators.put(Map.class, singletonList(MapComplexGenerator.class));

        // Primitives
        collectedGenerators.put(byte.class, singletonList(ByteGenerator.class));
        collectedGenerators.put(short.class, singletonList(ShortGenerator.class));
        collectedGenerators.put(int.class, singletonList(IntegerGenerator.class));
        collectedGenerators.put(long.class, singletonList(LongGenerator.class));
        collectedGenerators.put(float.class, Arrays.asList(FloatGenerator.class, FloatBigGenerator.class));
        collectedGenerators.put(double.class, Arrays.asList(DoubleGenerator.class, DoubleBigGenerator.class));
        collectedGenerators.put(char.class, singletonList(CharGenerator.class));
        collectedGenerators.put(boolean.class, singletonList(BooleanGenerator.class));

        return collectedGenerators;
    }

    private static Class<?> getGeneratorType(Class<? extends IGenerator> generator) {
        return (generator.getGenericInterfaces().length == 0)
                ? ((Class<?>) getGenericType(((Class<?>) generator.getGenericSuperclass()).getGenericInterfaces()[0]))
                : ((Class<?>) getGenericType(generator.getGenericInterfaces()[0]));
    }

    public static Class<? extends IGenerator> getAutoGenerator(final Class<?> fieldClass) {
        if (fieldClass == null)
            return NullGenerator.class;

        final List<Class<? extends IGenerator>> generators = AUTO_GENERATORS.get(fieldClass);
        final String fieldName = fieldClass.getSimpleName();

        if (fieldClass.getTypeName().endsWith("[][]"))
            return ArrayComplexGenerator.class;
        if (fieldClass.getTypeName().endsWith("[]"))
            return ArrayComplexGenerator.class;
        if (BasicCollectionUtils.isEmpty(generators))
            return NullGenerator.class;

        return generators.get(getIndexWithSalt(generators.size(), fieldName, SALT));
    }

    public static Class<? extends IGenerator> getAutoGenerator(final String classTypeName) {
        if (BasicStringUtils.isEmpty(classTypeName))
            return NullGenerator.class;

        for (Map.Entry<Class, List<Class<? extends IGenerator>>> entry : AUTO_GENERATORS.entrySet()) {
            if (entry.getKey().getTypeName().equals(classTypeName))
                return entry.getValue().get(getIndexWithSalt(entry.getValue().size(), classTypeName, SALT));
        }

        return NullGenerator.class;
    }
}
